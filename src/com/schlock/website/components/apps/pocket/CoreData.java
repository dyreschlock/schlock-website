package com.schlock.website.components.apps.pocket;

import com.schlock.website.entities.apps.pocket.PocketCore;
import org.apache.tapestry5.annotations.Parameter;

public class CoreData
{
    @Parameter
    private PocketCore core;


    public String getImageSrc()
    {
        return core.getImageLink();
    }
}
