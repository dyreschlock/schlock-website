package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.AbstractPost;

public interface PostContentsManagement
{
    String getHTMLContents(AbstractPost post);

    void writeHTMLFilesForPosts();
}
