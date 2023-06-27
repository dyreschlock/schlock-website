package com.schlock.website.services.apps.pocket.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.schlock.website.entities.apps.pocket.PocketCore;
import com.schlock.website.entities.apps.pocket.PocketGame;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pocket.PocketDataService;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PocketDataServiceImpl implements PocketDataService
{
    private final static String POCKET_DIR = "pocket-utils/";

    private final static String GAMES_JSON_FILE = "games.json";
    private final static String CORES_JSON_FILE = "cores.json";

    private final DeploymentContext context;

    private List<PocketGame> cachedGames = new ArrayList<PocketGame>();
    private List<PocketCore> cachedCores = new ArrayList<PocketCore>();

    private List<String> cachedGenres = new ArrayList<String>();

    public PocketDataServiceImpl(DeploymentContext context)
    {
        this.context = context;
    }

    public List<PocketGame> getGames()
    {
        if (cachedGames.isEmpty())
        {
            cachedGames = loadGames();
        }
        return cachedGames;
    }

    public List<PocketGame> getGamesByCore(PocketCore core)
    {
        List<PocketGame> games = new ArrayList<PocketGame>();

        for(PocketGame game : getGames())
        {
            if (StringUtils.equalsIgnoreCase(core.getNamespace(), game.getCore()))
            {
                games.add(game);
            }
        }
        return games;
    }

    public List<PocketCore> getCores()
    {
        if (cachedCores.isEmpty())
        {
            cachedCores = loadCores();
        }
        return cachedCores;
    }

    public List<PocketCore> getCoresByCategory(String category)
    {
        List<PocketCore> cores = new ArrayList<PocketCore>();

        for(PocketCore core : getCores())
        {
            if (StringUtils.equalsIgnoreCase(category, core.getCategory()))
            {
                cores.add(core);
            }
        }
        return cores;
    }

    private List<PocketGame> loadGames()
    {
        String filepath = context.dataDirectory() + POCKET_DIR + GAMES_JSON_FILE;
        String jsonString = readFileContents(filepath);

        Gson gson = new GsonBuilder().create();

        Type listOfGames = new TypeToken<ArrayList<PocketGame>>(){}.getType();

        List<PocketGame> games = gson.fromJson(jsonString, listOfGames);
        return games;
    }

    private List<PocketCore> loadCores()
    {
        String filepath = context.dataDirectory() + POCKET_DIR + CORES_JSON_FILE;
        String jsonString = readFileContents(filepath);

        Gson gson = new GsonBuilder().create();

        Type listOfCores = new TypeToken<ArrayList<PocketCore>>(){}.getType();

        List<PocketCore> cores = gson.fromJson(jsonString, listOfCores);

        cores = filterCoresByGames(cores);

        return cores;
    }

    private String readFileContents(String filepath)
    {
        String content = "";
        try
        {
            InputStream in = new FileInputStream(filepath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line = reader.readLine();
            while (line != null)
            {
                content += line;

                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return content;
    }

    private List<PocketCore> filterCoresByGames(List<PocketCore> oldList)
    {
        List<PocketCore> newList = new ArrayList<PocketCore>();

        for(PocketCore core : oldList)
        {
            boolean containsGameForCore = containsGameForCore(core);
            if (containsGameForCore)
            {
                newList.add(core);
            }
        }
        return newList;
    }

    private boolean containsGameForCore(PocketCore core)
    {
        for(PocketGame game : getGames())
        {
            if (StringUtils.equalsIgnoreCase(game.getCore(), core.getNamespace()))
            {
                return true;
            }
        }
        return false;
    }

    public List<String> getGameGenres()
    {
        if (cachedGenres.isEmpty())
        {
            cachedGenres = loadGenres();
        }
        return cachedGenres;
    }

    private List<String> loadGenres()
    {
        List<String> genres = new ArrayList<String>();

        for(PocketGame game : getGames())
        {
            String genre = game.getGenre();

            if (!genre.contains(genre))
            {
                genres.add(genre);
            }
        }

        Collections.sort(genres);

        return genres;
    }
}
