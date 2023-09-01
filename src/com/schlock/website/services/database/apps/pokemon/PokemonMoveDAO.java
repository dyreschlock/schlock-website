package com.schlock.website.services.database.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.PokemonMove;
import com.schlock.website.services.database.BaseDAO;

public interface PokemonMoveDAO extends BaseDAO<PokemonMove>
{
    PokemonMove getByName(String name);

    PokemonMove getByNameId(String nameId);
}
