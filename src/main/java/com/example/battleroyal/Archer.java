package com.example.battleroyal;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Archer extends Fighter{
    public Archer(String name, double x, double y, ArrayList<Weapon> weapons) {
        super(name, 80, weapons.get(0), 2, x, y, weapons, Color.BROWN);
    }
    @Override
    public void attack(Fighter target) {
        Weapon currentWeapon = getWeapon();
        target.healthDamage(currentWeapon.getDamage());
        System.out.println(getName() + " attacked " + target.getName() + " with " + currentWeapon.getName() + " causing " + currentWeapon.getDamage() + " damage!");
    }
        @Override
        public int getDamage(){
            return getWeapon().getDamage();
        }
    }

