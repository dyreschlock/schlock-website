package com.schlock.website.services.database.blog;

import com.schlock.website.entities.blog.*;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;
import java.util.Set;

public interface PostDAO extends BaseDAO<AbstractPost>
{
    public AbstractPost getByUuid(String uuid);

    public AbstractPost getByWpid(String wpid);

    public AbstractPost getByMtid(String mtid);

    public AbstractPost getByGalleryName(String galleryName);

    public Set<String> getAllUuids();

    public Post getMostRecentPost(boolean withUnpublished, Long categoryId);

    public List<Post> getMostRecentPosts(Integer postCount, boolean withUnpublished);

    public Post getNextPost(AbstractPost currentPost, boolean withUnpublished, Long categoryId);

    public List<Post> getNextPosts(AbstractPost currentPost, boolean withUnpublished, Long categoryId);

    public Post getPreviousPost(AbstractPost currentPost, boolean withUnpublihed, Long categoryId);

    public List<Post> getPreviousPosts(AbstractPost currentPost, boolean withUnplished, Long categoryId);

    public List<Post> getRecentPostsByYearMonth(Integer postCount, Integer year, Integer month, boolean withUnpublished, Long categoryId);

    public List<Post> getRecentPinnedPostsByYearMonth(Integer postCount, Integer year, Integer month, boolean withUnpublished, Long categoryId);

    public List<Object[]> getPublishedPostCounts();

    public List<Object[]> getCategoryPostCounts(boolean withUnpublished);

    public List<Object[]> getYearsMonthPostCounts(boolean withUnpublished);

    public List<Post> getByCategoryNames(List<String> categoryNames, boolean onlyWithGallery);

    public List<Page> getAllPages(boolean withUnpublished);

    public ClubPost getMostRecentClubPost(boolean withUnpublished);

    public List<ClubPost> getAllClubPosts(boolean withUnpublished);

    public LessonPost getMostRecentLessonPost(boolean withUnpublished);

    public List<LessonPost> getAllLessonPosts(boolean withUnpublished);
}
