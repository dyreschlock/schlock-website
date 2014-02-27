package com.schlock.website.services.database.blog.impl;

import com.schlock.website.entities.blog.Category;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.blog.PostDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PostDAOImpl extends BaseDAOImpl<Post> implements PostDAO
{
    private static final int TOP_RECENT = 5;

    public PostDAOImpl(Session session)
    {
        super(Post.class, session);
    }

    public Post getByUuid(String uuid)
    {
        String text = "from Post p " +
                        " where p.uuid = :uuid ";

        Query query = session.createQuery(text);
        query.setParameter("uuid", uuid);

        List list = query.list();
        if (list.size() > 1)
        {
            //log problem
        }

        if (list.isEmpty())
        {
            return null;
        }
        return (Post) list.get(0);
    }

    public Set<String> getAllUuids()
    {
        String text = "select p.uuid " +
                        " from Post p ";

        Query query = session.createQuery(text);

        List<String> list = query.list();

        Set<String> uuids = new HashSet<String>();
        uuids.addAll(list);

        return uuids;
    }

    public Post getMostRecentPost(boolean withUnpublished, Category category)
    {
        Query query;
        if(category == null)
        {
            String text = "from Post p ";

            if (!withUnpublished)
            {
                text += " where p.published is true ";
            }
            text += " order by p.createdGMT desc";

            query = session.createQuery(text);
        }
        else
        {
            String text = "from Post p " +
                    " join p.categories c " +
                    " where c.id = :categoryId ";

            if (!withUnpublished)
            {
                text += " where p.published is true ";
            }
            text += " order by p.createdGMT desc";

            query = session.createQuery(text);
            query.setParameter("categoryId", category.getId());
        }

        return (Post) singleResult(query);
    }

    public Post getNextPost(Post currentPost, boolean withUnpublished, Category category)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Post getPreviousPost(Post currentPost, boolean withUnplished, Category category)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Post> getRecentPosts(boolean withUnpublished)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Post> getRecentPinnedPosts(boolean withUnpublished)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Post> getAllPages(boolean withUnpublished)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
