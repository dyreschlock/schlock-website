package com.schlock.website.services.blog.impl;

import com.schlock.website.services.blog.LayoutManagement;

public class LayoutManagementImpl implements LayoutManagement
{
    public String getColumnClassByIndex(int index)
    {
        String cls = "";

        if (index % 4 == 3)
        {
            cls += " fourColumnLast";
        }
        if (index % 3 == 2)
        {
            cls += " threeColumnLast";
        }
        if (index % 2 == 1)
        {
            cls += " twoColumnLast";
        }
        return cls;
    }
}
