package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.Keyword;

import java.util.List;

public interface KeywordManagement
{
    List<Keyword> generateKeywords(String keyString);

    Keyword getOrCreateKeyword(String name);

    List<Object[]> getAllAvailableKeywordNamesAndWeights();

    String getKeywordTitle(String name);
}
