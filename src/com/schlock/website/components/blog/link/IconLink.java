package com.schlock.website.components.blog.link;

import com.schlock.website.entities.Icon;
import com.schlock.website.services.blog.IconManagement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class IconLink
{
    @Parameter(required = true)
    private Icon icon;

    @Parameter
    @Property
    private String redirectUrl;

    @Parameter
    @Property
    private String altTitleText;


    @Inject
    private IconManagement iconManagement;


    public String getIconUrl()
    {
        String dataUrl = iconManagement.createBase64ImgLink(icon);
        return dataUrl;
    }
}
