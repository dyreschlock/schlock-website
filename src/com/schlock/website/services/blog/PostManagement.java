package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.Post;

import java.util.Date;

public interface PostManagement
{
    public Post createTextPost(String postTitle, String postContent);

    public Post createTextPost(Date created, Date createdGMT, String postTitle, String postContent);
}
