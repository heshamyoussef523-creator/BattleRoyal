package com.example.battleroyal;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Mage extends Fighter {
    public Mage(String name, double x, double y, ArrayList<Weapon> weapons) {
        super(name, 100, new MagicStaff(), 6, x, y, weapons, Color.BLUE);
    }
    @Override
    public void attack(com.example.battleroyal.Fighter target){
        Weapon currentWeapon=getWeapon();
        target.healthDamage(currentWeapon.getDamage());
        System.out.println(getName() + " attacked " + target.getName() + " with " + currentWeapon.getName()+" causing " + currentWeapon.getDamage() + " damage!");}
        @Override
        public int getDamage(){
            return getWeapon().getDamage();
        }
    }
