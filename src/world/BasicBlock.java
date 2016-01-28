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
public class BasicBlock extends Block{

    public BasicBlock(int x, int y, int z) {
        super(x, y, z);
        super.solid = true;
    }

    @Override
    public void extraRender(Render3D render, Camera cam) {
        
    }

}
