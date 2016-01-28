/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Loot;

import physics.Point2D;

/**
 *
 * @author Bailey
 */
public class Material {
    String name;
    Point2D position;
    public Material(String name, int x, int y){
        this.name = name;
        position = new Point2D(x,y);
    }
}
