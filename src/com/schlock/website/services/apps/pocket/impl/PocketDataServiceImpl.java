package com.schlock.website.services.apps.pocket.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.schlock.website.entities.apps.pocket.PocketCore;
import com.schlock.website.entities.apps.pocket.PocketGame;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pocket.PocketDataService;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PocketDataServiceImpl implements PocketDataService
{
    private final static String POCKET_DIR = "pocket-utils/";

    private final static String GAMES_JSON_FILE = "games.json";
    private final static String CORES_JSON_FILE = "cores.json";

    private final DeploymentContext context;

    private List<PocketGame> cachedGames = new ArrayList<PocketGame>();

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

    public List<PocketCore> getCores()
    {
        return null;
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

}
