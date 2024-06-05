package com.schlock.website.components.apps.ps2;

import com.schlock.website.entities.apps.ps2.PlaystationPlatform;
import com.schlock.website.pages.apps.ps2.Index;
import org.apache.tapestry5.annotations.Parameter;

public class PlatformPanel
{
    @Parameter
    private PlaystationPlatform platform;

    @Parameter
    private String genre;

    @Parameter
    private Boolean imageView;


    public String getPs2Link()
    {
        if(PlaystationPlatform.PS2.equals(platform))
        {
            return Index.getPageLink(imageView, null, genre);
        }
        return Index.getPageLink(imageView, PlaystationPlatform.PS2, genre);
    }

    public String getPs1Link()
    {
        if (PlaystationPlatform.PS1.equals(platform))
        {
            return Index.getPageLink(imageView, null, genre);
        }
        return Index.getPageLink(imageView, PlaystationPlatform.PS1, genre);
    }
}
