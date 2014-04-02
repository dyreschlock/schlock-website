package com.schlock.website.components.blog.link;

import com.schlock.website.entities.Icon;
import com.schlock.website.services.blog.ImageManagement;
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
    private ImageManagement imageManagement;


    public String getIconUrl()
    {
        String dataUrl = imageManagement.createBase64ImgLink(icon);
        return dataUrl;
    }
}
