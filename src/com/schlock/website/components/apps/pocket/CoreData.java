package com.schlock.website.components.apps.pocket;

import com.schlock.website.entities.apps.pocket.PocketCore;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class CoreData
{
    @Parameter
    @Property
    private PocketCore core;


    public String getImageSrc()
    {
        return core.getImageLink();
    }

    public boolean isHasImage()
    {
        return !core.isFakeArcadeCore();
    }
}
