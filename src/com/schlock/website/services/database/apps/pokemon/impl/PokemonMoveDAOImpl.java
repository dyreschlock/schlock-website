package com.schlock.website.services.database.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.PokemonMove;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.pokemon.PokemonMoveDAO;
import org.hibernate.Query;
import org.hibernate.Session;

public class PokemonMoveDAOImpl extends BaseDAOImpl<PokemonMove> implements PokemonMoveDAO
{
    public PokemonMoveDAOImpl(Session session)
    {
        super(PokemonMove.class, session);
    }

    public PokemonMove getByName(String name)
    {
        String text = "select m " +
                        " from PokemonMove m " +
                        "where m.name = :name ";

        Query query = session.createQuery(text);
        query.setParameter("name", name);

        return (PokemonMove) singleResult(query);
    }

    public PokemonMove getByNameId(String nameId)
    {
        String text = " select m " +
                        " from PokemonMove m " +
                        " where m.nameId = :nameId ";

        Query query = session.createQuery(text);
        query.setParameter("nameId", nameId);

        return (PokemonMove) singleResult(query);
    }
}
