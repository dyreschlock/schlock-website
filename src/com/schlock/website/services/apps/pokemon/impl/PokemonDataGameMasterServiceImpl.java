package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.PokemonData;
import com.schlock.website.entities.apps.pokemon.PokemonMove;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pokemon.PokemonDataGameMasterService;
import com.schlock.website.services.database.apps.pokemon.PokemonDataDAO;
import com.schlock.website.services.database.apps.pokemon.PokemonMoveDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;

import java.util.*;

public class PokemonDataGameMasterServiceImpl extends AbstractPokemonDataExternalReadingServiceImpl implements PokemonDataGameMasterService
{
    private static final String GAME_MASTER_FILE = "latest.json";

    public static final String GM_TEMPLATE_ID = "templateId";

    public PokemonDataGameMasterServiceImpl(DeploymentContext deploymentContext,
                                                PokemonDataDAO dataDAO,
                                                PokemonMoveDAO moveDAO)
    {
        super(deploymentContext, dataDAO, moveDAO);
    }

    protected PokemonMove getMoveFromDatabase(PokemonMove move)
    {
        return moveDAO.getByNameId(move.getNameId());
    }

    protected PokemonData getPokemonFromDatabase(PokemonData pokemon)
    {
        return dataDAO.getByNameId(pokemon.getNameId());
    }

    protected String getMoveIdentifier(PokemonMove move)
    {
        return move.getNameId();
    }

    protected String getPokemonIdentifier(PokemonData pokemon)
    {
        return pokemon.getNameId();
    }

    protected boolean isIgnorePokemon(PokemonData pokemon)
    {
        final String NORMAL = "_NORMAL";

        List<String> IGNORE_FORMS_COSTUMES = Arrays.asList(
                "ARCEUS", "SILVALLY",
                "KORAIDON", "MIRAIDON",
                "SPEWPA", "SCATTERBUG", "VIVILLON", "FURFROU", "PYROAR", "DUDUNSPARCE",
                "FLABEBE", "FLOETTE", "FLORGES", "DEERLING", "SAWSBUCK", "FRILLISH", "JELLICENT",
                "SQUAWKABILLY", "MIMIKYU", "GASTRODON", "SHELLOS", "POLTEAGEIST", "SINISTEA", "MINIOR",
                "TATSUGIRI", "MORPEKO", "TOXTRICITY", "BURMY", "MAUSHOLD", "BASCULIN",
                "MAGEARNA", "KELDEO", "ZYGARDE",
                "BULBASAUR", "VENUSAUR", "SQUIRTLE", "BLASTOISE", "CHARMANDER", "CHARIZARD",
                "PIKACHU", "CUBCHOO", "BEARTIC", "DELIBIRD", "SLOWPOKE", "SLOWBRO", "SLOWKING",
                "AERODACTYL");


        String pokeId = pokemon.getNameId();

        for(String ignore : IGNORE_FORMS_COSTUMES)
        {
            if (StringUtils.startsWith(pokeId, ignore))
            {
                return true;
            }
        }
        for(String ignore : getIdBaseForms())
        {
            if (StringUtils.equals(pokeId, ignore))
            {
                return true;
            }
        }

        if (StringUtils.contains(pokeId, NORMAL))
        {
            String subId = pokeId.replaceAll(NORMAL, "");

            PokemonData poke = dataDAO.getByNameId(subId);
            if (poke != null)
            {
                return true;
            }
        }
        else
        {
            String subId = pokeId + NORMAL;
            if (pokemon.isShadow())
            {
                int index = pokeId.lastIndexOf("_S");

                subId = pokeId.substring(0, index) + NORMAL + pokeId.substring(index);
            }

            PokemonData poke = dataDAO.getByNameId(subId);
            if (poke != null)
            {
                return true;
            }
        }


        return false;
    }

    private List<String> getIdBaseForms()
    {
        List<String> baseIds = new ArrayList<String>();

        for(String id : pokemonData.keySet())
        {
            if (StringUtils.contains(id, "_"))
            {
                String base = id.split("_")[0];
                baseIds.add(base);
            }
        }
        return baseIds;
    }








    protected void loadAllJSONdata()
    {
        if (pokemonData.isEmpty() && moveData.isEmpty())
        {
            createObjectFromJSON();
            connectMovesToPokemon();
        }
    }

    private void createObjectFromJSON()
    {
        JSONArray objects = readJSONArrayFromFile(GAME_MASTER_FILE);

        Iterator iter = objects.iterator();
        while(iter.hasNext())
        {
            Object obj = iter.next();
            try
            {
                JSONObject json = (JSONObject) obj;

                String tempId = json.getString(GM_TEMPLATE_ID);
                try
                {
                    if (isPokemon(tempId) && !isIgnorePokemonId(tempId))
                    {
                        // create direct pokemon
                        // create shadow (if available)
                        // create mega (if available)

                        PokemonData data = PokemonData.createFromGameMasterJSON(json);
                        pokemonData.put(data.getNameId(), data);

                        PokemonData shadow = PokemonData.createShadowFromGameMasterJSON(json, data);
                        if (shadow != null && pokemonData.get(shadow.getNameId()) == null)
                        {
                            pokemonData.put(shadow.getNameId(), shadow);
                        }

                        Set<PokemonData> megas = PokemonData.createMegaFromGameMasterJSON(json, data);
                        for(PokemonData mega : megas)
                        {
                            pokemonData.put(mega.getNameId(), mega);
                        }
                    }
                    else if (isMoveStandard(tempId))
                    {
                        PokemonMove move = PokemonMove.createFromGameMasterStandardJSON(json);

                        PokemonMove current = moveData.get(move.getNameId());
                        if (current == null)
                        {
                            moveData.put(move.getNameId(), move);
                        }
                        else
                        {
                            current.updateFromGameMasterStandardJSON(move);
                        }
                    }
                    else if (isMoveCombat(tempId))
                    {
                        PokemonMove move = PokemonMove.createFormGameMasterCombatJSON(json);

                        PokemonMove current = moveData.get(move.getNameId());
                        if (current == null)
                        {
                            moveData.put(move.getNameId(), move);
                        }
                        else
                        {
                            current.updateFromGameMasterCombatJSON(move);
                        }
                    }
                }
                catch(Exception e)
                {
                    throw new RuntimeException("Something wrong with " + tempId, e);
                }
            }
            catch(ClassCastException e)
            {
            }
        }
    }

    private boolean isIgnorePokemonId(String templateId)
    {
        List<String> IGNORE_IDS = Arrays.asList("_HOME_FORM_REVERSION", "_HOME_REVERSION",
                                                    "REWARDS_PREMIUM", "REWARDS_FREE");
        for(String ignore : IGNORE_IDS)
        {
            if (StringUtils.endsWith(templateId, ignore))
            {
                return true;
            }
        }

        final String COSTUME = "_COSTUME_";

        if (StringUtils.contains(templateId, COSTUME))
        {
            return true;
        }
        return false;
    }


    private static final String POKEMON_TAG = PokemonData.GM_POKEMON_TAG;
    private static final String MOVE_TAG = PokemonMove.GM_MOVE_TAG;
    private static final String COMBAT_PREFIX = "COMBAT_V";

    private static final String V_PREFIX = "V";

    private boolean isPokemon(String templateId)
    {
        //format:
        //V0964_POKEMON_PALAFIN

        return StringUtils.startsWith(templateId, V_PREFIX) &&
                StringUtils.contains(templateId, POKEMON_TAG);
    }

    private boolean isMoveStandard(String templateId)
    {
        // standard stats format:
        // V0228_MOVE_METAL_CLAW_FAST

        return StringUtils.startsWith(templateId, V_PREFIX) &&
                StringUtils.contains(templateId, MOVE_TAG);
    }

    private boolean isMoveCombat(String templateId)
    {
        // pvp stats format:
        // COMBAT_V0228_MOVE_METAL_CLAW_FAST

        return StringUtils.startsWith(templateId, COMBAT_PREFIX) &&
                StringUtils.contains(templateId, MOVE_TAG);
    }


    private void connectMovesToPokemon()
    {
        for(PokemonData data : pokemonData.values())
        {
            String standardMoveIds = data.getStandardMoveNames();
            Set<PokemonMove> standardMoves = getMovesFromIds(standardMoveIds);

            data.setStandardMoves(standardMoves);

            String allMoveIds = data.getAllMoveNames();
            Set<PokemonMove> allMoves = getMovesFromIds(allMoveIds);

            data.setAllMoves(allMoves);
        }
    }

    private Set<PokemonMove> getMovesFromIds(String moveNameIds)
    {
        Set<PokemonMove> moves = new HashSet<PokemonMove>();
        for(String moveNameId : moveNameIds.split(","))
        {
            if (StringUtils.isNotEmpty(moveNameId.trim()))
            {
                PokemonMove move = moveData.get(moveNameId.trim());
                if (move == null)
                {
                    move = getMoveByNumber(moveNameId.trim());
                }

                if (move != null)
                {
                    moves.add(move);
                }
            }
        }
        return moves;
    }

    private PokemonMove getMoveByNumber(String moveNameId)
    {
        int uuid = 0;
        try
        {
            uuid = Integer.parseInt(moveNameId);
        }
        catch (Exception e)
        {
            return null;
        }

        for(PokemonMove move : moveData.values())
        {
            if (move.getUuid() == uuid)
            {
                return move;
            }
        }
        return null;
    }
}
