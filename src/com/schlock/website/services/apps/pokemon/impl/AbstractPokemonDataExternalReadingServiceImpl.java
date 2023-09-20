package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.PokemonData;
import com.schlock.website.entities.apps.pokemon.PokemonMove;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pokemon.PokemonDataExternalReadingService;
import com.schlock.website.services.database.apps.pokemon.PokemonDataDAO;
import com.schlock.website.services.database.apps.pokemon.PokemonMoveDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.json.JSONArray;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

public abstract class AbstractPokemonDataExternalReadingServiceImpl implements PokemonDataExternalReadingService
{
    private static final String POKEMON_DIR = "pokemon-data/counter-data/";

    private final DeploymentContext deploymentContext;

    protected final PokemonDataDAO dataDAO;
    protected final PokemonMoveDAO moveDAO;

    protected HashMap<String, PokemonMove> moveData = new HashMap<String, PokemonMove>();
    protected HashMap<String, PokemonData> pokemonData = new HashMap<String, PokemonData>();

    public AbstractPokemonDataExternalReadingServiceImpl(DeploymentContext deploymentContext,
                                                         PokemonDataDAO dataDAO,
                                                         PokemonMoveDAO moveDAO)
    {
        this.deploymentContext = deploymentContext;

        this.dataDAO = dataDAO;
        this.moveDAO = moveDAO;
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

        int missingMove = 0;
        int differentMainStats = 0;
        int differentPvpStats = 0;

        for(PokemonMove json : moveData.values())
        {
            PokemonMove database = getMoveFromDatabase(json);
            if (database == null)
            {
                messages.add(String.format(NEW_MOVE, getMoveIdentifier(json)));
                missingMove++;
            }
            else
            {
                String id = getMoveIdentifier(json);

                int before = messages.size();

                messages = reportStatDifference(messages, id, "power", database.getPower(), json.getPower());
                messages = reportStatDifference(messages, id, "cooldown", database.getCooldown(), json.getCooldown());
                messages = reportStatDifference(messages, id, "energyCost", database.getEnergyCost(), json.getEnergyCost());
                messages = reportStatDifference(messages, id, "energyGain", database.getEnergyGain(), json.getEnergyGain());
                messages = reportStatDifference(messages, id, "dodgeWindow", database.getDodgeWindow(), json.getDodgeWindow());
                messages = reportStatDifference(messages, id, "damageWindow", database.getDamageWindow(), json.getDamageWindow());

                int after = messages.size();

                differentMainStats += (after - before);

                before = messages.size();

                messages = reportStatDifference(messages, id, "pvpChargeEnergy", database.getPvpChargeEnergy(), json.getPvpChargeEnergy());
                messages = reportStatDifference(messages, id, "pvpChargeDamage", database.getPvpChargeDamage(), json.getPvpChargeDamage());
                messages = reportStatDifference(messages, id, "pvpFastEnergy", database.getPvpFastEnergy(), json.getPvpFastEnergy());
                messages = reportStatDifference(messages, id, "pvpFastPower", database.getPvpFastPower(), json.getPvpFastPower());
                messages = reportStatDifference(messages, id, "pvpFastDuration", database.getPvpFastDuration(), json.getPvpFastDuration());

                after = messages.size();

                differentPvpStats += (after - before);
            }
        }

        messages.add("[Moves] There are " + missingMove + " moves not in database.");
        messages.add("[Moves] There are " + differentMainStats + " main stat changes for moves.");
        messages.add("[Moves] There are " + differentPvpStats + " pvp stat changes from moves.");

        return messages;
    }

    private List<String> reportPokemonDifferences()
    {
        final String NEW_POKEMON = "A new pokemon was found in JSON: %s";

        List<String> messages = new ArrayList<String>();

        int missingPokemon = 0;
        int differentStats = 0;
        int missingMoves = 0;
        for(PokemonData json : pokemonData.values())
        {
            if (!isIgnorePokemon(json))
            {
                PokemonData database = getPokemonFromDatabase(json);
                if (database == null)
                {
                    messages.add(String.format(NEW_POKEMON, getPokemonIdentifier(json)));
                    missingPokemon++;
                }
                else
                {
                    String id = getPokemonIdentifier(json);

                    int before = messages.size();

                    messages = reportStatDifference(messages, id, "Stamina", database.getBaseStamina(), json.getBaseStamina());
                    messages = reportStatDifference(messages, id, "Attack", database.getBaseAttack(), json.getBaseAttack());
                    messages = reportStatDifference(messages, id, "Defense", database.getBaseDefense(), json.getBaseDefense());

                    int after = messages.size();

                    differentStats += (after - before);


                    List<String> standardMoves = reportDifferentMoves(id, "Standard", database.getStandardMoves(), json.getStandardMoves());
                    messages.addAll(standardMoves);

                    List<String> allMoves = reportDifferentMoves(id, "All", database.getAllMoves(), json.getAllMoves());
                    messages.addAll(allMoves);

                    missingMoves += standardMoves.size();
                    missingMoves += allMoves.size();
                }
            }
        }

        messages.add("[Pokemon] There are " + missingPokemon + " pokemon not in database.");
        messages.add("[Pokemon] There are " + differentStats + " stat changes for Pokemon.");
        messages.add("[Pokemon] There are " + missingMoves + " move changes for Pokemon.");

        return messages;
    }

    private List<String> reportStatDifference(List<String> messages, String id, String stat, int oldValue, int newValue)
    {
        final String DIFFERENT_STATS = "%s has different %s: old=%s new=%s";

        if (newValue != 0 && newValue != oldValue)
        {
            messages.add(String.format(DIFFERENT_STATS, id, stat, oldValue, newValue));
        }
        return messages;
    }

    private List<String> reportStatDifference(List<String> messages, String id, String stat, double oldValue, double newValue)
    {
        final String DIFFERENT_STATS = "%s has different %s: old=%s new=%s";

        if (newValue != 0 && newValue != oldValue)
        {
            messages.add(String.format(DIFFERENT_STATS, id, stat, oldValue, newValue));
        }
        return messages;
    }

    private List<String> reportStatDifference(List<String> messages, String id, String stat, String oldValue, String newValue)
    {
        final String DIFFERENT_STATS = "%s has different %s: old=%s new=%s";

        if (!StringUtils.equals(newValue, oldValue))
        {
            messages.add(String.format(DIFFERENT_STATS, id, stat, oldValue, newValue));
        }
        return messages;
    }

    private List<String> reportDifferentMoves(String id, String stat, Set<PokemonMove> oldMoves, Set<PokemonMove> newMoves)
    {
        final String NEW_MOVE = "%s has a new %s move added: %s";
        final String OLD_MOVE = "%s has an old %s move removed: %s";

        Set<String> sameMoves = getSameMoves(oldMoves, newMoves);

        List<String> messages = new ArrayList<String>();
        for(PokemonMove newMove : newMoves)
        {
            if (!sameMoves.contains(newMove.getNameId()))
            {
                messages.add(String.format(NEW_MOVE, id, stat, newMove.getNameId()));
            }
        }
        for(PokemonMove oldMove : oldMoves)
        {
            if (!sameMoves.contains(oldMove.getNameId()))
            {
                messages.add(String.format(OLD_MOVE, id, stat, oldMove.getNameId()));
            }
        }
        return messages;
    }

    private Set<String> getSameMoves(Set<PokemonMove> moveset1, Set<PokemonMove> moveset2)
    {
        Set<String> sameMoves = new HashSet<String>();
        for(PokemonMove move1 : moveset1)
        {
            for(PokemonMove move2 : moveset2)
            {
                String id1 = getMoveIdentifier(move1);
                String id2 = getMoveIdentifier(move2);

                if (StringUtils.equals(id1, id2) && !sameMoves.contains(id1))
                {
                    sameMoves.add(id1);
                }
            }
        }
        return sameMoves;
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


    public List<String> updateAll()
    {
        List<String> messages = new ArrayList<String>();

        messages.addAll(updateMovesAddNew());
        messages.addAll(updateMovesMainStats());
        messages.addAll(updateMovesPvpStats());

        messages.addAll(updatePokemonAddNew());
        messages.addAll(updatePokemonStats());
        messages.addAll(updatePokemonMoves());

        return messages;
    }

    public List<String> updateMovesAddNew()
    {
        loadAllJSONdata();

        final String ADD_MESSAGE = "[Move] New Pokemon Move Added: %s";
        final String COUNT_MESSAGE = "[Move] %s New Moves have been added.";
        final String NO_MESSAGE = "[Move] No new moves were added. None were found.";

        List<String> messages = new ArrayList<String>();

        for(PokemonMove jsonMove : moveData.values())
        {
            PokemonMove database = getMoveFromDatabase(jsonMove);
            if (database == null)
            {
                moveDAO.save(jsonMove);

                messages.add(String.format(ADD_MESSAGE, getMoveIdentifier(jsonMove)));
            }
        }

        messages = addCountMessage(messages, COUNT_MESSAGE, NO_MESSAGE);
        return messages;
    }

    public List<String> updateMovesMainStats()
    {
        loadAllJSONdata();

        final String COUNT_MESSAGE = "[Move] %s Stats have been changed.";
        final String NO_MESSAGE = "[Move] No Standard Stats has been changed. No differences found.";

        List<String> messages = new ArrayList<String>();

        for(PokemonMove jsonMove : moveData.values())
        {
            PokemonMove database = getMoveFromDatabase(jsonMove);
            if (database != null)
            {
                messages = updateStringFieldPokemonMove(messages, database, "DodgeWindow", jsonMove.getDodgeWindow());
                messages = updateStringFieldPokemonMove(messages, database, "DamageWindow", jsonMove.getDamageWindow());

                messages = updateIntegerFieldPokemonMove(messages, database, "EnergyGain", jsonMove.getEnergyGain());
                messages = updateIntegerFieldPokemonMove(messages, database, "EnergyCost", jsonMove.getEnergyCost());
                messages = updateIntegerFieldPokemonMove(messages, database, "Power", jsonMove.getPower());

                messages = updateDoubleFieldPokemonMove(messages, database, "Cooldown", jsonMove.getCooldown());
            }
        }

        messages = addCountMessage(messages, COUNT_MESSAGE, NO_MESSAGE);
        return messages;
    }

    public List<String> updateMovesPvpStats()
    {
        loadAllJSONdata();

        final String COUNT_MESSAGE = "[Move] %s PVP Stats have been changed.";
        final String NO_MESSAGE = "[Move] No PVP Stats have been changed. No differences found.";

        List<String> messages = new ArrayList<String>();

        for(PokemonMove jsonMove : moveData.values())
        {
            PokemonMove database = getMoveFromDatabase(jsonMove);
            if (database != null)
            {
                messages = updateIntegerFieldPokemonMove(messages, database, "PvpChargeEnergy", jsonMove.getPvpChargeEnergy());
                messages = updateIntegerFieldPokemonMove(messages, database, "PvpChargeDamage", jsonMove.getPvpChargeDamage());
                messages = updateIntegerFieldPokemonMove(messages, database, "PvpFastEnergy", jsonMove.getPvpFastEnergy());
                messages = updateIntegerFieldPokemonMove(messages, database, "PvpFastPower", jsonMove.getPvpFastPower());
                messages = updateDoubleFieldPokemonMove(messages, database, "PvpFastDuration", jsonMove.getPvpFastDuration());
            }
        }

        messages = addCountMessage(messages, COUNT_MESSAGE, NO_MESSAGE);
        return messages;
    }

    public List<String> updatePokemonAddNew()
    {
        loadAllJSONdata();

        List<String> messages = new ArrayList<String>();

        return messages;
    }

    public List<String> updatePokemonMoves()
    {
        loadAllJSONdata();

        final String COUNT_MESSAGE = "[Pokemon] %s moves have been added to Pokemon.";
        final String NO_MESSAGE = "[Pokemon] No new moves have been added. No differences found.";

        List<String> messages = new ArrayList<String>();

        for(PokemonData jsonPoke : pokemonData.values())
        {
            PokemonData database = getPokemonFromDatabase(jsonPoke);
            if (database != null)
            {
                messages = addMissingStandardMoves(messages, database, jsonPoke.getStandardMoves());
                messages = addMissingAllMoves(messages, database, jsonPoke.getAllMoves());
            }
        }

        messages = addCountMessage(messages, COUNT_MESSAGE, NO_MESSAGE);
        return messages;
    }

    private List<String> addMissingAllMoves(List<String> messages, PokemonData pokemon, Set<PokemonMove> newMoves)
    {
        final String NEW_ALL_MOVE = "[Pokemon] %s has been given a new All move: %s";

        Set<String> sameMoves = getSameMoves(pokemon.getAllMoves(), newMoves);
        if (newMoves.size() > sameMoves.size())
        {
            for(PokemonMove newMove : newMoves)
            {
                String id = getMoveIdentifier(newMove);
                if (!sameMoves.contains(id))
                {
                    PokemonMove move = getMoveFromDatabase(newMove);
                    pokemon.getAllMoves().add(move);

                    messages.add(String.format(NEW_ALL_MOVE, pokemon.getNameId(), id));
                }
            }

            String moveNames = getMoveNames(pokemon.getAllMoves());
            pokemon.setAllMoveNames(moveNames);

            dataDAO.save(pokemon);
        }
        return messages;
    }

    private List<String> addMissingStandardMoves(List<String> messages, PokemonData pokemon, Set<PokemonMove> newMoves)
    {
        final String NEW_STD_MOVE = "[Pokemon] %s has been given a new Standard move: %s";

        Set<String> sameMoves = getSameMoves(pokemon.getStandardMoves(), newMoves);
        if (newMoves.size() > sameMoves.size())
        {
            for(PokemonMove newMove : newMoves)
            {
                String id = getMoveIdentifier(newMove);
                if (!sameMoves.contains(id))
                {
                    PokemonMove move = getMoveFromDatabase(newMove);
                    pokemon.getStandardMoves().add(move);

                    messages.add(String.format(NEW_STD_MOVE, pokemon.getNameId(), id));
                }
            }

            String moveNames = getMoveNames(pokemon.getStandardMoves());
            pokemon.setStandardMoveNames(moveNames);

            dataDAO.save(pokemon);
        }
        return messages;
    }

    private String getMoveNames(Set<PokemonMove> moves)
    {
        StringBuilder sb = new StringBuilder();

        for(PokemonMove move : moves)
        {
            if (sb.length() > 0)
            {
                sb.append(",");
            }
            sb.append(move.getName());
        }
        return sb.toString();
    }

    public List<String> updatePokemonStats()
    {
        loadAllJSONdata();

        final String COUNT_MESSAGE = "[Pokemon] %s stats have been updated for Pokemon.";
        final String NO_MESSAGE = "[Pokemon] No stats have been changed. No differences found.";

        List<String> messages = new ArrayList<String>();

        for(PokemonData jsonPoke : pokemonData.values())
        {
            PokemonData database = getPokemonFromDatabase(jsonPoke);
            if (database != null)
            {
                messages = updateIntegerFieldPokemon(messages, database, "BaseStamina", jsonPoke.getBaseStamina());
                messages = updateIntegerFieldPokemon(messages, database, "BaseAttack", jsonPoke.getBaseAttack());
                messages = updateIntegerFieldPokemon(messages, database, "BaseDefense", jsonPoke.getBaseDefense());
            }
        }

        messages = addCountMessage(messages, COUNT_MESSAGE, NO_MESSAGE);
        return messages;
    }


    private List<String> updateIntegerFieldPokemon(List<String> messages, PokemonData target, final String fieldName, int newValue)
    {
        final String GETTER = "get" + fieldName;
        final String SETTER = "set" + fieldName;

        try
        {
            Method getter = target.getClass().getMethod(GETTER);
            Object returnValue = getter.invoke(target);

            int oldValue = ((Integer) returnValue).intValue();

            if (newValue != 0 && newValue != oldValue)
            {
                final String MESSAGE = "[Pokemon] %s changed for %s / old=%s new=%s";
                String message = String.format(MESSAGE, fieldName, target.getNameId(), oldValue, newValue);

                Method setter = target.getClass().getMethod(SETTER, int.class);
                setter.invoke(target, newValue);

                dataDAO.save(target);

                messages.add(message);
            }
            return messages;
        }
        catch(Exception e)
        {
            throw new RuntimeException("Something wrong with " + target.getNameId(), e);
        }
    }

    private List<String> updateIntegerFieldPokemonMove(List<String> messages, PokemonMove target, final String fieldName, int newValue)
    {
        final String GETTER = "get" + fieldName;
        final String SETTER = "set" + fieldName;

        try
        {
            Method getter = target.getClass().getMethod(GETTER);
            Object returnValue = getter.invoke(target);

            int oldValue = ((Integer) returnValue).intValue();

            if (newValue != 0 && newValue != oldValue)
            {
                final String MESSAGE = "[Move] %s changed for %s / old=%s new=%s";
                String message = String.format(MESSAGE, fieldName, target.getNameId(), oldValue, newValue);

                Method setter = target.getClass().getMethod(SETTER, int.class);
                setter.invoke(target, newValue);

                moveDAO.save(target);

                messages.add(message);
            }
            return messages;
        }
        catch(Exception e)
        {
            throw new RuntimeException("Something wrong with " + target.getNameId(), e);
        }
    }

    private List<String> updateDoubleFieldPokemonMove(List<String> messages, PokemonMove target, final String fieldName, double newValue)
    {
        final String GETTER = "get" + fieldName;
        final String SETTER = "set" + fieldName;

        try
        {
            Method getter = target.getClass().getMethod(GETTER);
            Object returnValue = getter.invoke(target);

            double oldValue = ((Double) returnValue).doubleValue();

            if (newValue != 0 && newValue != oldValue)
            {
                final String MESSAGE = "[Move] %s changed for %s / old=%s new=%s";
                String message = String.format(MESSAGE, fieldName, target.getNameId(), oldValue, newValue);

                Method setter = target.getClass().getMethod(SETTER, double.class);
                setter.invoke(target, newValue);

                moveDAO.save(target);

                messages.add(message);
            }
            return messages;
        }
        catch(Exception e)
        {
            throw new RuntimeException("Something wrong with " + target.getNameId(), e);
        }
    }

    private List<String> updateStringFieldPokemonMove(List<String> messages, PokemonMove target, final String fieldName, String newValue)
    {
        final String GETTER = "get" + fieldName;
        final String SETTER = "set" + fieldName;

        try
        {
            Method getter = target.getClass().getMethod(GETTER);
            Object returnValue = getter.invoke(target);

            String oldValue = (String) returnValue;

            if(StringUtils.isNotEmpty(newValue) && !StringUtils.equals(newValue, oldValue))
            {
                final String MESSAGE = "[Move] %s changed for %s / old=%s new=%s";
                String message = String.format(MESSAGE, fieldName, target.getNameId(), oldValue, newValue);

                Method setter = target.getClass().getMethod(SETTER, String.class);
                setter.invoke(target, newValue);

                moveDAO.save(target);

                messages.add(message);
            }
            return messages;
        }
        catch(Exception e)
        {
            throw new RuntimeException("Something wrong with " + target.getNameId(), e);
        }
    }

    private List<String> addCountMessage(List<String> messages, final String COUNT_MESSAGE, final String NO_MESSAGE)
    {
        if (messages.size() > 0)
        {
            messages.add(String.format(COUNT_MESSAGE, messages.size()));
        }
        else
        {
            messages.add(NO_MESSAGE);
        }
        return messages;
    }
}
