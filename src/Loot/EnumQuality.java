/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Loot;

import java.util.Random;

/**
 *
 * @author Bailey
 */
public enum EnumQuality {
    ROUGH(50.0f, 1.0f),//0% clear
    CLEAN(30.0f, 0.9f),//10% clear
    PURE(12.0f, 0.75f),//25% clear
    TRANSLUSENT(6.0f, 0.5f),//50% clear
    IRIDESCENT(2.0f, 0.5f);//60% clear with glow animation
    
    protected float quality = 0.0f;
    protected final float translucent;
    static Random rand = new Random();
    EnumQuality(float rarity, float translucent){
        this.quality = rarity;
        this.translucent = translucent;
    }
    
    public static EnumQuality genRarity(){
        float number = (float)rand.nextInt(100)+(float)Math.random();
        if(number <= ROUGH.quality){
            return ROUGH;
        }else if(number > ROUGH.quality && number <= ROUGH.quality + CLEAN.quality){
            return CLEAN;
        }else if(number > ROUGH.quality + CLEAN.quality && number <= ROUGH.quality + CLEAN.quality + PURE.quality){
            return PURE;
        }else if(number > ROUGH.quality + CLEAN.quality + PURE.quality && number <= ROUGH.quality + CLEAN.quality + PURE.quality + TRANSLUSENT.quality){
            return TRANSLUSENT;
        }else{
            return IRIDESCENT;
        }
    }
}
