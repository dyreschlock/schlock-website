package com.schlock.website.components.old.v2;

import com.schlock.website.components.old.AbstractOldRecentPosts;
import com.schlock.website.pages.old.v2.V2Index;

public class Version2RecentPosts extends AbstractOldRecentPosts
{
    protected Class getVersionIndexClass()
    {
        return V2Index.class;
    }
}
