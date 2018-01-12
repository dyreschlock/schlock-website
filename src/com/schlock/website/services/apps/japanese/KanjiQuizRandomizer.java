package com.schlock.website.services.apps.japanese;

import java.util.List;

public interface KanjiQuizRandomizer
{
    public List<String> getKanjiList(String lessonFlag, String onlyFlag);
}
