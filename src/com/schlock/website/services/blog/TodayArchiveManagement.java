package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.AbstractPost;

import java.util.List;

public interface TodayArchiveManagement
{
    AbstractPost getMostRecent(String dateString);

    List<String> getYears(String dateString);
}
