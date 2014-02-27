package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.Post;

import java.util.Date;

public interface PostManagement
{
    public Post createPost(String postTitle, String postContent);

    public Post createPost(Date created, Date createdGMT, String postTitle, String postContent);

    public void regenerateAllPostHTML();

    public void generatePostHTML(Post post);
}
