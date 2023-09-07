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
        loadAllJSONdata();

        List<String> messages = new ArrayList<String>();
        messages.addAll(reportMoveDifferences());
        messages.addAll(reportPokemonDifferences());

        return messages;
    }

    private List<String> reportMoveDifferences()
    {
        final String NEW_MOVE = "A new move was found in JSON: %s";


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
                String id = getMoveIdentifier(json);

                String pvpChargeEnergy = reportStatDifference(id, "pvpChargeEnergy", database.getPvpChargeEnergy(), json.getPvpChargeEnergy());
                if (pvpChargeEnergy != null)
                {
                    messages.add(pvpChargeEnergy);
                }

                String pvpChargeDamage = reportStatDifference(id, "pvpChargeDamage", database.getPvpChargeDamage(), json.getPvpChargeDamage());
                if (pvpChargeDamage != null)
                {
                    messages.add(pvpChargeDamage);
                }

                String pvpFastEnergy = reportStatDifference(id, "pvpFastEnergy", database.getPvpFastEnergy(), json.getPvpFastEnergy());
                if (pvpFastEnergy != null)
                {
                    messages.add(pvpFastEnergy);
                }

                String pvpFastPower = reportStatDifference(id, "pvpFastPower", database.getPvpFastPower(), json.getPvpFastPower());
                if (pvpFastPower != null)
                {
                    messages.add(pvpFastPower);
                }

                String pvpFastDuration = reportStatDifference(id, "pvpFastDuration", database.getPvpFastDuration(), json.getPvpFastDuration());
                if (pvpFastDuration != null)
                {
                    messages.add(pvpFastDuration);
                }


                //        this.pvpChargeEnergy = updates.pvpChargeEnergy;
//        this.pvpChargeDamage = updates.pvpChargeDamage;
//
//        this.pvpFastEnergy = updates.pvpFastEnergy;
//        this.pvpFastPower = updates.pvpFastPower;
//        this.pvpFastDuration = updates.pvpFastDuration;

            }
        }

        return messages;
    }

    private List<String> reportPokemonDifferences()
    {
        final String NEW_POKEMON = "A new pokemon was found in JSON: %s";

        List<String> messages = new ArrayList<String>();

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
                    String id = getPokemonIdentifier(json);

                    String stamina = reportStatDifference(id, "Stamina", database.getBaseStamina(), json.getBaseStamina());
                    if (stamina != null)
                    {
                        messages.add(stamina);
                    }

                    String attack = reportStatDifference(id, "Attack", database.getBaseAttack(), json.getBaseAttack());
                    if (attack != null)
                    {
                        messages.add(attack);
                    }

                    String defense = reportStatDifference(id, "Defense", database.getBaseDefense(), json.getBaseDefense());
                    if (defense != null)
                    {
                        messages.add(defense);
                    }


                }
            }
        }

        messages.add("There are " + count + " pokemon not in database.");

        return messages;
    }

    private String reportStatDifference(String id, String stat, int oldValue, int newValue)
    {
        final String DIFFERENT_STATS = "%s has different %s: old=%s new=%s";

        if (newValue != 0 && newValue != oldValue)
        {
            return String.format(DIFFERENT_STATS, id, stat, oldValue, newValue);
        }
        return null;
    }

    private String reportStatDifference(String id, String stat, double oldValue, double newValue)
    {
        final String DIFFERENT_STATS = "%s has different %s: old=%s new=%s";

        if (newValue != 0 && newValue != oldValue)
        {
            return String.format(DIFFERENT_STATS, id, stat, oldValue, newValue);
        }
        return null;
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
