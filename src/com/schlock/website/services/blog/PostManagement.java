package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.Post;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface PostManagement
{
    public Post createPost(String postTitle, String postContent);

    public Post createPost(Date created, String postTitle, String postContent);

    public Date getUpdatedTime(Page page);

    public void regenerateAllPostHTML();

    public void setPostHTML(AbstractPost post);

    public String generatePostPreview(AbstractPost post);

    public String wrapJapaneseTextInTags(String html);

    public List<String> getGalleryImages(AbstractPost post);

    public String getPostImage(AbstractPost post);

    public String getStylizedHTMLTitle(AbstractPost post);

    public List<Post> getTopPosts(Integer count, Long categoryId, Set<Long> excludeIds);

    public List<Post> getTopPosts(Integer count, Integer year, Integer month, Set<Long> excludeIds);

    public List<Post> getTopPosts(Integer count, Integer year, Integer month, Long categoryId, Set<Long> excludeIds);

    public List<AbstractPost> getNextPosts(AbstractPost post);

    public List<AbstractPost> getPreviousPosts(AbstractPost post);

    public List<AbstractPost> getNextRelatedPosts(AbstractPost post);

    public List<AbstractPost> getPreviousRelatedPosts(AbstractPost post);
}
