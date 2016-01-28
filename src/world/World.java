/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import Base.input.Keyboard;
import base.Camera;
import base.Display;
import base.util.DistanceCalculator;
import graphics.Render3D;
import graphics.Texture;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;
import physics.Point;
import physics.Point2D;
import physics.Point3D;
import physics.PrebuiltBodies;
import physics.RigidBody;
import physics.RigidUtils;
import physics.Vector3D;

/**
 *
 * @author Bailey
 */
public class World {
    public final int width;
    public final int height;
    private Block[] blocks;
    private RigidBody[] bodies;
    private LinkedList<Room> rooms = new LinkedList<Room>();
    private LinkedList<Node> nodes = new LinkedList<Node>();
    private LinkedList<RigidBody> halls = new LinkedList<RigidBody>();
    
    private  String seed = "Dank Memes"+Math.random();
    private  Random rand = new Random(seed.hashCode());
    
    private final int collisionSize = 6;
    private int floors;
    private Camera cam = new Camera(new Vector3D(0, 0, 0));
    
    public World(int width, int height, int floors){
        this.width = width;
        this.height = height;
        this.floors = floors;
        blocks = new Block[width * height];
        bodies = new RigidBody[width * height];
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                blocks[x + y * width] = new AirBlock(x, 0, y);
                bodies[x + y * width] = blocks[x + y * width].getCollision();
            }
        }
        
        //rooms
        for(int i = 0; i < 300; i++){
            int roomW = rand.nextInt(6)+5;
            int roomH = rand.nextInt(6)+5;
            Room room = new Room(rand.nextInt((width - roomW -1)), rand.nextInt((height - roomH -1)), roomW, roomH);
            this.placeRoom(room);
        }
        //find closest node
        this.addNodes(32);
        this.calcClosestNode();
        //average difference between nodes
        //gen halways
        this.genHallways(8);
        //fill all but the hallways
        this.fillVoid();
        //re-open Rooms
        for(int i = 0; i< this.rooms.size(); i++){
            this.clearRoom(this.rooms.get(i));
        }
        //update to remove visual glitches
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.updateBlock(x, y);
            }
        }
        //optimize, only keep walls that are seen
        this.removeUnrenderedTiles();
        //update a second time to make Visual Glitches disappear. 
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.updateBlock(x, y);
            }
        }
        //walls
//        this.makeGayHallways();
        //
        this.placeDoor();
    }
    
    public void render(Graphics g){
        if(Keyboard.bool2){
            g.setColor(Color.WHITE);
            g.drawString("X:"+this.cam.getX()+"\n"+
                         "Y:"+this.cam.getY()+"\n"+
                         "Z:"+this.cam.getZ()+"\n", 10, 10);
            g.translate(-cam.getX() * 16 + Display.WIDTH/2, -cam.getZ() * 16 + Display.HEIGHT/2);
            for(int i = 0 ; i< this.rooms.size(); i++){
                RigidUtils.Render(this.rooms.get(i).collision, g);
            }
            for(int i = 0; i < this.halls.size(); i++){
                RigidUtils.Render(this.halls.get(i), g);
            }
            g.setColor(Color.MAGENTA);
            for(int i = 0; i < this.nodes.size(); i++){
                g.drawLine((int)this.nodes.get(i).worldPos.getX(), (int)this.nodes.get(i).worldPos.getY(), (int)this.nodes.get(i).getChild().getX(), (int)this.nodes.get(i).getChild().getY());
            }
            
            RigidBody camera = new RigidBody(new Point[]{new Point2D(-8, 0), new Point2D(8, 0), new Point2D((int)(cam.renderDistance/1.75), -(int)cam.renderDistance), new Point2D(-(int)(cam.renderDistance/1.75), -(int)cam.renderDistance)});
            RigidUtils.RotateZOnlyPoints(camera, cam.getRotation().getX() * -1 + Math.toRadians(180.0));
            camera.Translate((int) cam.getPosition().getX(), (int) cam.getPosition().getZ(), 0);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bodies[x + y * width] != null) {
                        RigidUtils.Render(bodies[x + y * width], g);
                    }
                }
            }
            camera.setColor(Color.yellow);
            RigidUtils.RenderWireframe(camera, g);
            RigidBody body = new RigidBody(new Point[]{new Point2D(-collisionSize, -collisionSize), new Point2D(collisionSize, -collisionSize), new Point2D(collisionSize, collisionSize), new Point2D(-collisionSize, collisionSize)});
            body.Translate((int)cam.getPosition().getX(), (int)cam.getPosition().getZ(), 0);
            body.setColor(Color.cyan);
            RigidUtils.Render(body, g);
            g.translate(cam.getX() * 16 + Display.WIDTH/2, cam.getZ() * 16 + Display.HEIGHT/2);
        }
    }
    
    public void render3D(Render3D render){
        for(int i = 0; i<this.blocks.length; i++){
            this.blocks[i].render(render, cam);
        }
    }

    public Block getBlock(int x, int y){
        if(x >= 0 && x < width){
            if(y >= 0 && y < height){
                return this.blocks[x + y * width];
            }
        }
        return new AirBlock(0, 0, 0);
    }
    
    public void placeBlock(Block block){
        int x = block.x;
        int y = block.z;
        if(x >= 0 && x < width){
            if(y >= 0 && y < height){

                this.bodies[x + y * width] = block.getCollision();
                
                this.blocks[x + y * width] = block;
                this.updateBlock(x, y);
                this.updateBlock(x-1, y);
                this.updateBlock(x+1, y);
                this.updateBlock(x, y-1);
                this.updateBlock(x, y+1);
                return;
            }
        }
    }
    
    public boolean camCollides(){
        RigidBody body = new RigidBody(new Point[]{new Point2D(-collisionSize, -collisionSize), new Point2D(collisionSize, -collisionSize), new Point2D(collisionSize, collisionSize), new Point2D(-collisionSize, collisionSize)});
        body.Translate((int)cam.getPosition().getX(), (int)cam.getPosition().getZ(), 0);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (bodies[x + y * width] != null) {
                    if(RigidUtils.Collides(body, this.bodies[x + y * width])){
                        this.bodies[x + y * width].setColor(Color.RED);
                        this.blocks[x + y * width].triggerOnCollision(this);
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    public boolean collides(RigidBody body){
        for(int i = 0; i < this.bodies.length; i++){
            if(this.bodies[i] != null){
                if(RigidUtils.Collides(body, this.bodies[i])){
                    return true;
                }
            }
        }
        return false;
    }
    
    public void updateBlock(int x, int y){
        if(x >= 0 && x < width){
            if(y >= 0 && y < height){ 
                this.blocks[x + y * width].update(this);
                return;
            }
        }
    }
    
    public Camera getCam(){
        return this.cam;
    }
    
    public void setCam(Camera cam){
        this.cam = cam;
    }
    
    public void placeRoom(Room room){
        for(int i = 0; i< this.rooms.size(); i++){
            if(RigidUtils.Collides(room.collision, this.rooms.get(i).collision)){
                return;
            }
        }
        this.rooms.add(room);
        this.nodes.add(new Node(room.getCenterX(), room.getCenterY()));
        int roomWidth = room.width;
        int roomHeight = room.height;
        
        for (int i = 0; i < roomWidth + 2; i++) {
            for (int j = 0; j < roomHeight + 2; j++) {
                this.placeBlock(new BasicBlock(i + room.x, 0, j + room.y));
            }
            
        }

        for (int i = 0; i < roomWidth; i++) {
            for (int j = 0; j < roomHeight; j++) {
                this.placeBlock(new AirBlock(i + room.x + 1, 0, j + room.y + 1));
            }
        }
        

        this.cam.getPosition().setVelX((room.x + (int) ((roomWidth + 2)) / 2.0) * 16);
        this.cam.getPosition().setVelZ((room.y + (int) ((roomHeight + 2)) / 2.0) * 16);
    }
    
    public void clearRoom(Room room) {
        for (int i = 0; i < room.width; i++) {
            for (int j = 0; j < room.height; j++) {
                this.placeBlock(new AirBlock(i + room.x + 1, 0, j + room.y + 1));
            }
        }
    }
    
    public void calcClosestNode(){
        LinkedList<Node> calcNodes = (LinkedList<Node>) this.nodes.clone();
        int index = 0;
        int start = 0;
        while(index<calcNodes.size()){
           float distance = Float.MAX_VALUE;
           int closestNode = 0;
           for(int i = 0; i < this.nodes.size(); i++){
               float curDistance = DistanceCalculator.CalculateDistanceF(calcNodes.get(start).worldPos.getX(), calcNodes.get(start).worldPos.getY(), this.nodes.get(i).worldPos.getX(), this.nodes.get(i).worldPos.getY());
               if(curDistance < distance && curDistance > 0){
                   if(this.nodes.get(i).getChild().getX() == this.nodes.get(i).worldPos.getX() && this.nodes.get(i).getChild().getY() == this.nodes.get(i).worldPos.getY()){
                        distance = curDistance;
                        closestNode = i;
                   }
               }
           }
           //closest node will be acurate at this point
           this.nodes.get(start).setChild(this.nodes.get(closestNode).worldPos);
           start = closestNode;
           index++;
        }
        for (int i = 0; i < this.nodes.size(); i++) {
            if (this.nodes.get(i).getChild().getX() == this.nodes.get(i).worldPos.getX() && this.nodes.get(i).getChild().getY() == this.nodes.get(i).worldPos.getY()) {
                System.out.println("Room not joined");
            }
        }
    }
    
    public void genHallways(int tollerance){
        for(int i = 0; i < this.nodes.size(); i++){
//            int xDifference = (int) DistanceCalculator.CalculateXDifferenceF(this.nodes.get(i).worldPos.getX(), this.nodes.get(i).getChild().getX());
//            int yDifference = (int) DistanceCalculator.CalculateXDifferenceF(this.nodes.get(i).worldPos.getY(), this.nodes.get(i).getChild().getY());
//            if(xDifference > yDifference){
                RigidBody hall = new RigidBody(new Point[]{new Point2D(this.nodes.get(i).worldPos.getX(), this.nodes.get(i).worldPos.getY()-tollerance),
                                                           new Point2D(this.nodes.get(i).getChild().getX()+tollerance, this.nodes.get(i).worldPos.getY()-tollerance),
                                                           new Point2D(this.nodes.get(i).getChild().getX(), this.nodes.get(i).getChild().getY()+tollerance),
                                                           new Point2D(this.nodes.get(i).getChild().getX(), this.nodes.get(i).getChild().getY()-tollerance),
                                                           new Point2D(this.nodes.get(i).getChild().getX()-tollerance, this.nodes.get(i).worldPos.getY()+tollerance),
                                                           new Point2D(this.nodes.get(i).worldPos.getX(), this.nodes.get(i).worldPos.getY()+tollerance),});
                hall.setColor(Color.CYAN);
                this.halls.add(hall);
//            }else{
                RigidBody hall2 = new RigidBody(new Point[]{new Point2D(this.nodes.get(i).worldPos.getX()+tollerance, this.nodes.get(i).worldPos.getY()),
                                                           new Point2D(this.nodes.get(i).getChild().getX()-tollerance, this.nodes.get(i).worldPos.getY()+tollerance),
                                                           new Point2D(this.nodes.get(i).getChild().getX()-tollerance, this.nodes.get(i).getChild().getY()),
                                                           new Point2D(this.nodes.get(i).getChild().getX()+tollerance, this.nodes.get(i).getChild().getY()),
                                                           new Point2D(this.nodes.get(i).getChild().getX()+tollerance, this.nodes.get(i).worldPos.getY()-tollerance),
                                                           new Point2D(this.nodes.get(i).worldPos.getX()-tollerance, this.nodes.get(i).worldPos.getY()),});
                hall2.setColor(Color.CYAN);
                this.halls.add(hall2);
//            }
        }
    }

    private void fillVoid() {
        for(int i = 0; i< this.width; i++){
            for(int j = 0; j< this.height; j++){
                this.placeBlock(new BasicBlock(i, 0, j));
            }
        }
        for(int i = 0; i< this.width; i++){
            for(int j = 0; j< this.height; j++){
                for (int k = 0; k < this.halls.size(); k++) {
                    if (RigidUtils.Collides(this.bodies[i + j * width], this.halls.get(k))) {
                        this.placeBlock(new AirBlock(i, 0, j));
                        break;
                    }
                }
            }
        }
    }
    
    private void removeUnrenderedTiles(){
        for(int i = 0; i< this.width; i++){
            for(int j = 0; j< this.height; j++){
                if(!this.getBlock(i, j).isRendered()){
                    this.blocks[i + j * width] = new AirBlock(i, 0, j);
                    this.bodies[i + j * width] = null;
                }
            }
        }
    }
    
    private void addNodes(int num){
        for(int i = 0; i< num; i++){
            int x = ((rand.nextInt(this.width-12)+6)*16);
            int y = ((rand.nextInt(this.height-12)+6)*16);
            this.nodes.add(new Node(x+4, y+4));
        }
    }
    
    private void placeDoor(){
        int room = rand.nextInt(this.rooms.size());
        int x = rand.nextInt(this.rooms.get(room).width)+this.rooms.get(room).x - (this.rooms.get(room).width/2);
        int y = rand.nextInt(this.rooms.get(room).height)+this.rooms.get(room).y - (this.rooms.get(room).height/2);
        BlockLadder door = new BlockLadder(x,0,y);
        this.placeBlock(door);
        System.out.println("Placed door at:"+x+" "+y);
    }
    
    public void regenerate(){
        if(this.floors<=0){
            //end
            System.out.println("You beat the dungeon:"+seed);
            return;
        }
        this.floors--;
        this.blocks = new Block[width * height];
        this.bodies = new RigidBody[width * height];
        this.rooms.clear();
        this.nodes.clear();
        this.halls.clear();

        this.seed = "Dank Memes"+Math.random();
        this.rand = new Random(seed.hashCode());

        cam = new Camera(new Vector3D(0, 0, 0));
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                blocks[x + y * width] = new AirBlock(x, 0, y);
                bodies[x + y * width] = blocks[x + y * width].getCollision();
            }
        }
        
        //rooms
        for(int i = 0; i < 300; i++){
            int roomW = rand.nextInt(6)+5;
            int roomH = rand.nextInt(6)+5;
            Room room = new Room(rand.nextInt((width - roomW -1)), rand.nextInt((height - roomH -1)), roomW, roomH);
            this.placeRoom(room);
        }
        //find closest node
        this.addNodes(32);
        this.calcClosestNode();
        //average difference between nodes
        //gen halways
        this.genHallways(8);
        //fill all but the hallways
        this.fillVoid();
        //re-open Rooms
        for(int i = 0; i< this.rooms.size(); i++){
            this.clearRoom(this.rooms.get(i));
        }
        //update to remove visual glitches
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.updateBlock(x, y);
            }
        }
        //optimize, only keep walls that are seen
        this.removeUnrenderedTiles();
        //update a second time to make Visual Glitches disappear. 
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.updateBlock(x, y);
            }
        }
        //walls
//        this.makeGayHallways();
        //
        this.placeDoor();
    }
    
}
