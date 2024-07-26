package com.schlock.website.services.database.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.PokemonCustomCounter;
import com.schlock.website.entities.apps.pokemon.PokemonCustomCounterAccount;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface PokemonCustomCounterDAO extends BaseDAO<PokemonCustomCounter>
{
    List<PokemonCustomCounter> getByAccount(PokemonCustomCounterAccount account);
}
