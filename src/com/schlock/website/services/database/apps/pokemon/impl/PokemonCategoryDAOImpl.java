package com.schlock.website.services.database.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.PokemonCategory;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.pokemon.PokemonCategoryDAO;
import org.hibernate.Query;
import org.hibernate.Session;

public class PokemonCategoryDAOImpl extends BaseDAOImpl<PokemonCategory> implements PokemonCategoryDAO
{
    public PokemonCategoryDAOImpl(Session session)
    {
        super(PokemonCategory.class, session);
    }


    public PokemonCategory getByNameId(String nameId)
    {
        String text = "select c " +
                        " from PokemonCategory c " +
                        " where c.nameId = :nameId ";

        Query query = session.createQuery(text);
        query.setParameter("nameId", nameId);

        return (PokemonCategory) singleResult(query);
    }
}
