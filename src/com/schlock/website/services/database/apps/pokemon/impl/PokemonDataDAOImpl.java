package com.schlock.website.services.database.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.PokemonData;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.pokemon.PokemonDataDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class PokemonDataDAOImpl extends BaseDAOImpl<PokemonData> implements PokemonDataDAO
{
    public PokemonDataDAOImpl(Session session)
    {
        super(PokemonData.class, session);
    }


    public List<PokemonData> getRaidBosses()
    {
        String text = " select p " +
                        "from PokemonData p " +
                        "where p.raidBoss is true " +
                        "order by p.number asc ";

        Query query = session.createQuery(text);

        return query.list();
    }

    public PokemonData getByName(String name)
    {
        String text = " select p " +
                        " from PokemonData p " +
                        " where p.name = :name ";

        Query query = session.createQuery(text);
        query.setParameter("name", name);

        return (PokemonData) singleResult(query);
    }

    public PokemonData getByNameId(String nameId)
    {
        String text = " select p " +
                        " from PokemonData p " +
                        " where p.nameId = :nameId ";

        Query query = session.createQuery(text);
        query.setParameter("nameId", nameId);

        return (PokemonData) singleResult(query);
    }
}
