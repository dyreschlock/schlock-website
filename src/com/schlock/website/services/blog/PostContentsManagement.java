package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.Page;

public interface PostContentsManagement
{
    String getHTMLContents(Page page);
}
