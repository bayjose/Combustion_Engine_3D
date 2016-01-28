/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package base;

import graphics.Render;
import graphics.Texture;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 *this is a file that contains all the loaded sprites 
 * @author Bayjose
 */
public class SpriteBinder {
    //current resources.png file in the dir you are looking at
    
    public static Image preview;
     public static LinkedList<RegisteredImage> loadedImages = new LinkedList<RegisteredImage>();
    
    public static void init(){
        
    }
    
    public static Image checkImage(String id){
        for(int i=0; i<SpriteBinder.loadedImages.size(); i++){
            if(SpriteBinder.loadedImages.get(i).id.equals(id)){
//                System.out.println("Image:"+id+" already exists.");
                return SpriteBinder.loadedImages.get(i).image;
            }
        }
        
        RegisteredImage temp = new RegisteredImage(id);
        SpriteBinder.loadedImages.add(temp);
        return temp.image;
//        return new RegisteredImage("Core/developer.png").image;
    }
    
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
    
    public static Render loadTexture(String id){
        try{
            BufferedImage image = ImageIO.read(Texture.class.getClass().getResource(id));
            int width = image.getWidth();
            int height = image.getHeight();
            Render result = new Render(width, height);
            image.getRGB(0, 0, width, height, result.pixels, 0, width);
            return result;
        }catch(Exception e){
            System.err.println("Crash");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
   
    
}
