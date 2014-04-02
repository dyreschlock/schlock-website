package com.schlock.website.services.blog;

import com.schlock.website.entities.Icon;

public interface ImageManagement
{
    public String createBase64ImgLink(Icon icon);
    public String convertIconToBase64(Icon icon);
}
