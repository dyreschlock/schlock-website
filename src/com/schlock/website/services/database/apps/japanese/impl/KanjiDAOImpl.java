package com.schlock.website.services.database.apps.japanese.impl;

import com.schlock.website.entities.apps.japanese.Kanji;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.japanese.KanjiDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class KanjiDAOImpl extends BaseDAOImpl<Kanji> implements KanjiDAO
{
    public KanjiDAOImpl(Session session)
    {
        super(Kanji.class, session);
    }

    public List<Kanji> getAllFromLesson(int lesson)
    {
        String text = "from Kanji k " +
                        " where k.lesson >= :lesson ";

        Query query = session.createQuery(text);
        query.setInteger("lesson", lesson);

        List<Kanji> kanji = query.list();
        return kanji;
    }
}
