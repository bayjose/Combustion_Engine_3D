/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import Base.input.Keyboard;
import Loot.GemGenerator;
import base.Game;
import java.awt.Graphics;
import physics.Vector3D;
import world.World;

/**
 *
 * @author Bailey
 */
public class Screen extends Render{
    
    private Render test;
    private Render3D render;
    private World world = new World(32, 32,1);
    
    private GemGenerator gg;
    
    public Screen(int width, int height) {
        super(width, height);
        render = new Render3D(width, height);
        test = new Render(256, 256);
        gg = new GemGenerator();
    }
    
    public void tick(){
        Vector3D last = world.getCam().getPosition().newInstance();
        world.getCam().tick();
        Vector3D now = world.getCam().getPosition().newInstance();
        Vector3D difference = last.calcDifference(world.getCam().getPosition());
        world.getCam().setPosition(last);
        
        //calc the X
        world.getCam().getPosition().setVelX(now.getX());
        while(world.camCollides()){
            if(difference.getX()>0){
                world.getCam().getPosition().increaseVelX(0.1);
            }else{
                world.getCam().getPosition().increaseVelX(-0.1);
            }
        }
        
        //calc the Z
        world.getCam().getPosition().setVelZ(now.getZ());
        while(world.camCollides()){
            if(difference.getZ()>0){
                world.getCam().getPosition().increaseVelZ(0.1);
            }else{
                world.getCam().getPosition().increaseVelZ(-0.1);
            }
        }
        if(Keyboard.UP){
            gg.play();
        }
        gg.tick();
        //add the Y
        world.getCam().getPosition().increaseVelY(difference.getY());
        
    }
    
    public void render(Graphics g){
        world.render(g);
        gg.render(g);
    }
    
    public void render3D(Game game){
        //clear the screen
        for(int i = 0 ; i< width * height; i ++){
            pixels[i] = 0;
        }
        render.floor(world.getCam());
        world.render3D(render); 
        render.renderDistanceLimiter();
        draw(render, 0, 0);
    }
    
}
