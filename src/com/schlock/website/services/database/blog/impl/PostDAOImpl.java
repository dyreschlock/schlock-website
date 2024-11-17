package com.schlock.website.services.database.blog.impl;

import com.schlock.website.entities.blog.*;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.*;

public class PostDAOImpl extends BaseDAOImpl<AbstractPost> implements PostDAO
{
    private static final int POST_NOT_VISIBLE = AbstractPost.LEVEL_NOT_VISIBLE;
    private static final int POST_UNPUBLISHED = AbstractPost.LEVEL_UNPUBLISHED;
    private static final int POST_PUBLISHED = AbstractPost.LEVEL_PUBLISHED;
    private static final int POST_FRONT_PAGE = AbstractPost.LEVEL_FRONT_PAGE;
    private static final int POST_PINNED = AbstractPost.LEVEL_PINNED;


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

    public List<AbstractPost> getAllByUuid(String uuid)
    {
        String text = "from AbstractPost p " +
                        " where p.uuid = :uuid ";

        Query query = session.createQuery(text);
        query.setParameter("uuid", uuid);

        return query.list();
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

    public List<AbstractPost> getAllWithGallery()
    {
        String text = "from AbstractPost p where p.galleryName is not null";
        Query query = session.createQuery(text);

        return query.list();
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

    public List<String> getAllPublishedUuids()
    {
        String text = "select p.uuid " +
                " from AbstractPost p " +
                " where p.publishedLevel >= " + POST_PUBLISHED + " " +
                " order by p.created asc ";

        Query query = session.createQuery(text);

        List<String> list = query.list();
        return list;
    }

    public List<Post> getAllVisibleByDate()
    {
        String text = "select p " +
                " from Post p " +
                " where p.publishedLevel > " + POST_NOT_VISIBLE + " " +
                " order by p.created asc ";

        Query query = session.createQuery(text);

        List<Post> list = query.list();
        return list;
    }

    public Set<String> getAllGalleryNames()
    {
        String text = "select p.galleryName " +
                        " from AbstractPost p " +
                        " where p.galleryName is not null ";

        Query query = session.createQuery(text);
        List<String> list = query.list();

        Set<String> names = new HashSet<String>();
        names.addAll(list);

        return names;
    }

    public Post getMostRecentFrontPagePost(Long categoryId)
    {
        int publishLevel = POST_FRONT_PAGE;

        return mostRecent(publishLevel, categoryId);
    }

    public Post getMostRecentPost(boolean withUnpublished, Long categoryId)
    {
        int publishLevel = POST_PUBLISHED;
        if (withUnpublished)
        {
            publishLevel = POST_UNPUBLISHED;
        }

        return mostRecent(publishLevel, categoryId);
    }

    private Post mostRecent(int publishLevel, Long categoryId)
    {
        String selectClause = "select p from Post p ";
        String orderByClause = " order by p.created desc";

        Query query = createQuery(TOP_RECENT,
                                    null,
                                    null,
                                    publishLevel,
                                    categoryId,
                                    null,
                                    null,
                                    selectClause,
                                    null,
                                    orderByClause);

        return (Post) singleResult(query);
    }

    public List<Post> getMostRecentPostsWithGallery(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId, Set<Long> excludeIds)
    {
        int publishLevel = POST_PUBLISHED;
        if (withUnpublished)
        {
            publishLevel = POST_UNPUBLISHED;
        }

        String selectClause = "select p from Post p ";
        String whereClause = " p.galleryName is not null ";
        String orderByClause = " order by p.created desc";

        Query query = createQuery(postCount,
                                    year,
                                    month,
                                    publishLevel,
                                    categoryId,
                                    null,
                                    excludeIds,
                                    selectClause,
                                    Arrays.asList(whereClause),
                                    orderByClause);

        return query.list();
    }

    public List<Post> getMostRecentPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId)
    {
        return getMostRecentPosts(postCount, withUnpublished, year, month, categoryId, null);
    }

    public List<Post> getMostRecentPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId, Set<Long> excludeIds)
    {
        int publishLevel = POST_PUBLISHED;
        if (withUnpublished)
        {
            publishLevel = POST_UNPUBLISHED;
        }

        String selectClause = "select p from Post p ";
        String orderByClause = " order by p.created desc ";

        Query query = createQuery(postCount,
                                    year,
                                    month,
                                    publishLevel,
                                    categoryId,
                                    null,
                                    excludeIds,
                                    selectClause,
                                    null,
                                    orderByClause);

        return query.list();
    }

    public List<Post> getMostRecentPinnedPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId)
    {
        return getMostRecentPinnedPosts(postCount, withUnpublished, year, month, categoryId, null);
    }

    public List<Post> getMostRecentPinnedPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId, Set<Long> excludeIds)
    {
        boolean pinnedOnly = true;

        String selectClause = "select p from Post p ";
        String orderByClause = " order by p.created desc ";

        Query query = createQuery(postCount,
                                    year,
                                    month,
                                    POST_PINNED,
                                    categoryId,
                                    null,
                                    excludeIds,
                                    selectClause,
                                    null,
                                    orderByClause);

        return query.list();
    }

    private Query nextPostQuery(Integer postCount, AbstractPost currentPost, Class clazz, boolean pinnedOnly, boolean withUnpublished, Long categoryId, Long keywordId, Set<Long> excludePosts)
    {
        int publishLevel = POST_PUBLISHED;
        if (pinnedOnly)
        {
            publishLevel = POST_PINNED;
        }
        else if (withUnpublished)
        {
            publishLevel = POST_UNPUBLISHED;
        }


        String classType = "Post";
        if (clazz != null)
        {
            classType = clazz.getSimpleName();
            publishLevel = POST_UNPUBLISHED;
        }

        String selectClause = "select p from " + classType + " p ";
        String extraWhereClause = " p.created > :currentCreated ";
        String orderByClause = " order by p.created asc";

        Query query = createQuery(postCount,
                                    null,
                                    null,
                                    publishLevel,
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
        Query query = nextPostQuery(ONLY_ONE, currentPost, null, false, withUnpublished, categoryId, null, null);
        return (Post) singleResult(query);
    }

    public List<AbstractPost> getNextPosts(Integer postCount, AbstractPost currentPost, Class clazz, boolean pinnedOnly, boolean withUnpublished, Long categoryId, Long keywordId, Set<Long> excludePosts)
    {
        Query query = nextPostQuery(postCount, currentPost, clazz, pinnedOnly, withUnpublished, categoryId, keywordId, excludePosts);
        return query.list();
    }


    private Query previousPostQuery(Integer postCount, AbstractPost currentPost, Class clazz, boolean pinnedOnly, boolean withUnpublished, Long categoryId, Long keywordId, Set<Long> excludePosts)
    {
        int publishLevel = POST_PUBLISHED;
        if (pinnedOnly)
        {
            publishLevel = POST_PINNED;
        }
        else if (withUnpublished)
        {
            publishLevel = POST_UNPUBLISHED;
        }


        String classType = "Post";
        if (clazz != null)
        {
            classType = clazz.getSimpleName();
            publishLevel = POST_UNPUBLISHED;
        }

        String selectClause = "select p from " + classType + " p ";
        String extraWhereClause = " p.created < :currentCreated ";
        String orderByClause = " order by p.created desc";

        Query query = createQuery(postCount,
                null,
                null,
                publishLevel,
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
        Query query = previousPostQuery(ONLY_ONE, currentPost, null, false, withUnpublished, categoryId, null, null);
        return (Post) singleResult(query);
    }

    public List<AbstractPost> getPreviousPosts(Integer postCount, AbstractPost currentPost, Class clazz, boolean pinnedOnly, boolean withUnpublished, Long categoryId, Long keywordId, Set<Long> excludePosts)
    {
        Query query = previousPostQuery(postCount, currentPost, clazz, pinnedOnly, withUnpublished, categoryId, keywordId, excludePosts);
        return query.list();
    }


    public List<Integer> getAllYears(boolean withUnpublished)
    {
        String text = "select year(p.created)" +
                        " from Post p ";

        if (!withUnpublished)
        {
            text += " where p.publishedLevel >= " + POST_PUBLISHED + " ";
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
            text += " and p.publishedLevel >= " + POST_PUBLISHED + " ";
        }
        text += " group by month(p.created) " +
                " order by month(p.created) desc ";

        Query query = session.createQuery(text);
        query.setParameter("year", year);
        return query.list();
    }

    public List<List<Integer>> getYearsMonthsByCategory(boolean withUnpublished, Long categoryId)
    {
        String text = "select year(p.created), month(p.created)" +
                        " from Post p " +
                        " join p.categories c " +
                        " where c.id = :categoryId ";

        if (!withUnpublished)
        {
            text += " and p.publishedLevel >= " + POST_PUBLISHED + " ";
        }
        text += " group by year(p.created), month(p.created) " +
                " order by year(p.created) desc, month(p.created) desc ";

        Query query = session.createQuery(text);
        query.setParameter("categoryId", categoryId);
        List<Object[]> results = query.list();

        List<List<Integer>> list = new ArrayList<List<Integer>>();

        for (Object[] result : results)
        {
            Integer year = Integer.parseInt(result[0].toString());
            Integer month = Integer.parseInt(result[1].toString());

            list.add(Arrays.asList(year, month));
        }

        return list;
    }


    public List<Page> getAllPages(boolean withUnpublished)
    {
        String text = "from Page p ";

        if (!withUnpublished)
        {
            text += " where p.publishedLevel >= " + POST_PUBLISHED + " ";
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
            text += " and p.publishedLevel >= " + POST_PUBLISHED + " ";
        }
        text += " order by p.created desc ";

        Query query = session.createQuery(text);
        return (AbstractPost) singleResult(query);
    }

    public List<AbstractPost> getAllProjectsByCategory(boolean withUnpublished, Long categoryId)
    {
        return getAllByCategoryClass(ProjectCategory.class, withUnpublished, categoryId);
    }

    public List<AbstractPost> getAllCoursesByCategory(Long categoryId)
    {
        return getAllByCategoryClass(CourseCategory.class, true, categoryId);
    }

    private List<AbstractPost> getAllByCategoryClass(Class categoryClass, boolean withUnpublished, Long categoryId)
    {
        String text = "select distinct p " +
                        " from AbstractPost p " +
                        " join p.categories c " +
                        " where c.class = '" + categoryClass.getCanonicalName() + "' ";

        int publishLevel = POST_UNPUBLISHED;
        if (!withUnpublished)
        {
            publishLevel = POST_PUBLISHED;
        }
        text += " and p.publishedLevel >= " + publishLevel + " ";

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
            text += " where p.publishedLevel >= " + POST_PUBLISHED + " ";
        }
        else
        {
            text += " where p.publishedLevel > " + POST_NOT_VISIBLE + " ";
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
            text += " where p.publishedLevel >= " + POST_PUBLISHED + " ";
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
                              int publishLevel,
                              Long categoryId,
                              Long keywordId,
                              Set<Long> excludePosts,

                              String selectClause,
                              List<String> whereClauses,
                              String orderByClause)
    {
        List<String> phrases = new ArrayList<String>();
        phrases.add(" p.publishedLevel >= " + publishLevel +" ");
        if (categoryId != null)
        {
            phrases.add(" c.id = :categoryId ");
        }
        if (keywordId != null)
        {
            phrases.add(" k.id = :keywordId ");
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


    public List<LessonPost> getByYearGradeLessonKeyword(String year, String grade, String lesson)
    {
        List<String> grades = Arrays.asList(grade);
        return getByYearGradeLessonKeyword(year, grades, lesson);
    }

    public List<LessonPost> getByYearGradeLessonKeyword(String year, List<String> grades, String lesson)
    {
        String text =
                "select distinct p " +
                " from LessonPost p " +
                " join p.keywords y " +
                " join p.keywords g ";

        if (StringUtils.isNotBlank(lesson))
        {
            text += " join p.keywords l ";
        }
        text += " where p.publishedLevel >= " + AbstractPost.LEVEL_NOT_VISIBLE + " ";
        text += " and y.name = :year ";

        text += " and (";

        boolean first = true;
        for (int i = 0; i < grades.size(); i++)
        {
            if (!first)
            {
                text += " or ";
            }
            first = false;

            text += " g.name = :grade" + i + " ";
        }
        text += ") ";

        if (StringUtils.isNotBlank(lesson))
        {
            text += " and l.name = :lesson ";
        }
        text += " order by p.created asc ";


        Query query = session.createQuery(text);

        query.setParameter("year", year);
        for (int i = 0; i < grades.size(); i++)
        {
            String grade = grades.get(0);
            query.setParameter("grade" + i, grade);
        }
        if (StringUtils.isNotBlank(lesson))
        {
            query.setParameter("lesson", lesson);
        }

        return query.list();
    }
}
