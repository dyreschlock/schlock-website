package com.schlock.website.components.old.v1;

import com.schlock.website.components.old.AbstractOldRecentPosts;
import com.schlock.website.pages.old.v1.V1Index;

public class Version1RecentPosts extends AbstractOldRecentPosts
{
    protected Class getVersionIndexClass()
    {
        return V1Index.class;
    }
}
