/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import base.util.DistanceCalculator;
import physics.Point2D;

/**
 *
 * @author Bailey
 */
public class Node {
    Point2D worldPos;
    private Point2D child;
    
    public Node(int x, int y){
        worldPos = new Point2D(x,y);
        child = new Point2D(x, y);
    }
    
    public void setChild(Point2D child){
        this.child = child;
    }
    
    public void equilize(){
        double differenceX = Math.abs(DistanceCalculator.CalculateXDifferenceF(this.worldPos.getX(),this.child.getX()))/2;
        double differenceY = Math.abs(DistanceCalculator.CalculateXDifferenceF(this.worldPos.getY(),this.child.getY()))/2;
        if(differenceX > differenceY){
            differenceX = 0;
        }else{
            differenceY = 0;
        }
        if(this.child.getX() > this.worldPos.getX()){
            this.child.setX((float) (this.child.getX()-differenceX));
            this.worldPos.setX((float) (this.worldPos.getX()+differenceX));
        }else{
            this.child.setX((float) (this.child.getX()+differenceX));
            this.worldPos.setX((float) (this.worldPos.getX()-differenceX));
        }
        
        if(this.child.getY() > this.worldPos.getY()){
            this.child.setY((float) (this.child.getY()-differenceY));
            this.worldPos.setY((float) (this.worldPos.getY()+differenceY));
        }else{
            this.child.setY((float) (this.child.getY()+differenceY));
            this.worldPos.setY((float) (this.worldPos.getY()-differenceY));
        }
        System.out.println(differenceX+" "+differenceY);
    }
    
    public Point2D getChild(){
        return this.child;
    }
}
