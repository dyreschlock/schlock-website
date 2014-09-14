package com.schlock.website.services.database.blog.impl;

import com.schlock.website.entities.blog.AbstractCategory;
import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.blog.CategoryDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class CategoryDAOImpl extends BaseDAOImpl<AbstractCategory> implements CategoryDAO
{
    public CategoryDAOImpl(Session session)
    {
        super(AbstractCategory.class, session);
    }

    public AbstractCategory getByUuid(String uuid)
    {
        String text = "from AbstractCategory " +
                      " where uuid = :uuid ";

        Query query = session.createQuery(text);
        query.setParameter("uuid", uuid);

        return (AbstractCategory) singleResult(query);
    }

    public PostCategory getFirstCategory()
    {
        String text = "from PostCategory " +
                " where parent is null " +
                " order by ordering ";

        Query query = session.createQuery(text);
        query.setMaxResults(1);

        return (PostCategory) singleResult(query);
    }

    public List<PostCategory> getAllInOrder()
    {
        String text = "from PostCategory " +
                      " order by parentId, ordering ";

        Query query = session.createQuery(text);
        return query.list();
    }

    public List<PostCategory> getTopInOrder()
    {
        String text = "from PostCategory " +
                        " where parent is null " +
                        " order by ordering ";

        Query query = session.createQuery(text);
        return query.list();
    }

    public List<PostCategory> getSubInOrder(Long categoryId)
    {
        String text = "select child " +
                        " from PostCategory child " +
                        " join child.parent par " +
                        " where par.id = :categoryId " +
                        " order by child.ordering ";

        Query query = session.createQuery(text);
        query.setLong("categoryId", categoryId);

        return query.list();
    }
}
