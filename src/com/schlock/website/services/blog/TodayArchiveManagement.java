package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.Post;

import java.util.List;

public interface TodayArchiveManagement
{
    List<String> getAllMonths();

    List<String> getAllDays(String month);


    Post getPreviewPost(String dateString);

    String getPreviewPostUuid(String dateString);

    boolean isHasPreviewPost(String dateString);

    List<String> getYears(String dateString);

    int getPostCount(String dateString);

    List<String> getUuidsByDate(String dateString);


    List<Post> getPosts(String dateString, String year);

    List<Post> getPreviewPosts(String dateString, String year);


    String getNextDayString(String dateString);

    String getPreviousDayString(String dateString);
}
