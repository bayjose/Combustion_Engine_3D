/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import base.Camera;
import graphics.Render3D;

/**
 *
 * @author Bailey
 */
public class AirBlock extends Block{

    public AirBlock(int x, int y, int z) {
        super(x, y, z);
        this.specialRender = true;
        this.setCollision(null);
    }

    @Override
    public void extraRender(Render3D render, Camera cam) {
        
    }


}
