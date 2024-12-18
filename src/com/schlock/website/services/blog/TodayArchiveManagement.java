package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.AbstractPost;

import java.util.List;

public interface TodayArchiveManagement
{
    List<AbstractPost> getPostsByDate(String dateString);

    AbstractPost getMostRecent(String dateString);
}
