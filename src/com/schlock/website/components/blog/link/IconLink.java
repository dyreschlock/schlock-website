package com.schlock.website.components.blog.link;

import com.schlock.website.entities.Icon;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;

public class IconLink
{
    @Parameter(required = true)
    private Icon icon;

    @Parameter(required = true)
    private Boolean dark;

    @Parameter
    @Property
    private String redirectUrl;

    @Parameter
    @Property
    private String altTitleText;


    @Inject
    private AssetSource assetSource;


    public String getIconUrl()
    {
        if(dark)
        {
            return icon.getDarkIconPath(assetSource);
        }
        return icon.getLightIconPath(assetSource);
    }
}
