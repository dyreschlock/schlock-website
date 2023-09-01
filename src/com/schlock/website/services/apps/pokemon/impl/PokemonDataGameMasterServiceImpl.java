package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.PokemonMove;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pokemon.PokemonDataGameMasterService;
import com.schlock.website.services.database.apps.pokemon.PokemonMoveDAO;

public class PokemonDataGameMasterServiceImpl extends AbstractPokemonDataExternalReadingServiceImpl implements PokemonDataGameMasterService
{
    private final PokemonMoveDAO moveDAO;

    public PokemonDataGameMasterServiceImpl(DeploymentContext deploymentContext,
                                                PokemonMoveDAO moveDAO)
    {
        super(deploymentContext);

        this.moveDAO = moveDAO;
    }

    protected PokemonMove getMoveFromDatabase(PokemonMove move)
    {
        return moveDAO.getByNameId(move.getNameId());
    }



    protected void loadAllJSONdata()
    {

    }
}
