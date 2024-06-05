package com.schlock.website.components.apps.ps2;

import com.schlock.website.entities.apps.ps2.PlaystationPlatform;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class PlatformBar
{
    @Parameter
    @Property
    private PlaystationPlatform selectedPlatform;

    @Parameter
    @Property
    private String genre;

    @Parameter
    @Property
    private Boolean imageView;

    public PlaystationPlatform getPs2()
    {
        return PlaystationPlatform.PS2;
    }

    public PlaystationPlatform getPs1()
    {
        return PlaystationPlatform.PS1;
    }
}
