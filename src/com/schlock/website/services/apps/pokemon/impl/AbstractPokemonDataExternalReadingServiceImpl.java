package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.PokemonData;
import com.schlock.website.entities.apps.pokemon.PokemonMove;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pokemon.PokemonDataExternalReadingService;
import org.apache.tapestry5.json.JSONArray;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractPokemonDataExternalReadingServiceImpl implements PokemonDataExternalReadingService
{
    private static final String POKEMON_DIR = "pokemon-data/counter-data/";

    private final DeploymentContext deploymentContext;

    protected HashMap<String, PokemonMove> moveData = new HashMap<String, PokemonMove>();
    protected HashMap<String, PokemonData> pokemonData = new HashMap<String, PokemonData>();

    public AbstractPokemonDataExternalReadingServiceImpl(DeploymentContext deploymentContext)
    {
        this.deploymentContext = deploymentContext;
    }



    protected abstract void loadAllJSONdata();

    protected abstract PokemonMove getMoveFromDatabase(PokemonMove move);
    protected abstract PokemonData getPokemonFromDatabase(PokemonData pokemon);

    protected abstract boolean isIgnorePokemon(PokemonData pokemon);

    protected abstract String getMoveIdentifier(PokemonMove move);
    protected abstract String getPokemonIdentifier(PokemonData pokemon);

    public List<String> reportDifferences()
    {
        final String NEW_MOVE = "A new move was found in JSON: %s";
        final String NEW_POKEMON = "A new pokemon was found in JSON: %s";

        loadAllJSONdata();

        List<String> messages = new ArrayList<String>();

        for(PokemonMove json : moveData.values())
        {
            PokemonMove database = getMoveFromDatabase(json);
            if (database == null)
            {
                messages.add(String.format(NEW_MOVE, getMoveIdentifier(json)));
            }
            else
            {

            }
        }

        int count = 0;
        for(PokemonData json : pokemonData.values())
        {
            if (!isIgnorePokemon(json))
            {
                PokemonData database = getPokemonFromDatabase(json);
                if (database == null)
                {
                    messages.add(String.format(NEW_POKEMON, getPokemonIdentifier(json)));
                    count++;
                }
                else
                {



                }
            }
        }

        messages.add("There are " + count + " pokemon not in database.");

        return messages;
    }

    public void updateDatabase()
    {

    }


    protected JSONArray readJSONArrayFromFile(String filename)
    {
        StringBuilder content = new StringBuilder();
        try
        {
            String fileLocation = deploymentContext.dataDirectory() + POKEMON_DIR + filename;

            InputStream in = new FileInputStream(fileLocation);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line = reader.readLine();
            while (line != null)
            {
                content.append(line);

                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return new JSONArray(content.toString());
    }

}
