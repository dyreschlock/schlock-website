package com.schlock.website.services.database.blog;

import com.schlock.website.entities.blog.*;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;
import java.util.Set;

public interface PostDAO extends BaseDAO<AbstractPost>
{
    int TOP_RECENT = 4;
    int MIN_RECENT = 2;
    int ONLY_ONE = 1;

    List<AbstractPost> getByIds(Set<Long> ids);

    AbstractPost getByUuid(String uuid);

    List<Post> getPostsByUuid(List<String> uuids);

    List<AbstractPost> getAllByUuid(String uuid);

    AbstractPost getByWpid(String wpid);

    AbstractPost getByMtid(String mtid);

    AbstractPost getByGalleryName(String galleryName);

    List<AbstractPost> getAllWithGallery();

    Set<String> getAllUuids();

    List<String> getAllPublishedUuids();

    List<Post> getAllPublished();

    List<Post> getAllVisibleByDate();

    Set<String> getAllGalleryNames();

    List<Post> getAllPublishedWithMapLocation();


    Post getMostRecentFrontPagePost();

    Post getMostRecentPost(boolean withUnpublished);

    Post getFirstAvailablePost(boolean withUnpublished);


    List<Post> getMostRecentPostsWithGallery(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId, String keywordName, Set<Long> excludeIds);


    List<Post> getMostRecentPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId);

    List<Post> getMostRecentPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId, String keywordName);

    List<Post> getMostRecentPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Set<Long> categoryIds);

    List<Post> getMostRecentPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId, String keywordName, Set<Long> excludeIds);


    List<Post> getMostRecentPinnedPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId);

    List<Post> getMostRecentPinnedPosts(Integer postCount, boolean withUnpublished, Integer year, Integer month, Long categoryId, String keywordName, Set<Long> excludeIds);


    Post getNextPost(AbstractPost currentPost, boolean withUnpublished, Long categoryId);

    List<AbstractPost> getNextPosts(Integer postCount, AbstractPost currentPost, Class clazz, boolean pinnedOnly, boolean withUnpublished, Long categoryId, String keywordName, Set<Long> excludePosts);

    Post getPreviousPost(AbstractPost currentPost, boolean withUnpublished, Long categoryId);

    List<AbstractPost> getPreviousPosts(Integer postCount, AbstractPost currentPost, Class clazz, boolean pinnedOnly, boolean withUnpublished, Long categoryId, String keywordName, Set<Long> excludePosts);


    List<Integer> getAllYears(boolean withUnpublished);

    List<Integer> getMonths(Integer year, boolean withUnpublished);

    List<List<Integer>> getYearsMonthsByCategory(boolean withUnpublished, Long categoryId);

    List<List<Integer>> getYearsMonthsByKeyword(boolean withUnplished, String keywordName);


    List<Page> getAllPages(boolean withUnpublished);

    List<Page> getAllPagesNoCourses(boolean withUnpublished);

    AbstractPost getMostRecentProject(boolean withUnpublished);

    List<AbstractPost> getAllProjectsByCategory(boolean withUnpublished, Long categoryId);

    List<AbstractPost> getAllCoursesByCategory(Long categoryId);

    List<AbstractPost> getAllProjectsByKeyword(String keyword);

    List<AbstractPost> getAllCoursesByKeyword(String keyword);

    ClubPost getMostRecentClubPost(boolean withUnpublished);

    List<ClubPost> getAllClubPosts(boolean withUnpublished);

    LessonPost getMostRecentLessonPost(boolean withUnpublished);

    List<LessonPost> getAllLessonPosts(boolean withUnpublished);


    List<LessonPost> getByYearGradeLessonKeyword(String year, String grade, String lesson);

    List<LessonPost> getByYearGradeLessonKeyword(String year, List<String> grade, String lesson);


    List<Object[]> getPostDetailsFromUuids(List<String> uuids);
}
