package com.schlock.website.entities.apps.ps2;

public enum PlaystationGameScan
{
    x240p("scan/240p", "240p"),
    x480i("scan/480i", "480i"),
    x480p("scan/480p", "480p"),
    x480p1("scan/480p1", "480p1"),
    x480p2("scan/480p2", "480p2"),
    x576i("scan/576i", "576i"),
    x1080i("scan/1080i", "1080i");

    private final String scan;
    private final String scanText;

    PlaystationGameScan(String scan, String scanText)
    {
        this.scan = scan;
        this.scanText = scanText;
    }

    public String scan()
    {
        return this.scan;
    }

    public String scanText()
    {
        return this.scanText;
    }

    public static PlaystationGameScan getFromText(String text)
    {
        if (x240p.scan.equals(text))
        {
            return x240p;
        }
        if (x480p.scan.equals(text))
        {
            return x480p;
        }
        if (x480p1.scan.equals(text))
        {
            return x480p1;
        }
        if (x480p2.scan.equals(text))
        {
            return x480p2;
        }
        if (x576i.scan.equals(text))
        {
            return x576i;
        }
        if (x1080i.scan.equals(text))
        {
            return x1080i;
        }
        return null;
    }
}
