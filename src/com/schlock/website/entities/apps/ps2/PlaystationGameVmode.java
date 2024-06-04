package com.schlock.website.entities.apps.ps2;

public enum PlaystationGameVmode
{
    NTSC("vmode/ntsc", "NTSC"),
    PAL("vmode/pal", "PAL"),
    MULTI("vmode/multi", "Multi");

    private final String vmode;
    private final String vmodeText;

    PlaystationGameVmode(String vmode, String vmodeText)
    {
        this.vmode = vmode;
        this.vmodeText = vmodeText;
    }

    public String vmode()
    {
        return this.vmode;
    }

    public String vmodeText()
    {
        return this.vmodeText;
    }

    public static PlaystationGameVmode getFromText(String text)
    {
        if(NTSC.vmode.equals(text))
        {
            return NTSC;
        }
        if (PAL.vmode.equals(text))
        {
            return PAL;
        }
        if (MULTI.vmode.equals(text))
        {
            return MULTI;
        }
        return null;
    }
}
