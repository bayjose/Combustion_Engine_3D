/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Loot;

import base.SpriteSheet;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Bailey
 */
public class GemRegistry{
    private static LinkedList<Material> common = new LinkedList<Material>();
    private static LinkedList<Material> uncommon = new LinkedList<Material>();
    private static LinkedList<Material> rare = new LinkedList<Material>();
    private static LinkedList<Material> legendary = new LinkedList<Material>();
    private static LinkedList<Material> mythic = new LinkedList<Material>();
    
    private static SpriteSheet resources;
    
    private final static Random rand = new Random();
    public GemRegistry(){
        System.out.println("----------------------------------------");
        System.out.println("Creating Gem Registry");
        try{
            resources = new SpriteSheet(64, 64, 16, 16, "gems.png");
            System.out.println("Success.");
        }catch(IOException e){
            System.err.println("Error loading file:"+"gems.png");
        }
        common.add(new Material("Emerald",0,0));
        common.add(new Material("Saphire",0,1));
        common.add(new Material("Ruby",1,1));
        common.add(new Material("Amathyst",2,1));
        common.add(new Material("Value Gem",1,0));
        uncommon.add(new Material("Turquise",0,2));
        rare.add(new Material("Aquamariene",0,3));
        legendary.add(new Material("Diamond",0,4));
        mythic.add(new Material("Fire Opal",0,5));
    }
    
    public static Gem genRandomGem(){
        EnumRarity rareity = EnumRarity.genRarity();
        LinkedList<Material> pool;
        if(rareity.equals(EnumRarity.COMMON)){
            pool = common;
        }else if(rareity.equals(EnumRarity.UNCOMMON)){
            pool = uncommon;
        }else if(rareity.equals(EnumRarity.RARE)){
            pool = rare;
        }else if(rareity.equals(EnumRarity.LEGENDARY)){
            pool = legendary;   
        }else{
            pool = mythic;
        }
        EnumQuality quality = EnumQuality.genRarity();
        int index = rand.nextInt(pool.size());
//        System.out.println(rareity.name()+" "+quality.name()+" "+pool.get(index).name);
        return new Gem(rareity,quality,resources.getImage((int)pool.get(index).position.getX(), (int)pool.get(index).position.getY()), pool.get(index).name);
    }
    
    public static void renderAllGems(int x, int y, Graphics g){
        LinkedList<Gem> composite = new LinkedList<Gem>();
        for (Material common1 : common) {
            composite.add(new Gem(EnumRarity.COMMON,EnumQuality.IRIDESCENT,resources.getImage((int)common1.position.getX(), (int)common1.position.getY()), common1.name));
        }
        for (Material common1 : uncommon) {
            composite.add(new Gem(EnumRarity.UNCOMMON,EnumQuality.IRIDESCENT,resources.getImage((int)common1.position.getX(), (int)common1.position.getY()), common1.name));
        }
        for (Material common1 : rare) {
            composite.add(new Gem(EnumRarity.RARE,EnumQuality.IRIDESCENT,resources.getImage((int)common1.position.getX(), (int)common1.position.getY()), common1.name));
        }
        for (Material common1 : legendary) {
            composite.add(new Gem(EnumRarity.LEGENDARY,EnumQuality.IRIDESCENT,resources.getImage((int)common1.position.getX(), (int)common1.position.getY()), common1.name));
        }
        for (Material common1 : mythic) {
            composite.add(new Gem(EnumRarity.MYTHIC,EnumQuality.IRIDESCENT,resources.getImage((int)common1.position.getX(), (int)common1.position.getY()), common1.name));
        }
        
        for (int i = 0; i< composite.size(); i++) {
            composite.get(i).RenderAt(x + i * 96, y, g);
        }
    }
}
