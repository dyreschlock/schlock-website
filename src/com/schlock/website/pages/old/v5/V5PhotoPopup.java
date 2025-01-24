package com.schlock.website.pages.old.v5;

import com.schlock.website.entities.blog.AbstractPost;
import org.apache.commons.lang.StringUtils;

public class V5PhotoPopup extends AbstractVersion5Page
{
    private AbstractPost post;
    private Integer imageNumber;

    Object onActivate(String param)
    {
        return onActivate(param, null);
    }

    Object onActivate(String p1, String p2)
    {
        post = getPost(p1);
        if (StringUtils.isNotBlank(p2))
        {
            imageNumber = Integer.parseInt(p2);
        }
        return true;
    }

    public AbstractPost getPost()
    {
        return post;
    }

    public Integer getImageNumber()
    {
        return imageNumber;
    }
}
