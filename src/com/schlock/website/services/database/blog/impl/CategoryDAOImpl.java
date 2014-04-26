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

    public Category getByUuid(String uuid)
    {
        String text = "from Category " +
                      " where uuid = :uuid ";

        Query query = session.createQuery(text);
        query.setParameter("uuid", uuid);

        return (Category) singleResult(query);
    }

    public List<Category> getAllInOrder()
    {
        String text = "from Category " +
                      " order by parentId, ordering ";

        Query query = session.createQuery(text);
        return query.list();
    }

    public List<Category> getTopInOrder()
    {
        String text = "from Category " +
                        " where parent is null " +
                        " order by ordering ";

        Query query = session.createQuery(text);
        return query.list();
    }

    public List<Category> getSubInOrder(Long categoryId)
    {
        String text = "select child " +
                        " from Category child " +
                        " join child.parent par " +
                        " where par.id = :categoryId " +
                        " order by child.ordering ";

        Query query = session.createQuery(text);
        query.setLong("categoryId", categoryId);

        return query.list();
    }
}
