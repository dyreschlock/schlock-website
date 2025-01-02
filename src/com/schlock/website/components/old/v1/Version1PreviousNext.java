package com.schlock.website.components.old.v1;

import com.schlock.website.components.old.AbstractOldPreviousNext;
import com.schlock.website.pages.old.v1.V1Index;

public class Version1PreviousNext extends AbstractOldPreviousNext
{
    protected Class getVersionIndexClass()
    {
        return V1Index.class;
    }

    public String getWidth()
    {
        if (isHasNext() && isHasPrevious())
        {
            return "25%";
        }
        return "50%";
    }
}
