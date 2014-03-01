package com.schlock.website.services.database.blog.impl;

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

    public Post getByWpid(String wpid)
    {
        String text = "from Post p where p.wpid = :wpid";
        Query query = session.createQuery(text);
        query.setParameter("wpid", wpid);

        return (Post) singleResult(query);
    }

    public Post getByMtid(String mtid)
    {
        String text = "from Post p where p.mtid = :mtid";
        Query query = session.createQuery(text);
        query.setParameter("mtid", mtid);

        return (Post) singleResult(query);
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

    public Post getMostRecentPost(boolean withUnpublished, Long categoryId)
    {
        Query query;
        if(categoryId == null)
        {
            String text = "select p " +
                            " from Post p ";

            if (!withUnpublished)
            {
                text += " where p.published is true ";
            }
            text += " order by p.createdGMT desc";

            query = session.createQuery(text);
        }
        else
        {
            String text = "select p " +
                    " from Post p " +
                    " join p.categories c " +
                    " where c.id = :categoryId ";

            if (!withUnpublished)
            {
                text += " and p.published is true ";
            }
            text += " order by p.createdGMT desc";

            query = session.createQuery(text);
            query.setParameter("categoryId", categoryId);
        }

        return (Post) singleResult(query);
    }

    public Post getNextPost(Post currentPost, boolean withUnpublished, Long categoryId)
    {
        String text = "select p " +
                        " from Post p";
        if(categoryId != null)
        {
            text += " join p.categories c ";
        }
        text += " where p.createdGMT > :currentCreated ";
        if(categoryId != null)
        {
            text += " and c.id = :categoryId ";
        }
        if (!withUnpublished)
        {
            text += " and p.published is true ";
        }
        text += " order by p.createdGMT asc";

        Query query = session.createQuery(text);
        query.setTimestamp("currentCreated", currentPost.getCreatedGMT());
        if(categoryId != null)
        {
            query.setParameter("categoryId", categoryId);
        }

        return (Post) singleResult(query);
    }

    public Post getPreviousPost(Post currentPost, boolean withUnpublished, Long categoryId)
    {
        String text = "select p" +
                        " from Post p";
        if(categoryId != null)
        {
            text += " join p.categories c ";
        }
        text += " where p.createdGMT < :currentCreated ";
        if(categoryId != null)
        {
            text += " and c.id = :categoryId ";
        }
        if (!withUnpublished)
        {
            text += " and p.published is true ";
        }
        text += " order by p.createdGMT desc";

        Query query = session.createQuery(text);
        query.setTimestamp("currentCreated", currentPost.getCreatedGMT());
        if(categoryId != null)
        {
            query.setParameter("categoryId", categoryId);
        }

        return (Post) singleResult(query);
    }

    public List<Post> getRecentPosts(boolean withUnpublished, Long categoryId)
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
