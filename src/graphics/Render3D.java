/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import base.Camera;
import base.util.DistanceCalculator;
import java.util.Random;
import physics.Point;
import physics.Point2D;
import physics.RigidBody;
import physics.RigidUtils;

/**
 *
 * @author Bailey
 */
public class Render3D extends Render{
    double floorPosition = 8;
    double ceilingPosition = 8;

    double sine = 0;
    double cosine = 0;
    Random random = new Random();
    public double[] zBuffer;
    public double[] zBufferWall;
    
    public Render3D(int width, int height) {
        super(width, height);
        zBuffer = new double[width * height];
        zBufferWall = new double[width * height];
    }
    
    public void floor(Camera cam){
        
        for(int i = 0; i < width*height; i++){
            zBufferWall[i] = 0;
        }
//        System.out.println("X:"+(int)cam.getPosition().getX()/16+" Y:"+(int)cam.getPosition().getY()/16+" Z:"+(int)cam.getPosition().getZ()/16);
        cosine = Math.cos(cam.getRotation().getX());
        sine = Math.sin(cam.getRotation().getX());
        for(int y = 0; y < height; y++){
            double ceiling = (y - height / 2.0) / height;
            double z = (floorPosition - cam.getPosition().getY()) / ceiling;
            if(ceiling < 0){
                z = (ceilingPosition + cam.getPosition().getY()) / -ceiling;
            }
            for(int x = 0; x < width; x++){
                double depth = (x - width / 2.0) / height;
                depth *= z;
                double xx = depth * cosine + z * sine-1;
                double yy = z * cosine - depth * sine;
                int xPix = (int) (xx + (cam.getPosition().getX()));
                int yPix = (int) (yy + (cam.getPosition().getZ()));
                zBuffer[x + y * width] = z;
//                pixels[x + y * width] = ((xPix & 15) * 16) | ((yPix & 15) * 16) << 8;
                pixels[x + y * width] = Texture.getPixelsAt(Texture.ruins_floor, xPix, yPix);
//                pixels[x + y * width] = (Texture.floor.pixels[(xPix & 7) + (yPix & 7) * 8]* -1 << 1) & 128;
//                System.out.println(Texture.floor.pixels[(xPix & 31) + (yPix & 31) * 32]);
                if(z>cam.renderDistance){
                    pixels[x + y * width] = 0;
                }
            }
        }
    }
    
    public void drawWall(double xLeft, double xRight, double zDistanceLeft, double zDistanceRight, double yHeightTop, Camera cam){
        
//        xLeft = xLeft + (1.0 / 16.0);
        xRight = xRight + (1.0 / 16.0);
 
        double right = cam.getPosition().getX()/16;
        double up = cam.getPosition().getY()/16;
        double forward = cam.getPosition().getZ()/16;
        
        double xcLeft = ((xLeft) - right) * 2.0;
        double zcLeft = ((zDistanceLeft) - forward) * 2.0;
        
        double rotLeftSideX = xcLeft * cosine - zcLeft * sine;
        double yCornerTL = ((-yHeightTop - 0.5) - up) * 2.0;
        double yCornerBL = ((+1.0 - yHeightTop - 0.5) - up) * 2.0;
        double rotLeftSideZ = zcLeft * cosine + xcLeft * sine;
        
        double xcRight = ((xRight) - right) * 2.0;
        double zcRight = ((zDistanceRight) - forward) * 2;
        
        double rotRightSideX = xcRight * cosine - zcRight * sine;
        double yCornerTR = ((-yHeightTop - 0.5) - up) * 2.0;
        double yCornerBR = ((+1.0 - yHeightTop - 0.5) - up) * 2.0;
        double rotRightSideZ = zcRight * cosine + xcRight * sine;
        
        double tex30 = 0;
        double tex40 = 16;
        double clip  = 0.5;
        
        if(rotLeftSideZ < clip && rotRightSideZ < clip){
            return;
        }
        
        if(rotLeftSideZ < clip){
            double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
            rotLeftSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0; 
            rotLeftSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
            tex30 = tex30 + (tex40 - tex30) * clip0;
        }
        
        if(rotRightSideZ < clip){
            double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
            rotRightSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0; 
            rotRightSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
            tex40 = tex30 + (tex40 - tex30) * clip0;
        }
        
        double xPixelLeft = (rotLeftSideX / rotLeftSideZ * height + width / 2.0);
        double xPixelRight = (rotRightSideX / rotRightSideZ * height + width /2.0); 
        
        if(xPixelLeft >= xPixelRight){
            return;
        }
        
        int xPixelLeftInt = (int) xPixelLeft;
        int xPixelRightInt = (int) xPixelRight;
        
        if(xPixelLeftInt < 0){
            xPixelLeftInt = 0;
        }
        
        if(xPixelRightInt > width){
            xPixelRightInt = width;
        }
        
        double yPixelLeftTop  =  (yCornerTL / rotLeftSideZ * height + height / 2.0);
        double yPixelLeftBottom =  (yCornerBL / rotLeftSideZ * height + height / 2.0);
        double yPixelRightTop =  (yCornerTR / rotRightSideZ * height + height / 2.0);
        double yPixelRightBottom =  (yCornerBR / rotRightSideZ * height + height /2.0);
        
        double tex1 = 1 / rotLeftSideZ;
        double tex2 = 1 / rotRightSideZ;
        double tex3 = tex30 / rotLeftSideZ;
        double tex4 = tex40 / rotRightSideZ - tex3;
        
        for(int x = xPixelLeftInt; x<xPixelRightInt; x++){
            double pixelRotation = (x - xPixelLeft) / (xPixelRight - xPixelLeft);
            double zWall = (tex1 + (tex2 - tex1) * pixelRotation);
            
            int xTexture = (int) ((tex3 + tex4 * pixelRotation) / zWall);
            
            double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop) * pixelRotation;
            double yPixelBottom = yPixelLeftBottom + (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;
            
            int yPixelTopInt = (int) yPixelTop;
            int yPixelBottomInt = (int) yPixelBottom;
            
            if(yPixelTopInt < 0){
                    yPixelTopInt = 0;
            }
            
            if(yPixelBottomInt > height){
                yPixelBottomInt = height;
            }
            for(int y = yPixelTopInt; y < yPixelBottomInt; y++){
                double pixelRotationY = ( y - yPixelTop) / (yPixelBottom - yPixelTop);
                
                if (zBufferWall[x + y * width] > zWall) {
                    continue;
                }
                zBufferWall[x + y * width] = zWall;
                
                int yTexture = (int) (16 * pixelRotationY);
//                pixels[x+y*width] = xTexture * 100 + yTexture * 100 * 256;
                pixels[x+y*width] = Texture.getPixelsAt(Texture.floor, xTexture, yTexture);
                zBuffer[x+y*width] = 1 / (tex1 + (tex2 - tex1) * pixelRotation) * cam.numTiles * 2;
            }
        }
    }
    
    
    public void drawWall(double xLeft, double xRight, double zDistanceLeft, double zDistanceRight, double yHeightTop, Camera cam, Render texture){
        
//        xLeft = xLeft + (1.0 / 16.0);
        xRight = xRight + (1.0 / 16.0);
 
        double right = cam.getPosition().getX()/16;
        double up = cam.getPosition().getY()/16;
        double forward = cam.getPosition().getZ()/16;
        
        double xcLeft = ((xLeft) - right) * 2.0;
        double zcLeft = ((zDistanceLeft) - forward) * 2.0;
        
        double rotLeftSideX = xcLeft * cosine - zcLeft * sine;
        double yCornerTL = ((-yHeightTop - 0.5) - up) * 2.0;
        double yCornerBL = ((+1.0 - yHeightTop - 0.5) - up) * 2.0;
        double rotLeftSideZ = zcLeft * cosine + xcLeft * sine;
        
        double xcRight = ((xRight) - right) * 2.0;
        double zcRight = ((zDistanceRight) - forward) * 2;
        
        double rotRightSideX = xcRight * cosine - zcRight * sine;
        double yCornerTR = ((-yHeightTop - 0.5) - up) * 2.0;
        double yCornerBR = ((+1.0 - yHeightTop - 0.5) - up) * 2.0;
        double rotRightSideZ = zcRight * cosine + xcRight * sine;
        
        double tex30 = 0;
        double tex40 = 16;
        double clip  = 0.5;
        
        if(rotLeftSideZ < clip && rotRightSideZ < clip){
            return;
        }
        
        if(rotLeftSideZ < clip){
            double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
            rotLeftSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0; 
            rotLeftSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
            tex30 = tex30 + (tex40 - tex30) * clip0;
        }
        
        if(rotRightSideZ < clip){
            double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
            rotRightSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0; 
            rotRightSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
            tex40 = tex30 + (tex40 - tex30) * clip0;
        }
        
        double xPixelLeft = (rotLeftSideX / rotLeftSideZ * height + width / 2.0);
        double xPixelRight = (rotRightSideX / rotRightSideZ * height + width /2.0); 
        
        if(xPixelLeft >= xPixelRight){
            return;
        }
        
        int xPixelLeftInt = (int) xPixelLeft;
        int xPixelRightInt = (int) xPixelRight;
        
        if(xPixelLeftInt < 0){
            xPixelLeftInt = 0;
        }
        
        if(xPixelRightInt > width){
            xPixelRightInt = width;
        }
        
        double yPixelLeftTop  =  (yCornerTL / rotLeftSideZ * height + height / 2.0);
        double yPixelLeftBottom =  (yCornerBL / rotLeftSideZ * height + height / 2.0);
        double yPixelRightTop =  (yCornerTR / rotRightSideZ * height + height / 2.0);
        double yPixelRightBottom =  (yCornerBR / rotRightSideZ * height + height /2.0);
        
        double tex1 = 1 / rotLeftSideZ;
        double tex2 = 1 / rotRightSideZ;
        double tex3 = tex30 / rotLeftSideZ;
        double tex4 = tex40 / rotRightSideZ - tex3;
        
        for(int x = xPixelLeftInt; x<xPixelRightInt; x++){
            double pixelRotation = (x - xPixelLeft) / (xPixelRight - xPixelLeft);
            double zWall = (tex1 + (tex2 - tex1) * pixelRotation);
            
            int xTexture = (int) ((tex3 + tex4 * pixelRotation) / zWall);
            
            double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop) * pixelRotation;
            double yPixelBottom = yPixelLeftBottom + (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;
            
            int yPixelTopInt = (int) yPixelTop;
            int yPixelBottomInt = (int) yPixelBottom;
            
            if(yPixelTopInt < 0){
                    yPixelTopInt = 0;
            }
            
            if(yPixelBottomInt > height){
                yPixelBottomInt = height;
            }
            for(int y = yPixelTopInt; y < yPixelBottomInt; y++){
                double pixelRotationY = ( y - yPixelTop) / (yPixelBottom - yPixelTop);
                
                if (zBufferWall[x + y * width] > zWall) {
                    continue;
                }
                zBufferWall[x + y * width] = zWall;
                
                int yTexture = (int) (16 * pixelRotationY);
//                pixels[x+y*width] = xTexture * 100 + yTexture * 100 * 256;
                pixels[x+y*width] = Texture.getPixelsAt(texture, xTexture, yTexture);
                zBuffer[x+y*width] = 1 / (tex1 + (tex2 - tex1) * pixelRotation) * cam.numTiles * 2;
            }
        }
    }
    
    public void drawCube(int x, int y, int z, Camera cam){
        this.drawWall(x + (1.0/16.0), 1.0 + x, 1.0 + z, 1.0 + z, 0.0 + y, cam);
        this.drawWall(x + (1.0/16.0), x, 2.0 + z, 1.0 + z, 0.0 + y, cam);
        this.drawWall(x + (1.0/16.0) + 1.0, x + 1.0, 1.0 + z, 2.0 + z, 0.0 + y, cam);
        this.drawWall(1.0 + x + (1.0/16.0), x,  2.0 + z, 2.0 + z, 0.0 + y, cam);
    }
    
    public void drawWallFacesCamera(int x, int y, int z, Camera cam){
        RigidBody body = new RigidBody(new Point[]{new Point2D(-0.5f, 0),new Point2D(0.5f, 0)});
        RigidUtils.RotateZOnlyPoints(body, -cam.getRotation().getX());
        this.drawWall(body.points[0].getX() + x , body.points[1].getX() + x,  body.points[0].getY() + z, body.points[1].getY() + z, 0.0 + y , cam);
    }
    
    public void drawWallFacesCamera(int x, int y, int z, Camera cam, Render texture){
        RigidBody body = new RigidBody(new Point[]{new Point2D(-0.5f, 0),new Point2D(0.5f, 0)});
        RigidUtils.RotateZOnlyPoints(body, -cam.getRotation().getX());
        body.Translate(0.5f, 1.5f, 0.0f);
        this.drawWall(body.points[0].getX()+body.x + x , body.points[1].getX()+body.x + x,  body.points[0].getY()+body.y + z, body.points[1].getY()+body.y + z, 0.0 + y , cam, texture);
    }
    
    public void renderDistanceLimiter(){
        for(int i = 0; i<width*height; i++){
            int color = pixels[i];
            double brightness = (30000 / (zBuffer[i]));
            if(brightness < 0 ){
                brightness = 0;
            }  
            if(brightness > 255){
                brightness = 255;
            }   
            int r = (color >> 16) & 0xff;
            int g = (color >> 8) & 0xff;
            int b = (color) & 0xff;
            
            r = (int) (r *  brightness / 255);
            g = (int) (g *  brightness / 255);
            b = (int) (b *  brightness / 255);
            
            pixels[i] = r << 16 | g << 8 | b;
            
        }
    }
    
}
