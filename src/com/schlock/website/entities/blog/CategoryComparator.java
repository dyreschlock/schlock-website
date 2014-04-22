package com.schlock.website.entities.blog;

import java.util.Comparator;

public class CategoryComparator implements Comparator<Category>
{
    public int compare(Category category1, Category category2)
    {
        int c1 = category1.getOrdering();
        int c2 = category2.getOrdering();

        if (c1 > c2)
        {
            return -1;
        }
        if (c2 < c1)
        {
            return 1;
        }
        return 0;
    }
}
