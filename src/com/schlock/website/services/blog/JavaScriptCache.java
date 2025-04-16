package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.AbstractPost;

public interface JavaScriptCache
{
    boolean isHasCustomJavascript(AbstractPost post);

    String getCustomJavascript(AbstractPost post);

    String getCustomJavascript(String uuid);
}
