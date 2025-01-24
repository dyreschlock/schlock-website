package com.schlock.website.pages.old.v5;

import com.schlock.website.entities.blog.AbstractPost;

public class V5PhotoPopup extends AbstractVersion5Page
{
    private AbstractPost post;

    Object onActivate(String param)
    {
        post = getPost(param);

        return true;
    }

    public AbstractPost getPost()
    {
        return post;
    }
}
