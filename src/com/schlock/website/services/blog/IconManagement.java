package com.schlock.website.services.blog;

import com.schlock.website.entities.Icon;

public interface IconManagement
{
    public String createBase64ImgLink(Icon icon);
    public String convertIconToBase64(Icon icon);

    public String getIconLink(Icon icon);
}
