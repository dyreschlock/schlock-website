package com.schlock.website.entities.apps.ps2;

public enum PlaystationPlatform
{
    PS2("DVD", ".iso"),
    PS1("POPS", ".vcd");

    private final String folder;
    private final String filetype;

    PlaystationPlatform(String folder, String fileType)
    {
        this.folder = folder;
        this.filetype = fileType;
    }

    public String folder()
    {
        return this.folder;
    }

    public String fileType()
    {
        return this.filetype;
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
