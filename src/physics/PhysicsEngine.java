/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;


import base.util.StringUtils;
import java.awt.Color;
import java.awt.Graphics;


/**
 *
 * @author Bayjose
 */
public class PhysicsEngine {
    public static String activeChannel = "bodies";
    public static CollisionChannel[] channels;
    private int numBods = 0;
    
    private int time = 0;
    
//    new RigidBody(new Point[]{new Point2D(-20, -20),new Point2D(-20, 20),new Point2D(0, 100)});
    
    
    public PhysicsEngine(){
        System.out.println("--------------------------------------------------");
        Reset();
    }
    
    public void tick(){
        time++;
        
        for(int i=0; i<PhysicsEngine.channels.length; i++){
            PhysicsEngine.channels[i].tick();
        }
    }
    
    public void Render(Graphics g){
        for(int i=0; i<PhysicsEngine.channels.length; i++){
            PhysicsEngine.channels[i].clear();
            for(RigidBody obj: PhysicsEngine.channels[i].collisons){
                RigidUtils.Render(obj, g);
            }
        }
    }
    
    public static void addToChannel(String name, RigidBody object){
        for(int i=0; i<channels.length; i++){
            if(channels[i].name.equals(name)){
//                channels[i].append(object);
                return;
            }
        }
    }
    
    public static void addToActiveChannel(RigidBody object){
        for(int i=0; i<channels.length; i++){
            if(channels[i].name.equals(PhysicsEngine.activeChannel)){
//                channels[i].append(object);
                return;
            }
        }
    }
    
    
    public static void addChannel(String name){
        CollisionChannel[] newChannels = new CollisionChannel[PhysicsEngine.channels.length+1];
        for(int i=0; i<newChannels.length-1; i++){
            newChannels[i] = PhysicsEngine.channels[i];
        }
        newChannels[newChannels.length-1] = new CollisionChannel(name, new RigidBody[]{});
        PhysicsEngine.activeChannel = name;
        PhysicsEngine.channels = newChannels;
    }
    
    public static CollisionChannel getChannel(String name){
        if(channels==null){
            String[] channelNames = StringUtils.loadData("Game/PhysicsEngine.txt");
            System.out.println("Initializing Channels.");
            channels = new CollisionChannel[channelNames.length - 1];
            for (int i = 1; i < channelNames.length; i++) {
                channels[i - 1] = new CollisionChannel(channelNames[i], new RigidBody[]{});
                System.out.println("Initialized Collision Channel:" + channelNames[i]);
            }
            System.out.println("Done.");
        }
        for(int i=0; i<channels.length; i++){
            if(channels[i].name.equals(name)){
                return channels[i];
            }
        }
        PhysicsEngine.addChannel(name);
        return getChannel(name);
    }
    
    public Color randomColor(){
        return (new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)));
    }
    
    public static void Reset(){
        PhysicsEngine.channels = new CollisionChannel[0];
        if(channels.length<=0){
            String[] channelNames = StringUtils.loadData("Game/PhysicsEngine.txt");
            channelNames = StringUtils.addLine(channelNames, "bodies");
            System.out.println("Initializing Channels.");
            channels = new CollisionChannel[channelNames.length - 1];
            for (int i = 1; i < channelNames.length; i++) {
                channels[i - 1] = new CollisionChannel(channelNames[i], new RigidBody[]{});
                System.out.println("Initialized Collision Channel:" + channelNames[i]);
            }
            System.out.println("Done.");
        }
    }
}
