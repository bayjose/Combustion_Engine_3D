/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Loot;

import Base.input.MousePositionLocator;
import java.awt.Graphics;

/**
 *
 * @author Bailey
 */
public class MouseGem {
    public static Gem mouseGem = GemRegistry.genRandomGem();
    
    public static void render(Graphics g){
       if(mouseGem != null){
           mouseGem.RenderAt(MousePositionLocator.MouseLocation.x, MousePositionLocator.MouseLocation.y, g);
       }
    }
}
