package com.schlock.website.services.database.blog;

import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.services.database.BaseDAO;

public interface KeywordDAO extends BaseDAO<Keyword>
{
    public Keyword getByName(String name);
}
