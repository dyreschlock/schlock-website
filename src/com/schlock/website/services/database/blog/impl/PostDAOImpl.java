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


    public List<AbstractPost> getByIds(Set<Long> ids)
    {
        String text = " from AbstractPost p " +
                " where p.id in (:ids) " +
                " order by p.created DESC ";

        Query query = session.createQuery(text);
        query.setParameterList("ids", ids);

        return query.list();
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

    public List<Post> getPostsByUuid(List<String> uuids)
    {
        String text = " from Post p " +
                        " where p.uuid in (:uuids) " +
                        " order by p.created desc ";

        Query query = session.createQuery(text);
        query.setParameterList("uuids", uuids);

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
        return query.list();
    }

    public List<Post> getAllPublished()
    {
        String text = "select p " +
                        " from Post p " +
                        " where p.publishedLevel >= " + POST_PUBLISHED + " " +
                        " order by p.created desc ";

        Query query = session.createQuery(text);
        return query.list();
    }

    public List<Post> getAllPublishedWithMapLocation()
    {
        String text = "select p " +
                        " from Post p " +
                        " where p.publishedLevel >= " + POST_PUBLISHED + " " +
                        " and p.mapLocation is not null " +
                        " order by p.created desc ";

        Query query = session.createQuery(text);
        return query.list();
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

    public Post getMostRecentFrontPagePost()
    {
        int publishLevel = POST_FRONT_PAGE;

        return mostRecent(publishLevel, true);
    }

    public Post getMostRecentPost(boolean withUnpublished)
    {
        return mostRecent(withUnpublished, true);
    }

    public Post getFirstAvailablePost(boolean withUnpublished)
    {
        return mostRecent(withUnpublished, false);
    }

    private Post mostRecent(boolean withUnpublished, boolean desc)
    {
        int publishLevel = POST_PUBLISHED;
        if (withUnpublished)
        {
            publishLevel = POST_UNPUBLISHED;
        }

        return mostRecent(publishLevel, desc);
    }

    private Post mostRecent(int publishLevel, boolean desc)
    {
        String selectClause = "select p from Post p ";
        String orderByClause = " order by p.created desc";
        if (!desc)
        {
            orderByClause = " order by p.created asc";
        }

        Long catId = null;

        Query query = createQuery(TOP_RECENT,
                                    null,
                                    null,
                                    publishLevel,
                                    catId,
                                    null,
                                    null,
                                    selectClause,
                                    null,
                                    orderByClause);

        return (Post) singleResult(query);
    }

    public List<Post> getMostRecentPostsWithGallery(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId, String keywordName, Set<Long> excludeIds)
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
                                    keywordName,
                                    excludeIds,
                                    selectClause,
                                    Arrays.asList(whereClause),
                                    orderByClause);

        return query.list();
    }

    public List<Post> getMostRecentPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId)
    {
        String keyword = null;
        return getMostRecentPosts(postCount, withUnpublished, year, month, categoryId, keyword);
    }

    public List<Post> getMostRecentPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId, String keywordName)
    {
        Set<Long> catIds = new HashSet<>();
        if (categoryId != null)
        {
            catIds.add(categoryId);
        }

        return getMostRecentPosts(postCount, withUnpublished, year, month, catIds, keywordName, null);
    }


    public List<Post> getMostRecentPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Set<Long> categoryIds)
    {
        return getMostRecentPosts(postCount, withUnpublished, year, month, categoryIds, null, null);
    }

    public List<Post> getMostRecentPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId, String keywordName, Set<Long> excludeIds)
    {
        Set<Long> catIds = new HashSet<>();
        if (categoryId != null)
        {
            catIds.add(categoryId);
        }

        return getMostRecentPosts(postCount, withUnpublished, year, month, catIds, keywordName, excludeIds);
    }

    private List<Post> getMostRecentPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Set<Long> categoryIds, String keywordName, Set<Long> excludeIds)
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
                categoryIds,
                keywordName,
                excludeIds,
                selectClause,
                null,
                orderByClause);

        return query.list();
    }



    public List<Post> getMostRecentPinnedPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId)
    {
        return getMostRecentPinnedPosts(postCount, withUnpublished, year, month, categoryId, null, null);
    }

    public List<Post> getMostRecentPinnedPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId, String keywordName, Set<Long> excludeIds)
    {
        boolean pinnedOnly = true;

        String selectClause = "select p from Post p ";
        String orderByClause = " order by p.created desc ";

        Query query = createQuery(postCount,
                                    year,
                                    month,
                                    POST_PINNED,
                                    categoryId,
                                    keywordName,
                                    excludeIds,
                                    selectClause,
                                    null,
                                    orderByClause);

        return query.list();
    }

    private Query nextPostQuery(Integer postCount, AbstractPost currentPost, Class clazz, boolean pinnedOnly, boolean withUnpublished, Long categoryId, String keywordName, Set<Long> excludePosts)
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
                                    keywordName,
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

    public List<AbstractPost> getNextPosts(Integer postCount, AbstractPost currentPost, Class clazz, boolean pinnedOnly, boolean withUnpublished, Long categoryId, String keywordName, Set<Long> excludePosts)
    {
        Query query = nextPostQuery(postCount, currentPost, clazz, pinnedOnly, withUnpublished, categoryId, keywordName, excludePosts);
        return query.list();
    }


    private Query previousPostQuery(Integer postCount, AbstractPost currentPost, Class clazz, boolean pinnedOnly, boolean withUnpublished, Long categoryId, String keywordName, Set<Long> excludePosts)
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
                keywordName,
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

    public List<AbstractPost> getPreviousPosts(Integer postCount, AbstractPost currentPost, Class clazz, boolean pinnedOnly, boolean withUnpublished, Long categoryId, String keywordName, Set<Long> excludePosts)
    {
        Query query = previousPostQuery(postCount, currentPost, clazz, pinnedOnly, withUnpublished, categoryId, keywordName, excludePosts);
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
        return getYearsMonthsByCategoryKeyword(withUnpublished, categoryId, null);
    }

    public List<List<Integer>> getYearsMonthsByKeyword(boolean withUnplished, String keywordName)
    {
        return getYearsMonthsByCategoryKeyword(withUnplished, null, keywordName);
    }

    private List<List<Integer>> getYearsMonthsByCategoryKeyword(boolean withUnpublished, Long categoryId, String keywordName)
    {
        String text = "select year(p.created), month(p.created)" +
                        " from Post p ";

        if (categoryId != null)
        {
            text += " join p.categories c ";
        }
        if (keywordName != null)
        {
            text += " join p.keywords k ";
        }

        text += " where p.created is not null ";

        if (categoryId != null)
        {
            text += " and c.id = :categoryId ";
        }
        if (keywordName != null)
        {
            text += " and k.name = :keywordName ";
        }
        if (!withUnpublished)
        {
            text += " and p.publishedLevel >= " + POST_PUBLISHED + " ";
        }
        text += " group by year(p.created), month(p.created) " +
                " order by year(p.created) desc, month(p.created) desc ";

        Query query = session.createQuery(text);
        if (categoryId != null)
        {
            query.setParameter("categoryId", categoryId);
        }
        if (keywordName != null)
        {
            query.setParameter("keywordName", keywordName);
        }

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

    public List<Page> getAllPagesNoCourses(boolean withUnpublished)
    {
        String text = "from Page p " +
                        " where p.class is not :coursePage ";

        if (!withUnpublished)
        {
            text += " and p.publishedLevel >= " + POST_PUBLISHED + " ";
        }
        else
        {
            text += " and p.publishedLevel >= " + POST_UNPUBLISHED + " ";
        }
        text += " order by p.created desc ";

        Query query = session.createQuery(text);
        query.setParameter("coursePage", CoursePage.class.getName());
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
                              String keywordName,
                              Set<Long> excludePosts,

                              String selectClause,
                              List<String> whereClauses,
                              String orderByClause)
    {
        Set<Long> catIds = new HashSet<>();
        if (categoryId != null)
        {
            catIds.add(categoryId);
        }

        return createQuery(postCount, year, month, publishLevel, catIds, keywordName, excludePosts, selectClause, whereClauses, orderByClause);
    }

    private Query createQuery(Integer postCount,
                              Integer year,
                              Integer month,
                              int publishLevel,
                              Set<Long> categoryIds,
                              String keywordName,
                              Set<Long> excludePosts,

                              String selectClause,
                              List<String> whereClauses,
                              String orderByClause)
    {
        List<String> phrases = new ArrayList<String>();
        phrases.add(" p.publishedLevel >= " + publishLevel +" ");
        if (categoryIds != null && !categoryIds.isEmpty())
        {
            phrases.add(" c.id in (:categoryIds) ");
        }
        if (keywordName != null)
        {
            phrases.add(" k.name = :keywordName ");
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
        if (categoryIds != null && !categoryIds.isEmpty())
        {
            text += " join p.categories c ";
        }
        if (keywordName != null)
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
        if (categoryIds != null && !categoryIds.isEmpty())
        {
            query.setParameterList("categoryIds", categoryIds);
        }
        if (keywordName != null)
        {
            query.setParameter("keywordName", keywordName);
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


    public List<Object[]> getPostDetailsFromUuids(List<String> uuids)
    {
        String text = " select p.uuid, p.title, p.created " +
                " from AbstractPost p " +
                " where p.uuid in (:postUuids) " +
                " and p.class is not :coursePage " +
                " and " +
                    "(" +
                        "(p.class is :clubClass " +
                            "and p.publishedLevel > :notVisible) " +
                        " or (p.class is not :clubClass " +
                            "and p.publishedLevel > :notPublished)" +
                    ")";

        Query query = session.createQuery(text);
        query.setParameterList("postUuids", uuids);
        query.setParameter("coursePage", CoursePage.class.getName());
        query.setParameter("clubClass", ClubPost.class.getName());
        query.setParameter("notVisible", AbstractPost.LEVEL_NOT_VISIBLE);
        query.setParameter("notPublished", AbstractPost.LEVEL_UNPUBLISHED);

        return query.list();
    }
}
