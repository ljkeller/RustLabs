package com.example.rustlabs.model;

public class Armor
{
    public static final String FIELD_PROTECTION_PROJECTILE = "protectionProjectile";
    public static final String FIELD_PROTECTION_MELEE = "protectionMelee";
    public static final String FIELD_PROTECTION_RADIATION = "protectionRadiation";
    public static final String FIELD_PROTECTION_COLD = "protectionCold";
    public static final String FIELD_CRAFT_COST = "craftCost";
    public static final String FIELD_TOP_LOCATION = "topLocation";

    private String name;
    private String topLocation;
    private String craftCost;
    private String photo;

    private int protectionProjectile;
    private int protectionMelee;
    private int protectionRadiation;
    private int protectionCold;

    public Armor () {}

    public Armor(String name, String topLocation, int protectionProjectile, int protectionMelee, int protectionRadiation, int protectionCold)
    {
        this.name = name;
        this.topLocation = topLocation;
        this.protectionProjectile = protectionProjectile;
        this.protectionMelee = protectionMelee;
        this.protectionRadiation = protectionRadiation;
        this.protectionCold = protectionCold;
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

    public String getTopLocation()
    {
        return topLocation;
    }

    public void setTopLocation(String topLocation)
    {
        this.topLocation = topLocation;
    }

    public String getCraftCost()
    {
        return craftCost;
    }

    public void setCraftCost(String craftCost)
    {
        this.craftCost = craftCost;
    }

    public int getProtectionProjectile()
    {
        return protectionProjectile;
    }

    public void setProtectionProjectile(int protectionProjectile)
    {
        this.protectionProjectile = protectionProjectile;
    }

    public int getProtectionMelee()
    {
        return protectionMelee;
    }

    public void setProtectionMelee(int protectionMelee)
    {
        this.protectionMelee = protectionMelee;
    }

    public int getProtectionRadiation()
    {
        return protectionRadiation;
    }

    public void setProtectionRadiation(int protectionRadiation)
    {
        this.protectionRadiation = protectionRadiation;
    }

    public int getProtectionCold()
    {
        return protectionCold;
    }

    public void setProtectionCold(int protectionCold)
    {
        this.protectionCold = protectionCold;
    }
}
