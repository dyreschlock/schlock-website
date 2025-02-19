package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.services.blog.KeywordManagement;
import com.schlock.website.services.database.blog.KeywordDAO;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                keywords.add(keyword);
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
        return keywordDAO.getAllAvailable();
    }

    public String getKeywordTitle(String name)
    {
        final List<String> UPPERCASE = Arrays.asList("pc", "pax", "rpg", "ps2", "ps3", "psp", "3ds", "ds", "cd", "ddr");

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
