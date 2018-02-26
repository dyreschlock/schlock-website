package com.schlock.website.services.database.apps.bingo.impl;

import com.schlock.website.entities.apps.bingo.BingoOption;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.bingo.BingoOptionDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class BingoOptionDAOImpl extends BaseDAOImpl<BingoOption> implements BingoOptionDAO
{
    public BingoOptionDAOImpl(Session session) { super(BingoOption.class, session); }

    public List<BingoOption> getAllBySheet(String sheet)
    {
        String text = " from BingoOption b " +
                        " where b.sheet = :sheet " +
                        " and b.skip = false ";

        Query query = session.createQuery(text);
        query.setString("sheet", sheet);

        List<BingoOption> options = query.list();
        return options;
    }
}
