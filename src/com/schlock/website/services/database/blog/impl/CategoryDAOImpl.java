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

    public List<Category> getAllInOrder()
    {
        String text = "from Category " +
                      " order by parentId, ordering ";

        Query query = session.createQuery(text);
        return query.list();
    }

    public List<Object[]> getWithPostCounts(boolean withUnpublished)
    {
        String text = "select c.id, count(p.id) " +
                        " from Post p " +
                        " join p.categories c ";
        if (!withUnpublished)
        {
            text += " where p.published is true ";
        }
        text += " group by c.id ";

        Query query = session.createQuery(text);
        List<Object[]> list = query.list();
        return list;
    }
}
