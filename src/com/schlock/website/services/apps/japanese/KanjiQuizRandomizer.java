package com.schlock.website.services.apps.japanese;

import java.util.List;

public interface KanjiQuizRandomizer
{
    public List<String> getKanjiList();

    public List<String> getKanjiList(int lesson);

}
