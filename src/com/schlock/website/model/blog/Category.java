package com.schlock.website.model.blog;

import com.schlock.website.model.Persisted;

public class Category extends Persisted
{
    private String name;

    private Category parent;
    
    private boolean top;
    private int order;
}
