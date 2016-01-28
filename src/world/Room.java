/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import java.awt.Color;
import physics.Point2D;
import physics.PrebuiltBodies;
import physics.RigidBody;

/**
 *
 * @author Bailey
 */
public class Room {
    int x = 0;
    int y = 0;
    int width = 0; 
    int height = 0;
    int exitsX;
    int exitsY;
    RigidBody collision;
    
    public Room(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.collision = PrebuiltBodies.quad(new Point2D((int)(((x-1) + 0.5) * 16), (int)(((y-1) + 1.5) * 16)), (width + 2) * 16, (height + 2) * 16);
        this.collision.Translate((width+3)*16/2, (height+3)*16/2, 0);
        this.collision.setColor(Color.green);
        this.exitsX = (int)(Math.random() * (width))+1;
        this.exitsY = (int)(Math.random() * (height))+1;
    }
    
    public int getCenterX(){
        return (int)(((x-1) + 0.5) * 16) + ((width+3)*16/2);
    }
    
    public int getCenterY(){
        return (int)(((y-1) + 1.5) * 16) + ((height+3)*16/2);
    }
}
