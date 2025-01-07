package com.schlock.website.pages.old.v1;

import com.schlock.website.entities.blog.AbstractPost;

import java.util.Collections;
import java.util.List;

public abstract class AbstractVersion1Page
{
    public List<AbstractPost> getPosts()
    {
        return Collections.EMPTY_LIST;
    }

    public String getPage()
    {
        return "";
    }
}
