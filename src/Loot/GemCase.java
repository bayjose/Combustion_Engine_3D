/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Loot;

import base.SpriteBinder;
import java.awt.Graphics;

/**
 *
 * @author Bailey
 */
public class GemCase {
    int width = 5;
    int height = 3;
    int offsetX = 108;
    int offsetY = 152;
    int spacingX = 146;
    int spacingY = 144;
    GemSlot[] slots = new GemSlot[width * height];
    public GemCase(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                this.slots[i + j* width] = new GemSlot(i * spacingX + offsetX, j * spacingY + offsetY);
//                this.slots[i + j* width].gem = GemRegistry.genRandomGem();
                this.slots[i + j* width].gem = null;
            }
        }
    }
    
    public void checkCollisions(){
        for(int i = 0; i< this.slots.length; i++){
            this.slots[i].Collides();
        }
    }
    
    public void render(Graphics g){
        g.drawImage(SpriteBinder.checkImage("gemCase.png"), 0, 0, null);
        for(int i = 0; i< this.slots.length; i++){
            this.slots[i].render(g);
        }
    }
    
    public void addGem(Gem gem){
        for(int i = 0; i< this.slots.length; i++){
            if(this.slots[i].gem == null){
                this.slots[i].gem = gem;
                return;
            }
        }
    }
    
}
