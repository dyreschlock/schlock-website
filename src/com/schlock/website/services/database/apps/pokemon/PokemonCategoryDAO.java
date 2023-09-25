package com.schlock.website.services.database.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.PokemonCategory;
import com.schlock.website.services.database.BaseDAO;

public interface PokemonCategoryDAO extends BaseDAO<PokemonCategory>
{
    PokemonCategory getByNameId(String name);
}
