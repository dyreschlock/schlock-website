package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.Post;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface PostManagement
{
    Post createPost(String postTitle, String postContent);

    Post createPost(Date created, String postTitle, String postContent);

    Date getUpdatedTime(Page page);

    void regenerateAllPostHTML();

    void regeneratePostNumbers();

    String generateCommentHTML(String comment);

    String generatePostHTML(String htmlContents);

    String generatePostHTML(AbstractPost post, boolean rssFeed);

    String generatePostPreview(AbstractPost post);

    String generatePostDescription(AbstractPost post);

    String updateLinkToModernReference(String link);

    String wrapJapaneseTextInTags(String html);

    String getStylizedHTMLTitle(AbstractPost post);

    List<Post> getTopPosts(Integer count, Long categoryId, Set<Long> excludeIds);

    List<Post> getTopPosts(Integer count, Integer year, Integer month, Set<Long> excludeIds);

    List<Post> getTopPosts(Integer count, Integer year, Integer month, Long categoryId, Set<Long> excludeIds);

    List<AbstractPost> getNextPosts(AbstractPost post);

    List<AbstractPost> getPreviousPosts(AbstractPost post);

    List<AbstractPost> getNextRelatedPosts(AbstractPost post);

    List<AbstractPost> getPreviousRelatedPosts(AbstractPost post);
}
