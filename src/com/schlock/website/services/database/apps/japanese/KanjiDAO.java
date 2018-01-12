package com.schlock.website.services.database.apps.japanese;

import com.schlock.website.entities.apps.japanese.Kanji;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface KanjiDAO extends BaseDAO<Kanji>
{
    public List<Kanji> getAllFromLesson(int lesson, boolean only);
}
