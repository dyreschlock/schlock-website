package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.Post;

import java.util.Date;
import java.util.List;

public interface PostManagement
{
    public Post createPost(String postTitle, String postContent);

    public Post createPost(Date created, String postTitle, String postContent);

    public void regenerateAllPostHTML();

    public void setPostHTML(Post post);

    public String generatePostPreview(Post post);

    public List<String> getGalleryImages(Post post);

    public String getPostImage(Post post);

    public String getStylizedHTMLTitle(Post post);
}
