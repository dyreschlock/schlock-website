package com.schlock.website.entities.blog;

import java.util.Comparator;

public class CategoryComparator implements Comparator<AbstractCategory>
{
    public int compare(AbstractCategory category1, AbstractCategory category2)
    {
        int c1 = 0;
        int c2 = 0;

        if (category1.isTopCategory() && category2.isTopCategory())
        {
            c1 = category1.getOrdering();
            c2 = category2.getOrdering();
        }
        else if(category1.isTopCategory())
        {
            c1 = category1.getOrdering();
            c2 = category2.getParent().getOrdering();
        }
        else if (category2.isTopCategory())
        {
            c1 = category1.getParent().getOrdering();
            c2 = category2.getOrdering();
        }
        else
        {
            AbstractCategory parent1 = category1.getParent();
            AbstractCategory parent2 = category2.getParent();

            c1 = parent1.getOrdering();
            c2 = parent2.getOrdering();

            if (c1 == c2)
            {
                c1 = category1.getOrdering();
                c2 = category2.getOrdering();
            }
        }


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
