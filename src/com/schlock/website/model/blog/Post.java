package com.schlock.website.model.blog;

import com.schlock.website.model.Persisted;

import java.util.Date;
import java.util.Set;

public abstract class Post extends Persisted
{
    private String uuid;

    private String title;
    private Date created;

    private boolean published;
    private boolean pinned;
    private boolean page;

    private Set<Category> categories;


    public boolean isPhoto()
    {
        return this instanceof PhotoPost;
    }

    public boolean isText()
    {
        return this instanceof TextPost;
    }
}
