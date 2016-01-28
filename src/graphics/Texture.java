/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import base.SpriteBinder;

/**
 *
 * @author Bailey
 */
public class Texture {    
    
    public static Render floor = Texture.loadBitmap("floor.png");
    public static Render wall = Texture.loadBitmap("wall.png");
    public static Render gay = Texture.loadBitmap("gay.png");
    public static Render fish = Texture.loadBitmap("Core/seanFish.png");
    public static Render ruins_wall = Texture.loadBitmap("ruins-wall.png");
    public static Render ruins_floor = Texture.loadBitmap("ruins-floor.png");
    public static Render ladder = Texture.loadBitmap("ladder.png");
    
    public static Render loadBitmap(String path){
        return SpriteBinder.loadTexture("/"+path);
    }
    
    public static int getPixelsAt(Render render, int x, int y){
        return render.pixels[(x & render.width-1) + (y & render.height-1) * render.width];
    }
}
