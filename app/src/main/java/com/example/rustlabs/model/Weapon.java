package com.example.rustlabs.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

/**
 * Weapon POJO
 */
public class Weapon
{
    public static final String FIELD_DAMAGE = "damage";
    public static final String FIELD_AMMO_TYPE = "ammoType";
    public static final String FIELD_PICTURE = "picture";
    public static final String FIELD_TOP_LOCATION = "topLocation";

    private String name;
    private String ammoType;
    private String photo;
    private String topLocation;

    private int damage;
    // For if we want to display number of tips to user. No internal use planned
    private int numTips;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAmmoType()
    {
        return ammoType;
    }

    public void setAmmoType(String ammoType)
    {
        this.ammoType = ammoType;
    }

    public String getPhoto()
    {
        return photo;
    }

    public void setPhoto(String photo)
    {
        this.photo = photo;
    }

    public String getTopLocation()
    {
        return topLocation;
    }

    public void setTopLocation(String topLocation)
    {
        this.topLocation = topLocation;
    }

    public int getDamage()
    {
        return damage;
    }

    public void setDamage(int damage)
    {
        this.damage = damage;
    }

    public int getNumTips()
    {
        return numTips;
    }

    public void setNumTips(int numTips)
    {
        this.numTips = numTips;
    }

    public Weapon() {}

    public Weapon(String name, String ammoType, String photo, String topLocation, int damage,
                  int numTips)
    {
        this.name = name;
        this.ammoType = ammoType;
        this.photo = photo;
        this.topLocation = topLocation;
        this.damage = damage;
        this.numTips = numTips;
    }
}