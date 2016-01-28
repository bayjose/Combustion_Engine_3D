/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Loot;

import static Loot.EnumRarity.COMMON;
import java.util.Random;

/**
 *
 * @author Bailey
 */
public enum EnumRarity {
    COMMON(70.0f),
    UNCOMMON(20.0f),
    RARE(7.0f),
    LEGENDARY(2.0f),
    MYTHIC(1.0f);
    
    protected float rarity = 0.0f;
    static Random rand = new Random();
    EnumRarity(float rarity){
        this.rarity = rarity;
    }
    
    public static EnumRarity genRarity(){
        float number = (float)rand.nextInt(100)+(float)Math.random();
        if(number <= COMMON.rarity){
            return COMMON;
        }else if(number > COMMON.rarity && number <= COMMON.rarity + UNCOMMON.rarity){
            return UNCOMMON;
        }else if(number > COMMON.rarity + UNCOMMON.rarity && number <= COMMON.rarity + UNCOMMON.rarity + RARE.rarity){
            return RARE;
        }else if(number > COMMON.rarity + UNCOMMON.rarity + RARE.rarity && number <= COMMON.rarity + UNCOMMON.rarity + RARE.rarity + LEGENDARY.rarity){
            return LEGENDARY;
        }else{
            return MYTHIC;
        }
    }
    
    public static EnumRarity genRarity(float offset){
        float number = (float)rand.nextInt(100)+(float)Math.random();
        number += offset;
        if(number <= COMMON.rarity){
            return COMMON;
        }else if(number > COMMON.rarity && number <= COMMON.rarity + UNCOMMON.rarity){
            return UNCOMMON;
        }else if(number > COMMON.rarity + UNCOMMON.rarity && number <= COMMON.rarity + UNCOMMON.rarity + RARE.rarity){
            return RARE;
        }else if(number > COMMON.rarity + UNCOMMON.rarity + RARE.rarity && number <= COMMON.rarity + UNCOMMON.rarity + RARE.rarity + LEGENDARY.rarity){
            return LEGENDARY;
        }else{
            return MYTHIC;
        }
    }
}
