/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Loot;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import physics.Point2D;
import physics.PrebuiltBodies;
import physics.RigidBody;
import physics.RigidUtils;

/**
 *
 * @author Bailey
 */
public class Gem {
    private final EnumRarity rarity;
    private final EnumQuality quality;
    private final BufferedImage material;
    public final String name;
    private double rotation;
    private final int size = 80;
    public RigidBody shape;
    
    public Gem (EnumRarity rarity,EnumQuality quality, BufferedImage material, String name){
        this.rarity = rarity;
        this.quality = quality;
        this.material = material;
        this.name = name;
        this.rotation = Math.random() * 360;
        shape = new RigidBody(new Point2D[]{new Point2D(0,size/2),new Point2D(-size/2, 0),new Point2D(-size/3, -size/4),new Point2D(size/3, -size/4),new Point2D(size/2, 0)});
        shape.color = Color.BLACK;
    }
    
    public void RenderAt(int x, int y, Graphics g){
        Polygon p = shape.getCollision();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.quality.translucent));
        g2d.translate(x, y);
        g2d.setClip(p);
        g2d.rotate(Math.toRadians(rotation)+Math.toDegrees(shape.angleY));
        g.drawImage(this.material, -(size+(size/3 * 2))/2, -(size+(size/3 * 2))/2, size+(size/3 * 2), size+(size/3 * 2), null);
        g2d.rotate(-Math.toRadians(rotation)-Math.toDegrees(shape.angleY));
        g2d.setClip(null);
        RigidUtils.RenderWireframe(shape, g);
        g2d.translate(-x,-y);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
    
    public String getData(){
        return this.rarity.name().toLowerCase()+" "+this.quality.name().toLowerCase()+" "+this.name;
    }
    
}
