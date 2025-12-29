package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.old.SiteVersion;

import java.util.List;
import java.util.Set;

public interface PostArchiveManagement
{
    List<String> getYearlyMonthlyIterations(Integer year, Integer month);

    List<String> getYearlyMonthlyIterations(String keywordName);

    String getNextYearlyMontlyIteration(String iteration, String keywordName);

    String getPreviousYearlyMonthlyInteration(String iteration, String keywordName);


    List<Post> getPosts(String iteration);

    List<Post> getPosts(String iteration, String keywordName);

    List<Post> getPreviewPosts(String iteration, Set<Long> excludeIds);

    List<Post> getPreviewPosts(String iteration, String keywordName, Set<Long> excludeIds);


    List<Post> getPagedPosts(SiteVersion version, Integer postCount, Integer pageNumber);

    List<Post> getPagedPosts(SiteVersion version, Integer postCount, Integer pageNumber, String iteration);

    List<Post> getPagedPosts(SiteVersion version, Integer postCount, Integer pageNumber, Set<String> keywordNames);

    List<Post> getPagedClubPosts(Integer postCount, Integer pageNumber);


    String getParentIteration(Integer year, Integer month);

    String getIterationTitle(String iteration);

    String getIterationTitle(Integer year, Integer month);

    String getIterationUrlChain(String iterationCode);

    Integer parseYear(String iteration);

    Integer parseMonth(String iteration);

    boolean isIteration(String iteration);
}
