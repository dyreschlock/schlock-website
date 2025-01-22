package com.schlock.website.entities.apps.ps2;

public enum PlaystationPlatform
{
    PS2("DVD", ".iso", ""),
    PS1("POPS", ".vcd", ""),
    DC("", "gdi", "Sega_-_Dreamcast"),
    GC("", "rvz", "");

    private final String IMAGE_LINK = "img/%s.png";

    private final String folder;
    private final String filetype;
    private final String boxartRepoName;

    PlaystationPlatform(String folder, String fileType, String boxartRepoName)
    {
        this.folder = folder;
        this.filetype = fileType;
        this.boxartRepoName = boxartRepoName;
    }

    public String folder()
    {
        return this.folder;
    }

    public String fileType()
    {
        return this.filetype;
    }

    public String boxartRepoName()
    {
        return boxartRepoName;
    }

    public String gameImageFolder()
    {
        String name = name().toLowerCase();
        if (PS1.equals(this))
        {
            name = PS2.name().toLowerCase();
        }
        return name;
    }

    public String getImagePath()
    {
        String imageLink = String.format(IMAGE_LINK, name().toLowerCase());
        return imageLink;
    }

    public static PlaystationPlatform getFromText(String text)
    {
        for(PlaystationPlatform value : PlaystationPlatform.values())
        {
            if (value.name().equalsIgnoreCase(text))
            {
                return value;
            }
        }
        return null;
    }
}
