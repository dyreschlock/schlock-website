package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.Post;

import java.util.List;
import java.util.Set;

public interface PostArchiveManagement
{
    public List<String> getYearlyMonthlyIterations(Integer year, Integer month);

    public List<String> getYearlyMonthlyIterations(Long categoryId);


    public List<Post> getPosts(String iteration);

    public List<Post> getPosts(String iteration, Long categoryId);

    public List<Post> getPreviewPosts(String iteration, Set<Long> excludeIds);

    public List<Post> getPreviewPosts(String iteration, Long categoryId, Set<Long> excludeIds);


    public String getParentIteration(Integer year, Integer month);

    public String getIterationTitle(String iteration);

    public String getIterationTitle(Integer year, Integer month);

    public String getIterationUrlChain(String iterationCode);

    public Integer parseYear(String iteration);

    public Integer parseMonth(String iteration);
}
