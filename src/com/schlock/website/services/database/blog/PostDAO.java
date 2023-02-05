package com.schlock.website.services.database.blog;

import com.schlock.website.entities.blog.*;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;
import java.util.Set;

public interface PostDAO extends BaseDAO<AbstractPost>
{
    public static final int TOP_RECENT = 4;
    public static final int MIN_RECENT = 2;
    public static final int ONLY_ONE = 1;

    public AbstractPost getByUuid(String uuid);

    public AbstractPost getByWpid(String wpid);

    public AbstractPost getByMtid(String mtid);

    public AbstractPost getByGalleryName(String galleryName);

    public Set<String> getAllUuids();

    public Set<String> getAllGalleryNames();


    public Post getMostRecentFrontPagePost(Long categoryId);

    public Post getMostRecentPost(boolean withUnpublished, Long categoryId);


    public List<Post> getMostRecentPostsWithGallery(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId, Set<Long> excludeIds);


    public List<Post> getMostRecentPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId);

    public List<Post> getMostRecentPinnedPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId);


    public List<Post> getMostRecentPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId, Set<Long> excludeIds);

    public List<Post> getMostRecentPinnedPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId, Set<Long> excludeIds);


    public Post getNextPost(AbstractPost currentPost, boolean withUnpublished, Long categoryId);

    public List<AbstractPost> getNextPosts(Integer postCount, AbstractPost currentPost, Class clazz, boolean pinnedOnly, boolean withUnpublished, Long categoryId, Long keywordId, Set<Long> excludePosts);

    public Post getPreviousPost(AbstractPost currentPost, boolean withUnpublished, Long categoryId);

    public List<AbstractPost> getPreviousPosts(Integer postCount, AbstractPost currentPost, Class clazz, boolean pinnedOnly, boolean withUnpublished, Long categoryId, Long keywordId, Set<Long> excludePosts);


    public List<Integer> getAllYears(boolean withUnpublished);

    public List<Integer> getMonths(Integer year, boolean withUnpublished);

    public List<List<Integer>> getYearsMonthsByCategory(boolean withUnpublished, Long categoryId);


    public List<Page> getAllPages(boolean withUnpublished);

    public AbstractPost getMostRecentProject(boolean withUnpublished);

    public List<AbstractPost> getAllProjectsByCategory(boolean withUnpublished, Long categoryId);

    public ClubPost getMostRecentClubPost(boolean withUnpublished);

    public List<ClubPost> getAllClubPosts(boolean withUnpublished);

    public LessonPost getMostRecentLessonPost(boolean withUnpublished);

    public List<LessonPost> getAllLessonPosts(boolean withUnpublished);


    public List<LessonPost> getByYearGradeLessonKeyword(String year, String grade, String lesson);

    public List<LessonPost> getByYearGradeLessonKeyword(String year, List<String> grade, String lesson);
}
