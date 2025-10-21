package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.services.blog.KeywordManagement;
import com.schlock.website.services.blog.LessonsManagement;
import com.schlock.website.services.database.blog.KeywordDAO;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class KeywordManagementImpl implements KeywordManagement
{
    private KeywordDAO keywordDAO;

    public KeywordManagementImpl(KeywordDAO keywordDAO)
    {
        this.keywordDAO = keywordDAO;
    }


    public List<Keyword> generateKeywords(String keyString)
    {
        List<Keyword> keywords = new ArrayList<Keyword>();

        if (StringUtils.isNotBlank(keyString))
        {
            String[] parts = StringUtils.split(keyString, Keyword.DELIMITER);
            for (String part : parts)
            {
                String key = StringUtils.strip(part);

                Keyword keyword = getOrCreateKeyword(key);
                if (!keywords.contains(keyword))
                {
                    keywords.add(keyword);
                }
            }
        }
        return keywords;
    }


    public Keyword getOrCreateKeyword(String name)
    {
        Keyword keyword = keywordDAO.getByName(name);
        if (keyword == null)
        {
            keyword = new Keyword(name);

            keywordDAO.save(keyword);
        }
        return keyword;
    }

    public List<Object[]> getAllAvailableKeywordNamesAndWeights()
    {
        List<String> excludes = Arrays.asList(LessonsManagement.SIXTH_GRADE,
                                                LessonsManagement.FIFTH_GRADE,
                                                LessonsManagement.FOURTH_GRADE,
                                                LessonsManagement.THIRD_GRADE,
                                                LessonsManagement.SECOND_GRADE,
                                                LessonsManagement.FIRST_GRADE,
                                                LessonsManagement.HEISEI28,
                                                LessonsManagement.HEISEI27,
                                                LessonsManagement.HEISEI26,
                                                LessonsManagement.HEISEI25);

        List<Object[]> namesAndWeights = new ArrayList<>();

        List<Object[]> namesAndCounts = keywordDAO.getAllAvailable();
        for(Object[] entry : namesAndCounts)
        {
            String name = (String) entry[0];
            if (!excludes.contains(name))
            {
                long count = (long) entry[2];

                Integer weight = 1;
                if (count >= 25)
                {
                    weight = 6;
                }
                else if (count >= 13)
                {
                    weight = 5;
                }
                else if(count >= 7)
                {
                    weight = 4;
                }
                else if (count >= 5)
                {
                    weight = 3;
                }
                else if (count >= 3)
                {
                    weight = 2;
                }

                Calendar recent = Calendar.getInstance();
                recent.setTime((Date) entry[3]);

                int year = recent.get(Calendar.YEAR);
                if (year >= 2015)
                {
                    //nothing
                }
                else if(year >= 2010)
                {
                    weight = weight -1;
                }
                else
                {
                    weight = weight -3;
                }

                if (weight < 1)
                {
                    weight = 1;
                }

                String title = (String) entry[1];
                if (title == null)
                {
                    title = getKeywordTitle(name);
                }

                Object[] newEntry = new Object[3];
                newEntry[0] = name;
                newEntry[1] = title;
                newEntry[2] = weight;

                namesAndWeights.add(newEntry);
            }
        }

        Collections.sort(namesAndWeights, new Comparator<Object[]>()
        {
            public int compare(Object[] o1, Object[] o2)
            {
                String t1 = (String) o1[1];
                String t2 = (String) o2[1];

                return t1.compareTo(t2);
            }

        });

        return namesAndWeights;
    }


    public String getKeywordTitle(String name)
    {
        final List<String> UPPERCASE = Arrays.asList("pc", "pax", "rpg", "ps2", "ps3", "psp", "3ds", "ds", "dj", "cd", "opl", "ddr");

        String[] words = name.split("-");

        String title = "";
        for(int i = 0; i < words.length; i++)
        {
            if (i != 0)
            {
                title += " ";
            }
            String word = words[i];
            if (UPPERCASE.contains(word))
            {
                title += word.toUpperCase();
            }
            else
            {
                title += StringUtils.capitalize(words[i]);
            }
        }
        return title;
    }
}
