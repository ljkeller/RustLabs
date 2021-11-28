package com.example.rustlabs.model;

public class Structure
{
    public static final String FIELD_HEALTH = "health";
    public static final String FIELD_RAID_C4 = "raidC4";
    public static final String FIELD_RAID_ROCKET = "raidRocket";
    public static final String FIELD_RAID_SATCHEL = "raidSatchel";
    public static final String FIELD_RAID_EXPLOSIVE_AMMO = "raidExplosiveAmmo";

    private String name;
    private String photo;

    private int raidC4;
    private int raidRocket;
    private int raidSatchel;
    private int raidExplosiveAmmo;
    private int health;
    private int numTips;

    public Structure() {}

    public Structure(String name, int raidC4, int raidRocket, int raidSatchel, int raidExplosiveAmmo, int health, int numTips, String photo)
    {
        this.name = name;
        this.raidC4 = raidC4;
        this.raidRocket = raidRocket;
        this.raidSatchel = raidSatchel;
        this.raidExplosiveAmmo = raidExplosiveAmmo;
        this.health = health;
        this.numTips = numTips;
        this.photo = photo;
    }

    public int getNumTips()
    {
        return numTips;
    }

    public void setNumTips(int numTips)
    {
        this.numTips = numTips;
    }

    public String getPhoto()
    {
        return photo;
    }

    public void setPhoto(String photo)
    {
        this.photo = photo;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getRaidC4()
    {
        return raidC4;
    }

    public void setRaidC4(int raidC4)
    {
        this.raidC4 = raidC4;
    }

    public int getRaidRocket()
    {
        return raidRocket;
    }

    public void setRaidRocket(int raidRocket)
    {
        this.raidRocket = raidRocket;
    }

    public int getRaidSatchel()
    {
        return raidSatchel;
    }

    public void setRaidSatchel(int raidSatchel)
    {
        this.raidSatchel = raidSatchel;
    }

    public int getRaidExplosiveAmmo()
    {
        return raidExplosiveAmmo;
    }

    public void setRaidExplosiveAmmo(int raidExplosiveAmmo)
    {
        this.raidExplosiveAmmo = raidExplosiveAmmo;
    }

    public int getHealth()
    {
        return health;
    }

    public void setHealth(int health)
    {
        this.health = health;
    }
}
