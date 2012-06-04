package com.schlock.website.model.blog;

import com.schlock.website.model.Persisted;

import java.util.Date;
import java.util.Set;

public class Post extends Persisted
{
    private String title;
    private Date created;
    
    private String text;
    private String html;

    private Set<Category> categories;
}
