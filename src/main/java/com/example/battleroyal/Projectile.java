package com.example.battleroyal;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Projectile extends Circle {
    private double velocityX;
    private double velocityY;
    private int damage;
    private boolean active;
    private Fighter shooter;

    public Projectile(double startX, double startY, double targetX, double targetY, Weapon weapon, Fighter shooter) {
        super(startX, startY, 8);
        this.shooter = shooter;
        this.damage = weapon.getDamage();
        this.active = true;
        switch (weapon.getName()) {
            case "Bow":
                setFill(Color.LIMEGREEN);
                break;
            case "MagicStaff":
                setFill(Color.DEEPSKYBLUE);
                break;
            case "Canon":
                setFill(Color.ORANGERED);
                break;
            default:
                setFill(Color.GOLD);
        }
        setStroke(Color.BLACK);
        setStrokeWidth(1);
        double dx = targetX - startX;
        double dy = targetY - startY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < 1) distance = 1;

        {
            this.velocityX = (dx / distance) * weapon.getProjectileSpeed();
            this.velocityY = (dy / distance) * weapon.getProjectileSpeed();
        }
    }
    public void update() {

        if (!active) return;
        setCenterX(getCenterX() + velocityX);
        setCenterY(getCenterY() + velocityY);

        if (getCenterX() < 0 || getCenterX() > 600 ||
                getCenterY() < 0 || getCenterY() > 400) {
            active = false;
        }

    }

    public int getDamage() {
        return damage;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    public Fighter getShooter() {
        return shooter;
    }
    public boolean collidesWith(Fighter fighter) {
        double fighterCenterX = fighter.getX() + 20;
        double fighterCenterY = fighter.getY() + 20;
        double distance = Math.sqrt(Math.pow(getCenterX() - fighterCenterX, 2) +
                Math.pow(getCenterY() - fighterCenterY, 2));
        return distance < (getRadius() + 20);
    }
}
