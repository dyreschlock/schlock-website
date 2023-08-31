package com.schlock.website.services.database.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.PokemonData;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.pokemon.PokemonDataDAO;
import org.hibernate.Session;

public class PokemonDataDAOImpl extends BaseDAOImpl<PokemonData> implements PokemonDataDAO
{
    public PokemonDataDAOImpl(Session session)
    {
        super(PokemonData.class, session);
    }
}
