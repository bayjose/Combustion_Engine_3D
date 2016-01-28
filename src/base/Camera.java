/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import Base.input.Keyboard;
import audio.SoundPlayer;
import base.util.StringUtils;
import graphics.Render3D;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import physics.Vector3D;

/**
 *
 * @author Bailey
 */
public class Camera {
    private Vector3D rotation = new Vector3D(0.0, 0.0, 0.0);
    private Vector3D velocity = new Vector3D(0.0, 0.0, 0.0);
    private Vector3D position = new Vector3D(0.0, 0.0, 0.0);
    public int numTiles = 12;
    public double renderDistance = (numTiles + 1) * 16;
    
    public Camera(Vector3D position){
        this.position = position;
    }
    
    public void tick(){
//        System.out.println("X:"+(int)this.getPosition().getX()/16+" Y:"+this.getY()+" Z:"+(int)this.getPosition().getZ()/16);
        if (Keyboard.W){
            this.goForward(1);
        }
        if (Keyboard.S) {
            this.goBackward(1);
        }
        if (Keyboard.A) {
            this.goLeft(1);
        }
        if (Keyboard.D) {
            this.goRight(1);
        }
        if (Keyboard.Q) {
            this.position.increaseVelY(1);
        }
        if (Keyboard.E) {
            this.position.increaseVelY(-1);
        }
        if (Keyboard.LEFT) {
            this.rotation.increaseVelX(Math.toRadians(-3));
        }
        if (Keyboard.RIGHT) {
            this.rotation.increaseVelX(Math.toRadians(3));
        }
        this.position = this.position.addVector(this.velocity);
    }
    
    public void goForward(double speed){
        this.position.increaseVelZ(speed*Math.cos(rotation.getX()));
        this.position.increaseVelX(speed*Math.sin(rotation.getX()));
    }
    
    public void goBackward(double speed){
        this.position.increaseVelZ(-speed*Math.cos(rotation.getX()));
        this.position.increaseVelX(-speed*Math.sin(rotation.getX()));
    }
    
    public void goLeft(double speed){
        this.position.increaseVelZ(-speed*Math.sin(-rotation.getX()));
        this.position.increaseVelX(-speed*Math.cos(-rotation.getX()));
    }
    
    public void goRight(double speed){
        this.position.increaseVelZ(speed*Math.sin(-rotation.getX()));
        this.position.increaseVelX(speed*Math.cos(-rotation.getX()));
    }
    
    public int getX(){
        return (int)this.getPosition().getX()/16;
    }
    
    public int getY(){
        int out = (int)(this.getPosition().getY()*-1)/16;
        if((this.getPosition().getY()*-1) < 0 ){
            out -= 1;
        }
        return out;
    }
    
    public int getZ(){
        return (int)this.getPosition().getZ()/16;
    }
    
    
    public Vector3D getPosition(){
        return this.position;
    }
    
    public Vector3D getVelocity(){
        return this.velocity;
    }
    
    public Vector3D getRotation(){
        return this.rotation;
    }
    
    public void setPosition(Vector3D vec){
        this.position = vec;
    }
    
    public void setVelocity(Vector3D vec){
        this.velocity = vec;
    }
    
    public void setRotation(Vector3D vec){
        this.rotation = vec;
    }
}
