package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.Keyword;

import java.util.List;

public interface KeywordManagement
{
    public List<Keyword> generateKeywords(String keyString);

    public Keyword getOrCreateKeyword(String name);
}
