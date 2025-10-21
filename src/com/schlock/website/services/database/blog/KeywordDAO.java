package com.schlock.website.services.database.blog;

import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface KeywordDAO extends BaseDAO<Keyword>
{
    Keyword getByName(String name);

    List<Object[]> getAllAvailable();

    List<Keyword> getTopInOrder();

    List<Keyword> getSubInOrder(Keyword keyword);
}
