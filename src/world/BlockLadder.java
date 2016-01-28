/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import base.Camera;
import graphics.Render3D;
import graphics.Texture;
import physics.Point2D;
import physics.PrebuiltBodies;
import physics.RigidUtils;

/**
 *
 * @author Bailey
 */
public class BlockLadder extends Block{

    public BlockLadder(int x, int y, int z) {
        super(x, y, z);
        this.specialRender = true;
        this.texture = Texture.ladder;
        this.setCollision(PrebuiltBodies.quad(new Point2D((int)((x + 0.5) * 16), (int)((z + 1.5) * 16)), 16, 8));
    }

    public void extraRender(Render3D render, Camera cam) {
        render.drawWallFacesCamera(x, y, z, cam, texture);
    }
    
    @Override
    public void triggerOnCollision(World world){
        world.regenerate();
    }
    
}
