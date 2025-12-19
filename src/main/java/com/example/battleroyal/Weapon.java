package com.example.battleroyal;

public abstract class Weapon {
    private String Name;
    private int Damage;
    private long Cooldown;
    private double ProjectileSpeed;
    protected long lastshoottime = 0;
    private int ammo = 5;
    private int maxammo = 5;
    private boolean isReloading = false;
    private long reloadStartTime = 0;
    private long reloadTime = 2_000_000_000;

    public Weapon(String Name, int Damage, long Cooldown, double ProjectileSpeed){
        this.Name = Name;
        this.Damage = Damage;
        this.Cooldown = Cooldown;
        this.ProjectileSpeed = ProjectileSpeed;
    }

    public boolean canShot(){
        if(isReloading){
            if(System.nanoTime()- reloadStartTime >= reloadTime){
                ammo = maxammo;
                isReloading = false;
            }
            else {
                return false;
            }
        }
        return ammo > 0 && (System.nanoTime() - lastshoottime >= Cooldown);
    }


    public void shot() {
        if (ammo > 0) {
            ammo--;
            lastshoottime = System.nanoTime();
            if (ammo == 0) reload();
        }
    }

    public void reload() {
        if (!isReloading) {
            isReloading = true;
            reloadStartTime = System.nanoTime();
        }
    }

    public boolean hasAmmo() {

        return ammo > 0;
    }

    public void setName(String Name){

        this.Name = Name;
    }
    public String getName(){
        return Name;
    }
    public void setDamage(int Damage){
        this.Damage = Damage;
    }
    public int getDamage(){
        return Damage;
    }
    public void setCooldown(long Cooldown){
        this.Cooldown = Cooldown;
    }
    public long getCooldown(){
        return Cooldown;
    }

    public void setProjectileSpeed(double projectileSpeed) {
        ProjectileSpeed = projectileSpeed;
    }
    public double getProjectileSpeed(){
        return ProjectileSpeed;
    }

    public void setMaxAmmo(int maxAmmo){

        this.maxammo = maxAmmo; reload();}

    public int getMaxAmmo(){
        return maxammo;
    }
    public int getAmmo(){
        return ammo;
    }
    public boolean isReloading(){
        return isReloading;
    }
    public void setReloadTime(long reloadTime){
        this.reloadTime = reloadTime;
    }
    public long getReloadTime(){
        return reloadTime;
    }
}