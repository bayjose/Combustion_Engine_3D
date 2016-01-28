package base;

import graphics.Texture;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteSheet{
   BufferedImage spriteSheet; 
   
   public int width;
   public int height;
   public int rows;
   public int columns;
   public BufferedImage[] sprites;
   
   public String path;
   
   public SpriteSheet(int width, int height, int rows, int columns, String path) throws IOException {
      path = "/"+path;
      System.out.println("SpriteSheet path:"+path);
      spriteSheet = ImageIO.read(Texture.class.getClass().getResource(path));
      this.width = width;
      this.height = height;
      this.rows = rows;
      this.columns = columns;
      this.sprites= new BufferedImage[rows * columns];
      this.path = path;
      
      for(int i = 0; i < rows; i++) {
         for(int j = 0; j < columns; j++) {
            sprites[(i * columns) + j] = spriteSheet.getSubimage(i * width, j * height, width, height);
         }
      }
   }
   
   public BufferedImage getImage(int row, int col){
       int index = (col+(row*columns));
       if(index<this.sprites.length){
           return this.sprites[index];
       }else
           
       return null;
   }

}