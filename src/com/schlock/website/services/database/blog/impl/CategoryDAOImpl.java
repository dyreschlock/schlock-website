package com.schlock.website.services.database.blog.impl;

import com.schlock.website.entities.blog.Category;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.blog.CategoryDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class CategoryDAOImpl extends BaseDAOImpl<Category> implements CategoryDAO
{
    public CategoryDAOImpl(Session session)
    {
        super(Category.class, session);
    }

    public List<Category> getTopCategoriesInOrder()
    {
        String text = "from Category " +
                        " where parentId is null " +
                        " order by ordering ";

        Query query = session.createQuery(text);
        return query.list();
    }

    public List<Category> getSubcategoriesInOrder(Long categoryId)
    {
        String text = "from Category " +
                        " where parentId = :categoryId " +
                        " order by ordering ";

        Query query = session.createQuery(text);
        query.setParameter("categoryId", categoryId);
        return query.list();
    }
}