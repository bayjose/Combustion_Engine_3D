/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Loot;

import base.Display;
import java.awt.Graphics;
import java.util.LinkedList;
import physics.RigidUtils;
import physics.Vector3D;

/**
 *
 * @author Bailey
 */
public class GemGenerator {
    public boolean running = false;
    private int ticks = 0;
    private int coolDown = 0;
    private float speed = 0.45f;
    
    private LinkedList<Gem> visual = new LinkedList<Gem>();
    
    public void play(){
        running = true;
        ticks = 600;
        coolDown = 60;
        this.visual.clear();
        for(int i = 0 ; i< (Display.WIDTH/96)+1; i++){
            Gem gem = GemRegistry.genRandomGem();
            RigidUtils.Move(new Vector3D(i * 92, 0,0), gem.shape);
            visual.add(gem);
        }
    }
    
    public void tick(){
        if(this.ticks>0){

                for(int i = 0; i< this.visual.size(); i++){
                    if(visual.get(i).shape.x<-32){
                        visual.remove(i);
                        Gem toAdd = GemRegistry.genRandomGem();
                        toAdd.shape.Translate(this.visual.getLast().shape.x+96, 0, 0);
                        visual.add(toAdd);
                    }
                }
                for(int i = 0; i< this.visual.size(); i++){
                    RigidUtils.Move(new Vector3D(-(Math.log(this.ticks) * Math.log(this.ticks)) * this.speed, 0, 0), this.visual.get(i).shape);
                }

            this.ticks--;
        }
        if(this.ticks<=0 && this.running){
            if(this.coolDown<=0){
                Display.gemCase.addGem(this.visual.get(this.visual.size()/2));
                running = false;
            }
            this.coolDown--;
        }
    }
    
    public void render(Graphics g){
        if(running){
            for(int i = 0; i< this.visual.size(); i++){
                this.visual.get(i).RenderAt((int)visual.get(i).shape.x, 100, g);
            }
            GemRegistry.renderAllGems(0, 400, g);
        }
    }
}
