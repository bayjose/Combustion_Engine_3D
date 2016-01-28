/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Loot;

import Base.input.Keyboard;
import Base.input.MousePositionLocator;
import java.awt.Graphics;
import physics.Point2D;
import physics.PrebuiltBodies;
import physics.RigidBody;
import physics.RigidUtils;

/**
 *
 * @author Bailey
 */
public class GemSlot {
    
    public int x;
    public int y;
    public RigidBody collision;
    public Gem gem = null;
    
    public GemSlot(int x, int y){
        this.x =x;
        this.y = y;
        this.collision = PrebuiltBodies.quad(new Point2D(x, y), 96);
    }
    
    public void render(Graphics g){
        if(Keyboard.bool1){
            RigidUtils.RenderWireframe(collision, g);
        }
        if(this.gem != null){
            this.gem.RenderAt(x, y, g);
            if (RigidUtils.Collides(MousePositionLocator.MouseLocation, collision)) {
                g.drawString(gem.getData(), x, y);
            }
        }
    }
    
    public Gem takeGem(){
        Gem out = this.gem;
        this.gem = null;
        return out;
    }
    
    public boolean Collides(){
        if(RigidUtils.Collides(MousePositionLocator.MouseLocation, collision)){
            if(MouseGem.mouseGem==null){
                if(this.gem!=null){
                    MouseGem.mouseGem = this.takeGem();
                    return false;
                }
            }
            if(MouseGem.mouseGem!=null){
                if(this.gem==null){
                    this.gem = MouseGem.mouseGem;
                    MouseGem.mouseGem = null;
                    return false;
                }
            }
            if(MouseGem.mouseGem!=null){
                if(this.gem!=null){
                    Gem temp = this.gem;
                    this.gem = MouseGem.mouseGem;
                    MouseGem.mouseGem = temp;
                    return false;
                }
            }
            return true;
        }else{
            return false;
        }
    }
    
}
