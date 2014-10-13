package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.Post;

import java.util.List;

public interface PostArchiveManagement
{
    public List<String> getYearlyMonthlyIterations(Integer year, Integer month);


    public List<Post> getPosts(String iteration);

    public List<Post> getPreviewPosts(String iteration, List<Long> excludeIds);


    public String getParentIteration(Integer year, Integer month);

    public Integer parseYear(String iteration);

    public Integer parseMonth(String iteration);
}
