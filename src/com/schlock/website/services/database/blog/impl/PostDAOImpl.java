package com.schlock.website.services.database.blog.impl;

import com.schlock.website.entities.blog.*;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.blog.PostDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.*;

public class PostDAOImpl extends BaseDAOImpl<AbstractPost> implements PostDAO
{
    public static final int TOP_RECENT = 4;
    public static final int MIN_RECENT = 2;

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

    private Query nextPostQuery(AbstractPost currentPost, boolean withUnpublished, Long categoryId)
    {
        String selectClause = "select p from Post p ";
        String extraWhereClause = " p.created > :currentCreated ";
        String orderByClause = " order by p.created asc";

        Query query = createQuery(TOP_RECENT,
                                    null,
                                    null,
                                    withUnpublished,
                                    categoryId,
                                    selectClause,
                                    Arrays.asList(extraWhereClause),
                                    orderByClause);

        query.setTimestamp("currentCreated", currentPost.getCreated());
        return query;
    }

    public Post getNextPost(AbstractPost currentPost, boolean withUnpublished, Long categoryId)
    {
        Query query = nextPostQuery(currentPost, withUnpublished, categoryId);
        return (Post) singleResult(query);
    }

    public List<Post> getNextPosts(AbstractPost currentPost, boolean withUnpublished, Long categoryId)
    {
        Query query = nextPostQuery(currentPost, withUnpublished, categoryId);
        return query.list();
    }


    private Query previousPostQuery(AbstractPost currentPost, boolean withUnpublished, Long categoryId)
    {
        String selectClause = "select p from Post p ";
        String extraWhereClause = " p.created < :currentCreated ";
        String orderByClause = " order by p.created desc";

        Query query = createQuery(TOP_RECENT,
                                    null,
                                    null,
                                    withUnpublished,
                                    categoryId,
                                    selectClause,
                                    Arrays.asList(extraWhereClause),
                                    orderByClause);

        query.setTimestamp("currentCreated", currentPost.getCreated());
        return query;
    }

    public Post getPreviousPost(AbstractPost currentPost, boolean withUnpublished, Long categoryId)
    {
        Query query = previousPostQuery(currentPost, withUnpublished, categoryId);
        return (Post) singleResult(query);
    }

    public List<Post> getPreviousPosts(AbstractPost currentPost, boolean withUnpublished, Long categoryId)
    {
        Query query = previousPostQuery(currentPost, withUnpublished, categoryId);
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

    private Query projectsQuery(boolean withUnpublished)
    {
        String text = "from Project p ";
        if (!withUnpublished)
        {
            text += " where p.published is true ";
        }
        text += " order by p.created desc ";

        Query query = session.createQuery(text);
        return query;
    }

    public Project getMostRecentProject(boolean withUnpublished)
    {
        Query query = projectsQuery(withUnpublished);
        return (Project) singleResult(query);
    }

    public List<Project> getAllProjects(boolean withUnpublished)
    {
        Query query = projectsQuery(withUnpublished);
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

                              String selectClause,
                              List<String> whereClauses,
                              String orderByClause)
    {
        List<String> phrases = new ArrayList<String>();
        if (categoryId != null)
        {
            phrases.add(" c.id = :categoryId ");
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
        if (whereClauses != null && !whereClauses.isEmpty())
        {
            phrases.addAll(whereClauses);
        }

        String text = selectClause;
        if (categoryId != null)
        {
            text += " join p.categories c ";
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
            query.setParameter("categoryId", categoryId);
        }
        if (year != null)
        {
            query.setParameter("year", year);
        }
        if (month != null)
        {
            query.setParameter("month", month);
        }
        if (postCount != null)
        {
            query.setMaxResults(postCount);
        }

        return query;
    }
}
