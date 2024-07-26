package com.schlock.website.services.database.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.PokemonCustomCounter;
import com.schlock.website.entities.apps.pokemon.PokemonCustomCounterAccount;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.pokemon.PokemonCustomCounterDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class PokemonCustomCounterDAOImpl extends BaseDAOImpl<PokemonCustomCounter> implements PokemonCustomCounterDAO
{
    public PokemonCustomCounterDAOImpl(Session session)
    {
        super(PokemonCustomCounter.class, session);
    }

    public List<PokemonCustomCounter> getByAccount(PokemonCustomCounterAccount account)
    {
        String text = " select c " +
                        " from PokemonCustomCounter c " +
                        " where c.account = :account ";

        Query query = session.createQuery(text);
        query.setParameter("account", account);

        return query.list();
    }
}
