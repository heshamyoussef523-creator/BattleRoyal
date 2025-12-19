package com.example.battleroyal;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.lang.String;

import java.util.ArrayList;

public abstract class Fighter {
    protected String name;
    protected int health;
    private Weapon weapon;
    protected int speed;
    protected double x;
    protected double y;
    private ArrayList<Weapon> weapons;
    private int currentWeaponIndex = 0;
    protected Rectangle sprite;
    protected Color color;

    public Fighter(String name, int health, Weapon initialWeapon, int speed, double x, double y, ArrayList<Weapon> weapons, Color color) {
        this.name = name;
        this.health = health;
        this.weapon = initialWeapon;
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.weapons = weapons;
        if (this.weapons.isEmpty()) {
            this.weapons.add(initialWeapon);

        }
        this.sprite = new Rectangle(40, 40);
        this.sprite.setFill(color);
        this.sprite.setStroke(Color.BROWN);
        this.sprite.setStrokeWidth(2);
        updateSpritePosition();

    }

    public Rectangle getSprite() {
        return sprite;
    }
    public void updateSpritePosition() {
        if (sprite != null) {
            sprite.setX(x);
            sprite.setY(y);
        }
    } public Projectile shootProjectile(Fighter target) {
        Weapon weapon = weapons.get(currentWeaponIndex);
        if (!weapon.canShot()) return null;
        weapon.shot();
        if (!weapon.hasAmmo()) {
            weapon.reload();
        }
        return new Projectile(
                getX() + 20, getY() + 20, target.getX() + 20, target.getY() + 20,  weapon, this
        );
}
    public String getName() {
        return name;
    }    public void setName(String name) {
        this.name = name;
    }
    public int getHealth() {
        return health;
    } public void setHealth(int health) {
        this.health = health;
    }
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
        updateSpritePosition();
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
        updateSpritePosition();
    }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public Weapon getWeapon() {
        return weapon;
    }
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        updateSpritePosition();
    }
    public boolean isDied() {
        return health <= 0;
    }
    public void healthDamage(int damage) {
        health -= damage;
        if (health<0)
        {health=0;}

    }
    public void moveUp() {if(y>0) {move(0, -speed);}}
    public void moveDown() {if (y<360)
    { move(0, speed);}
    }
    public void moveLeft() {if (x>0)
    {move(-speed, 0);}
    }
    public void moveRight() {if(x<550)
    {move(speed, 0);}
    }
    public void weaponChange(int index) {
        if (index >= 0 && index < weapons.size()) { weapon = weapons.get(index); currentWeaponIndex = index;}
    }
    public void nextWeapon() {
        currentWeaponIndex++;
        if (currentWeaponIndex >= weapons.size()) {
            currentWeaponIndex = 0;
        }
        weapon = weapons.get(currentWeaponIndex);
    } public ArrayList<Weapon> getWeapons() {
        return weapons;
    }
    public abstract void attack(Fighter target);
    public abstract int getDamage();
    }


