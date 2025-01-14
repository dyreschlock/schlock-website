package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.Post;

import java.util.List;
import java.util.Set;

public interface PostArchiveManagement
{
    List<String> getYearlyMonthlyIterations(Integer year, Integer month);

    List<String> getYearlyMonthlyIterations(Long categoryId);

    String getNextYearlyMontlyIteration(String iteration, Long categoryId);

    String getPreviousYearlyMonthlyInteration(String iteration, Long categoryId);


    List<Post> getPosts(String iteration);

    List<Post> getPosts(String iteration, Long categoryId);

    List<Post> getPreviewPosts(String iteration, Set<Long> excludeIds);

    List<Post> getPreviewPosts(String iteration, Long categoryId, Set<Long> excludeIds);


    List<Post> getPagedPosts(Integer postCount, Integer pageNumber);

    List<Post> getPagedPosts(Integer postCount, Integer pageNumber, String iteration);

    List<Post> getPagedPosts(Integer postCount, Integer pageNumber, Set<Long> categoryIds);


    String getParentIteration(Integer year, Integer month);

    String getIterationTitle(String iteration);

    String getIterationTitle(Integer year, Integer month);

    String getIterationUrlChain(String iterationCode);

    Integer parseYear(String iteration);

    Integer parseMonth(String iteration);

    boolean isIteration(String iteration);
}
