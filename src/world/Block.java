/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import base.Camera;
import graphics.Render;
import graphics.Render3D;
import graphics.Texture;
import physics.Point2D;
import physics.PrebuiltBodies;
import physics.RigidBody;

/**
 *
 * @author Bailey
 */
public abstract class Block {
    int x = 0;
    int y = 0;
    int z = 0;
    
    boolean specialRender = false;
    boolean solid = false;
    
    Render texture = Texture.ruins_wall;
    
    //collision
    private RigidBody collision;
    
    //renders
//    private boolean renderTop    = false;
//    private boolean renderBottom = false;
    private boolean renderNorth  = false;
    private boolean renderSouth  = false;
    private boolean renderEast   = false;
    private boolean renderWest   = false;
    
    public Block(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
        this.collision = PrebuiltBodies.quad(new Point2D((int)((x + 0.5) * 16), (int)((z + 1.5) * 16)), 16);
    }
    
    public void update(World world){
        renderNorth = world.getBlock(x, z+1).specialRender;
        renderSouth = world.getBlock(x, z-1).specialRender;
        renderEast  = world.getBlock(x+1, z).specialRender;
        renderWest  = world.getBlock(x-1, z).specialRender;
    }
    
    public void render(Render3D render, Camera cam){
        if(!this.specialRender){
            if(this.renderSouth){
                render.drawWall(x + (1.0/16.0), 1.0 + x, 1.0 + z, 1.0 + z, 0.0 + y, cam, this.texture);
            }
            if(this.renderWest){
                render.drawWall(x + (1.0/16.0), x, 2.0 + z, 1.0 + z, 0.0 + y, cam, this.texture);
            }
            if(this.renderEast){
                render.drawWall(x + (1.0/16.0) + 1.0, x + 1.0, 1.0 + z, 2.0 + z, 0.0 + y, cam, this.texture);
            }
            if(this.renderNorth){
                render.drawWall(1.0 + x + (1.0/16.0), x,  2.0 + z, 2.0 + z, 0.0 + y, cam, this.texture);
            }
        }
        if(this.isRendered()){
            this.extraRender(render, cam);
        }
    }
    
    public abstract void extraRender(Render3D render, Camera cam);
    
    public void triggerOnCollision(World world){
        return;
    }
    
    public boolean isRendered(){
        return (renderNorth || this.renderSouth || this.renderEast || this.renderWest);
    }

    void setTexture(Render render) {
        this.texture = render;
    }
    
    public RigidBody getCollision(){
        return this.collision;
    }
    
    public void setCollision(RigidBody collision){
        this.collision = collision;
    }
    
    
}
