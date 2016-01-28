/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package physics;

/**
 *
 * @author Bayjose
 */
public class Vector3D {
    double xDir=0.0F;
    double yDir=0.0F;
    double zDir=0.0F;
    
    public Vector3D(double x, double y, double z){
        this.xDir=x;
        this.yDir=y;
        this.zDir=z;
    }
    
    public double getX(){
        return this.xDir;
    }
    
    public double getY(){
        return this.yDir;
    }
    
    public double getZ(){
        return this.zDir;
    }
    
    public void setVelZ(double velForwards){
        this.zDir=velForwards;
    }
    
    public void setVelX(double velForwards){
        this.xDir=velForwards;
    }
    public void setVelY(double velForwards){
        this.yDir=velForwards;
    }
    
    public void increaseVelX(double x){
        this.xDir+=x;
    }
    public void increaseVelY(double x){
        this.yDir+=x;
    }
    public void increaseVelZ(double x){
        this.zDir+=x;
    }
    
    public Vector3D addVector(Vector3D vector){
        this.xDir+=vector.getX();
        this.yDir+=vector.getY();
        this.zDir+=vector.getZ();
        return this;
    }
    
    public Vector3D inverse(){
        return new Vector3D(-this.getX(), -this.getY(), -this.getZ());
    }
    
    public boolean isEqualTo(Vector3D test){
        if(test.getX()==this.getX()){
            if(test.getY()==this.getY()){
                if(test.getZ()==this.getZ()){
                    return true;
                }
            } 
        }
        return false;
    }
    
    public Vector3D newInstance(){
        return new Vector3D(this.getX(), this.getY(), this.getZ());
    }
    
    public double dotProduct(Vector3D compare){
        double uv = (this.getX()*compare.getX()) + (this.getY() * compare.getY()) + (this.getZ() + compare.getZ());
        double u = (double)(Math.sqrt((this.getX()*this.getX())+(this.getY()*this.getY())+(this.getZ() * this.getZ())));
        double v = (double)(Math.sqrt((compare.getX()*compare.getX())+(compare.getY()*compare.getY())+(compare.getZ() * compare.getZ())));
        System.out.println((double)Math.acos(uv/u*v));
        return (double)Math.acos(uv/u*v);
    }
    
    public Vector3D calcDifference(Vector3D other){
        return new Vector3D(this.getX() - other.getX(), this.getY() - other.getY(), this.getZ() - other.getZ());
    }
    
    public Vector3D multiplyVector(Vector3D magnitude){
        return new Vector3D(this.xDir*magnitude.getX(), this.yDir*magnitude.getY(), this.zDir*magnitude.getZ());
    }
}
