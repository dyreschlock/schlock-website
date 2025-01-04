package com.schlock.website.entities.old;

import com.schlock.website.pages.old.v1.V1Index;
import com.schlock.website.pages.old.v2.V2Index;
import com.schlock.website.pages.old.v7.V7Index;

public enum SiteVersion
{
    V1(V1Index.class),
    V2(V2Index.class),
    V3(null),
    V4(null),
    V5(null),
    V6(null),
    V7(V7Index.class);

    private final Class indexClass;

    SiteVersion(Class indexClass)
    {
        this.indexClass = indexClass;
    }


    public Class indexClass()
    {
        return indexClass;
    }
}
