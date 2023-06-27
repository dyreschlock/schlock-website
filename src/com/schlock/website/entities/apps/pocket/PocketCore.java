package com.schlock.website.entities.apps.pocket;

public class PocketCore
{
    public static final String CAT_CONSOLE = "Console";
    public static final String CAT_HANDHELD = "Handheld";
    public static final String CAT_COMPUTER = "Computer";
    public static final String CAT_ARCADE = "Arcade";
    public static final String CAT_ARCADE_MULTI = "Arcade Multi";

    private String namespace;
    private String name;
    private String category;
    private String manufacturer;
    private String year;

    public String getNamespace()
    {
        return namespace;
    }

    public void setNamespace(String namespace)
    {
        this.namespace = namespace;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getManufacturer()
    {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = manufacturer;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }
}
