package com.schlock.website.services.database.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.PokemonData;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface PokemonDataDAO extends BaseDAO<PokemonData>
{
    List<PokemonData> getRaidBosses();

    PokemonData getByName(String name);

    PokemonData getByNameId(String nameId);
}
