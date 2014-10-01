package com.schlock.website.services.database.blog.impl;

import com.schlock.website.entities.blog.*;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.blog.PostDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.*;

public class PostDAOImpl extends BaseDAOImpl<AbstractPost> implements PostDAO
{
    public PostDAOImpl(Session session)
    {
        super(AbstractPost.class, session);
    }

    public AbstractPost getByUuid(String uuid)
    {
        String text = "from AbstractPost p " +
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
        return (AbstractPost) list.get(0);
    }

    public AbstractPost getByWpid(String wpid)
    {
        String text = "from AbstractPost p where p.wpid = :wpid";
        Query query = session.createQuery(text);
        query.setParameter("wpid", wpid);

        return (AbstractPost) singleResult(query);
    }

    public AbstractPost getByMtid(String mtid)
    {
        String text = "from AbstractPost p where p.mtid = :mtid";
        Query query = session.createQuery(text);
        query.setParameter("mtid", mtid);

        return (AbstractPost) singleResult(query);
    }

    public AbstractPost getByGalleryName(String galleryName)
    {
        String text = "from AbstractPost p where p.galleryName = :galleryName";
        Query query = session.createQuery(text);
        query.setParameter("galleryName", galleryName);

        return (AbstractPost) singleResult(query);
    }

    public Set<String> getAllUuids()
    {
        String text = "select p.uuid " +
                        " from AbstractPost p ";

        Query query = session.createQuery(text);

        List<String> list = query.list();

        Set<String> uuids = new HashSet<String>();
        uuids.addAll(list);

        return uuids;
    }

    public Post getMostRecentPost(boolean withUnpublished, Long categoryId)
    {
        String selectClause = "select p from Post p ";
        String orderByClause = " order by p.created desc";

        Query query = createQuery(TOP_RECENT,
                                    null,
                                    null,
                                    withUnpublished,
                                    categoryId,
                                    null,
                                    null,
                                    selectClause,
                                    null,
                                    orderByClause);

        return (Post) singleResult(query);
    }

    public Post getMostRecentPostWithGallery(boolean withUnpublished, Long categoryId)
    {
        String selectClause = "select p from Post p ";
        String whereClause = " p.galleryName is not null ";
        String orderByClause = " order by p.created desc";

        Query query = createQuery(TOP_RECENT,
                                    null,
                                    null,
                                    withUnpublished,
                                    categoryId,
                                    null,
                                    null,
                                    selectClause,
                                    Arrays.asList(whereClause),
                                    orderByClause);

        return (Post) singleResult(query);
    }

    public Post getMostRecentPostWithGallery(boolean withUnpublished, Integer year, Integer month)
    {
        String selectClause = "select p from Post p ";
        String whereClause = " p.galleryName is not null ";
        String orderByClause = " order by p.created desc ";

        Query query = createQuery(TOP_RECENT,
                                    year,
                                    month,
                                    withUnpublished,
                                    null,
                                    null,
                                    null,
                                    selectClause,
                                    Arrays.asList(whereClause),
                                    orderByClause);

        return (Post) singleResult(query);
    }

    public List<Post> getMostRecentPosts(Integer postCount, boolean withUnpublished, Long categoryId)
    {
        return getRecentPostsByYearMonth(postCount, withUnpublished, null, null, categoryId);
    }

    public List<Post> getMostRecentPinnedPosts(Integer postCount, boolean withUnpublished, Long categoryId)
    {
        return getRecentPinnedPostsByYearMonth(postCount, withUnpublished, null, null, categoryId);
    }

    public List<Post> getMostRecentPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month)
    {
        return getRecentPostsByYearMonth(postCount, withUnpublished, year, month, null);
    }

    public List<Post> getMostRecentPinnedPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month)
    {
        return getRecentPinnedPostsByYearMonth(postCount, withUnpublished, year, month, null);
    }

    private Query nextPostQuery(Integer postCount, AbstractPost currentPost, Class clazz, boolean withUnpublished, Long categoryId, Long keywordId, Set<Long> excludePosts)
    {
        String classType = "Post";
        if (clazz != null)
        {
            classType = clazz.getSimpleName();
            withUnpublished = true;
        }

        String selectClause = "select p from " + classType + " p ";
        String extraWhereClause = " p.created > :currentCreated ";
        String orderByClause = " order by p.created asc";

        Query query = createQuery(postCount,
                                    null,
                                    null,
                                    withUnpublished,
                                    categoryId,
                                    keywordId,
                                    excludePosts,
                                    selectClause,
                                    Arrays.asList(extraWhereClause),
                                    orderByClause);

        query.setTimestamp("currentCreated", currentPost.getCreated());
        return query;
    }

    public Post getNextPost(AbstractPost currentPost, boolean withUnpublished, Long categoryId)
    {
        Query query = nextPostQuery(ONLY_ONE, currentPost, null, withUnpublished, categoryId, null, null);
        return (Post) singleResult(query);
    }

    public List<AbstractPost> getNextPosts(Integer postCount, AbstractPost currentPost, Class clazz, boolean withUnpublished, Long categoryId, Long keywordId, Set<Long> excludePosts)
    {
        Query query = nextPostQuery(postCount, currentPost, clazz, withUnpublished, categoryId, keywordId, excludePosts);
        return query.list();
    }


    private Query previousPostQuery(Integer postCount, AbstractPost currentPost, Class clazz, boolean withUnpublished, Long categoryId, Long keywordId, Set<Long> excludePosts)
    {
        String classType = "Post";
        if (clazz != null)
        {
            classType = clazz.getSimpleName();
            withUnpublished = true;
        }

        String selectClause = "select p from " + classType + " p ";
        String extraWhereClause = " p.created < :currentCreated ";
        String orderByClause = " order by p.created desc";

        Query query = createQuery(postCount,
                                    null,
                                    null,
                                    withUnpublished,
                                    categoryId,
                                    keywordId,
                                    excludePosts,
                                    selectClause,
                                    Arrays.asList(extraWhereClause),
                                    orderByClause);

        query.setTimestamp("currentCreated", currentPost.getCreated());
        return query;
    }

    public Post getPreviousPost(AbstractPost currentPost, boolean withUnpublished, Long categoryId)
    {
        Query query = previousPostQuery(ONLY_ONE, currentPost, null, withUnpublished, categoryId, null, null);
        return (Post) singleResult(query);
    }

    public List<AbstractPost> getPreviousPosts(Integer postCount, AbstractPost currentPost, Class clazz, boolean withUnpublished, Long categoryId, Long keywordId, Set<Long> excludePosts)
    {
        Query query = previousPostQuery(postCount, currentPost, clazz, withUnpublished, categoryId, keywordId, excludePosts);
        return query.list();
    }


    private List<Post> getRecentPostsByYearMonth(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId)
    {
        String selectClause = "select p from Post p ";
        String orderByClause = " order by p.created desc ";

        Query query = createQuery(postCount,
                                    year,
                                    month,
                                    withUnpublished,
                                    categoryId,
                                    null,
                                    null,
                                    selectClause,
                                    null,
                                    orderByClause);

        return query.list();
    }

    private List<Post> getRecentPinnedPostsByYearMonth(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId)
    {
        String selectClause = "select p from Post p ";
        String extraWhereClause = " p.pinned is true ";
        String orderByClause = " order by p.created desc ";

        Query query = createQuery(postCount,
                                    year,
                                    month,
                                    withUnpublished,
                                    categoryId,
                                    null,
                                    null,
                                    selectClause,
                                    Arrays.asList(extraWhereClause),
                                    orderByClause);

        return query.list();
    }


    public List<Integer> getAllYears(boolean withUnpublished)
    {
        String text = "select year(p.created)" +
                        " from Post p ";

        if (!withUnpublished)
        {
            text += " where p.published is true ";
        }
        text += " group by year(p.created) " +
                " order by year(p.created) desc ";

        Query query = session.createQuery(text);
        return query.list();
    }

    public List<Integer> getMonths(Integer year, boolean withUnpublished)
    {
        String text = "select month(p.created)" +
                        " from Post p " +
                        " where year(p.created) = :year ";

        if (!withUnpublished)
        {
            text += " and p.published is true ";
        }
        text += " group by month(p.created) " +
                " order by month(p.created) desc ";

        Query query = session.createQuery(text);
        query.setParameter("year", year);
        return query.list();
    }


    public List<Page> getAllPages(boolean withUnpublished)
    {
        String text = "from Page p ";

        if (!withUnpublished)
        {
            text += " where p.published is true ";
        }
        text += " order by p.created desc ";

        Query query = session.createQuery(text);
        return query.list();
    }

    public AbstractPost getMostRecentProject(boolean withUnpublished)
    {
        String text = "select p " +
                        " from AbstractPost p " +
                        " join p.categories c " +
                        " where c.class = '" + ProjectCategory.class.getCanonicalName() + "' ";
        if (!withUnpublished)
        {
            text += " and p.published is true ";
        }
        text += " order by p.created desc ";

        Query query = session.createQuery(text);
        return (AbstractPost) singleResult(query);
    }

    public List<AbstractPost> getAllProjectsByCategory(boolean withUnpublished, Long categoryId)
    {
        String text = "select distinct p " +
                        " from AbstractPost p " +
                        " join p.categories c " +
                        " where c.class = '" + ProjectCategory.class.getCanonicalName() + "' ";
        if (!withUnpublished)
        {
            text += " and p.published is true ";
        }
        if (categoryId != null)
        {
            text += " and c.id = :categoryId ";
        }
        text += " order by p.created desc ";

        Query query = session.createQuery(text);
        if (categoryId != null)
        {
            query.setParameter("categoryId", categoryId);
        }
        return query.list();
    }

    private Query clubPostsQuery(boolean withUnpublished)
    {
        String text = "from ClubPost p ";

        if (!withUnpublished)
        {
            text += " where p.published is true ";
        }
        text += " order by p.eventDate desc ";

        Query query = session.createQuery(text);
        return query;
    }

    public ClubPost getMostRecentClubPost(boolean withUnpublished)
    {
        Query query = clubPostsQuery(withUnpublished);
        return (ClubPost) singleResult(query);
    }

    public List<ClubPost> getAllClubPosts(boolean withUnpublished)
    {
        Query query = clubPostsQuery(withUnpublished);
        return query.list();
    }

    private Query lessonPostsQuery(boolean withUnpublished)
    {
        String text = "from LessonPost p ";

        if (!withUnpublished)
        {
            text += " with p.published is true ";
        }
        text += " order by p.created desc ";

        Query query = session.createQuery(text);
        return query;
    }

    public LessonPost getMostRecentLessonPost(boolean withUnpublished)
    {
        Query query = lessonPostsQuery(withUnpublished);
        return (LessonPost) singleResult(query);
    }

    public List<LessonPost> getAllLessonPosts(boolean withUnpublished)
    {
        Query query = lessonPostsQuery(withUnpublished);
        return query.list();
    }


    private Query createQuery(Integer postCount,
                              Integer year,
                              Integer month,
                              boolean withUnpublished,
                              Long categoryId,
                              Long keywordId,
                              Set<Long> excludePosts,

                              String selectClause,
                              List<String> whereClauses,
                              String orderByClause)
    {
        List<String> phrases = new ArrayList<String>();
        if (categoryId != null)
        {
            phrases.add(" c.id = :categoryId ");
        }
        if (keywordId != null)
        {
            phrases.add(" k.id = :keywordId ");
        }
        if (!withUnpublished)
        {
            phrases.add(" p.published is true ");
        }
        if (year != null)
        {
            phrases.add(" year(p.created) = :year ");
        }
        if (month != null)
        {
            phrases.add(" month(p.created) = :month ");
        }
        if (excludePosts != null && !excludePosts.isEmpty())
        {
            phrases.add(" p.id not in (:exclude) ");
        }
        if (whereClauses != null && !whereClauses.isEmpty())
        {
            phrases.addAll(whereClauses);
        }

        String text = selectClause;
        if (categoryId != null)
        {
            text += " join p.categories c ";
        }
        if (keywordId != null)
        {
            text += " join p.keywords k ";
        }
        boolean first = true;
        for(String phrase : phrases)
        {
            if (first)
                text += " where ";
            else
                text += " and ";

            text += phrase;

            first = false;
        }
        text += orderByClause;

        Query query = session.createQuery(text);
        if (categoryId != null)
        {
            query.setLong("categoryId", categoryId);
        }
        if (keywordId != null)
        {
            query.setLong("keywordId", keywordId);
        }
        if (year != null)
        {
            query.setParameter("year", year);
        }
        if (month != null)
        {
            query.setParameter("month", month);
        }
        if (excludePosts != null && !excludePosts.isEmpty())
        {
            query.setParameterList("exclude", excludePosts);
        }
        if (postCount != null)
        {
            query.setMaxResults(postCount);
        }

        return query;
    }
}
