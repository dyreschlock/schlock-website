package com.schlock.website.entities.blog;

import java.util.Comparator;

public class KeywordComparator implements Comparator<Keyword>
{
    public int compare(Keyword keyword1, Keyword keyword2)
    {
        KeywordType type1 = keyword1.getType();
        KeywordType type2 = keyword2.getType();

        if (type1 == null && type2 == null)
        {
            return keyword1.getName().compareTo(keyword2.getName());
        }
        if (type1 == null)
        {
            return -1;
        }
        if (type2 == null)
        {
            return 1;
        }

        int typeCompare = compare(type1, type2);
        if (typeCompare != 0)
        {
            return typeCompare;
        }

        Integer k1 = 0;
        Integer k2 = 0;

        if (keyword1.isTopKeyword() && keyword2.isTopKeyword())
        {
            k1 = keyword1.getOrdering();
            k2 = keyword2.getOrdering();
        }
        else if(keyword1.isTopKeyword())
        {
            k1 = keyword1.getOrdering();
            k2 = keyword2.getParent().getOrdering();
        }
        else if(keyword2.isTopKeyword())
        {
            k1 = keyword1.getParent().getOrdering();
            k2 = keyword2.getOrdering();
        }
        else
        {
            k1 = keyword1.getParent().getOrdering();
            k2 = keyword2.getParent().getOrdering();

            if (k1 == k2)
            {
                k1 = keyword1.getOrdering();
                k2 = keyword2.getOrdering();
            }
        }

        if (k1 > k2)
        {
            return 1;
        }
        if (k1 < k2)
        {
            return -1;
        }
        return 0;
    }

    private int compare(KeywordType type1, KeywordType type2)
    {
        int t1 = 0;
        int t2 = 0;

        for(int i = 0; i < KeywordType.values().length; i++)
        {
            KeywordType type = KeywordType.values()[i];

            if (type.equals(type1))
            {
                t1 = i;
            }
            if (type.equals(type2))
            {
                t2 = i;
            }
        }
        return t2 - t1;
    }
}
