package com.schlock.website.entities.old;

import com.schlock.website.pages.old.v1.V1Index;
import com.schlock.website.pages.old.v2.V2Index;
import com.schlock.website.pages.old.v3.V3Index;
import com.schlock.website.pages.old.v4.V4Index;
import com.schlock.website.pages.old.v5.V5Index;
import com.schlock.website.pages.old.v6.V6Index;
import com.schlock.website.pages.old.v7.V7Index;

public enum SiteVersion
{
    V1(V1Index.class, false),
    V2(V2Index.class, false),
    V3(V3Index.class, false),
    V4(V4Index.class, false),
    V5(V5Index.class, false),
    V6(V6Index.class, true),
    V7(V7Index.class, true);

    private final Class indexClass;

    private final boolean allPosts;

    SiteVersion(Class indexClass, boolean allPosts)
    {
        this.indexClass = indexClass;
        this.allPosts = allPosts;
    }


    public boolean isChangeLinks()
    {
        return this == V6 || this == V7;
    }

    public Class indexClass()
    {
        return indexClass;
    }

    public boolean isAllPosts()
    {
        return allPosts;
    }
}
