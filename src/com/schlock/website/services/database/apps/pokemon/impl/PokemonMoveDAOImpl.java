package com.schlock.website.services.database.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.PokemonMove;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.pokemon.PokemonMoveDAO;
import org.hibernate.Session;

public class PokemonMoveDAOImpl extends BaseDAOImpl<PokemonMove> implements PokemonMoveDAO
{
    public PokemonMoveDAOImpl(Session session)
    {
        super(PokemonMove.class, session);
    }
}
