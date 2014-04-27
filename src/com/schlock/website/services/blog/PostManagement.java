package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Category;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.Post;

import java.util.Date;
import java.util.List;

public interface PostManagement
{
    public Post createPost(String postTitle, String postContent);

    public Post createPost(Date created, String postTitle, String postContent);

    public Date getUpdatedTime(Page page);

    public void regenerateAllPostHTML();

    public void setPostHTML(AbstractPost post);

    public String generatePostPreview(AbstractPost post);

    public List<String> getGalleryImages(AbstractPost post);

    public String getPostImage(AbstractPost post);

    public String getStylizedHTMLTitle(AbstractPost post);

    public List<Post> getTopPostsForCategory(Integer count, Category category, List<Long> excludeIds);
}
