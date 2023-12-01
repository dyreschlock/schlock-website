package com.schlock.website.services.apps.pocket.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.schlock.website.entities.apps.games.DataPanelData;
import com.schlock.website.entities.apps.pocket.PocketCore;
import com.schlock.website.entities.apps.pocket.PocketGame;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pocket.PocketDataService;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class PocketDataServiceImpl implements PocketDataService
{
    private final static String POCKET_DIR = "pocket-utils/";

    private final static String GAMES_JSON_FILE = "games.json";
    private final static String CORES_JSON_FILE = "cores.json";

    private final DeploymentContext context;

    private List<PocketGame> cachedGames = new ArrayList<PocketGame>();
    private Map<String, PocketCore> cachedCores = new HashMap<String, PocketCore>();

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
        if (core == null)
        {
            return getGames();
        }

        List<PocketGame> games = new ArrayList<PocketGame>();

        if (core.isFakeArcadeCore())
        {
            for(PocketCore arcadeCore : getCoresByCategory(PocketCore.CAT_ARCADE_1, PocketCore.CAT_ARCADE_2))
            {
                List<PocketGame> arcadeGames = getGamesByCore(arcadeCore);
                games.addAll(arcadeGames);
            }
            return games;
        }

        for(PocketGame game : getGames())
        {
            if (StringUtils.equalsIgnoreCase(core.getNamespace(), game.getCore()))
            {
                games.add(game);
            }
        }
        return games;
    }

    public List<PocketGame> getGamesByGenre(String genreId)
    {
        if (genreId == null)
        {
            return getGames();
        }

        List<PocketGame> games = new ArrayList<PocketGame>();

        for(PocketGame game : getGames())
        {
            if (StringUtils.equalsIgnoreCase(genreId, game.getGenreId()))
            {
                games.add(game);
            }
        }
        return games;
    }

    public List<PocketGame> getGamesByCoreGenre(PocketCore core, String genreId)
    {
        if (genreId == null)
        {
            return getGamesByCore(core);
        }
        if (core == null)
        {
            return getGamesByGenre(genreId);
        }

        List<PocketGame> games = new ArrayList<PocketGame>();

        if (core.isFakeArcadeCore())
        {
            for(PocketCore arcadeCore : getCoresByCategory(PocketCore.CAT_ARCADE_1, PocketCore.CAT_ARCADE_2))
            {
                List<PocketGame> arcadeGames = getGamesByCoreGenre(arcadeCore, genreId);
                games.addAll(arcadeGames);
            }
            return games;
        }

        for(PocketGame game : getGames())
        {
            if (StringUtils.equalsIgnoreCase(core.getNamespace(), game.getCore()) &&
                    StringUtils.equalsIgnoreCase(genreId, game.getGenreId()))
            {
                games.add(game);
            }
        }
        return games;
    }

    private List<PocketGame> getGamesByCoreOrGenre(PocketCore core, String genreId)
    {
        if (core != null)
        {
            return getGamesByCore(core);
        }
        return getGamesByGenre(genreId);
    }

    public List<PocketCore> getCores()
    {
        if (cachedCores.isEmpty())
        {
            loadCoreMap();
        }

        List<PocketCore> cores = new ArrayList<PocketCore>();
        cores.addAll(cachedCores.values());

        return cores;
    }

    public List<PocketCore> getCoresByCategory(String category)
    {
        return getCoresByCategory(category, "");
    }

    public List<PocketCore> getCoresByCategory(String cat1, String cat2)
    {
        List<PocketCore> cores = new ArrayList<PocketCore>();

        for(PocketCore core : getCores())
        {
            if (StringUtils.equalsIgnoreCase(cat1, core.getCategory()) ||
                StringUtils.equalsIgnoreCase(cat2, core.getCategory()))
            {
                cores.add(core);
            }
        }
        return cores;
    }

    public PocketCore getCoreByNamespace(String namespace)
    {
        loadCoreMap();
        return cachedCores.get(namespace);
    }

    public List<DataPanelData> getCountByMostCommonDeveloper(PocketCore core, String genre, Integer maxResults)
    {
        List<PocketGame> games = getGamesByCoreOrGenre(core, genre);
        return getCountByMostCommonDeveloper(games, maxResults);
    }

    private List<DataPanelData> getCountByMostCommonDeveloper(List<PocketGame> games, Integer maxResults)
    {
        Map<String, Integer> developerInfo = new HashMap<String, Integer>();

        for(PocketGame game : games)
        {
            if (StringUtils.isNotBlank(game.getDeveloper()))
            {
                String dev = game.getDeveloper();
                Integer count = developerInfo.get(dev);

                if (count == null)
                {
                    count = 1;
                }
                else
                {
                    count++;
                }

                developerInfo.put(dev, count);
            }
        }
        return createDataPanelData(developerInfo, maxResults);
    }

    public List<DataPanelData> getCountByMostCommonPublisher(PocketCore core, String genre, Integer maxResults)
    {
        List<PocketGame> games = getGamesByCoreOrGenre(core, genre);
        return getCountByMostCommonPublisher(games, maxResults);
    }

    private List<DataPanelData> getCountByMostCommonPublisher(List<PocketGame> games, Integer maxResults)
    {
        Map<String, Integer> publisherInfo = new HashMap<String, Integer>();

        for(PocketGame game : games)
        {
            if (StringUtils.isNotBlank(game.getPublisher()))
            {
                String dev = game.getPublisher();
                Integer count = publisherInfo.get(dev);

                if (count == null)
                {
                    count = 1;
                }
                else
                {
                    count++;
                }

                publisherInfo.put(dev, count);
            }
        }
        return createDataPanelData(publisherInfo, maxResults);
    }

    public List<DataPanelData> getCountByMostCommonYear(PocketCore core, String genre, Integer maxResults)
    {
        List<PocketGame> games = getGamesByCoreOrGenre(core, genre);
        return getCountByMostCommonYear(games, maxResults);
    }

    private List<DataPanelData> getCountByMostCommonYear(List<PocketGame> games, Integer maxResults)
    {
        Map<String, Integer> yearInfo = new HashMap<String, Integer>();

        for(PocketGame game : games)
        {
            if (StringUtils.isNotBlank(game.getYear()))
            {
                String dev = game.getYear();
                Integer count = yearInfo.get(dev);

                if (count == null)
                {
                    count = 1;
                }
                else
                {
                    count++;
                }

                yearInfo.put(dev, count);
            }
        }
        return createDataPanelData(yearInfo, maxResults);
    }


    private List<DataPanelData> createDataPanelData(Map<String, Integer> data, Integer maxResults)
    {
        List<DataPanelData> panelData = new ArrayList<DataPanelData>();
        for(String dev : data.keySet())
        {
            Integer count = data.get(dev);

            panelData.add(new DataPanelData(dev, count.toString()));
        }

        Collections.sort(panelData, new Comparator<DataPanelData>()
        {
            public int compare(DataPanelData o1, DataPanelData o2)
            {
                Integer count1 = Integer.parseInt(o1.getCount());
                Integer count2 = Integer.parseInt(o2.getCount());

                return count2.compareTo(count1);
            }
        });

        List<DataPanelData> results = new ArrayList<DataPanelData>();
        for(DataPanelData panel : panelData)
        {
            results.add(panel);
            if (results.size() == maxResults)
            {
                return results;
            }
        }
        return results;
    }


    private List<PocketGame> loadGames()
    {
        String filepath = context.dataDirectory() + POCKET_DIR + GAMES_JSON_FILE;
        String jsonString = readFileContents(filepath);

        Gson gson = new GsonBuilder().create();

        Type listOfGames = new TypeToken<ArrayList<PocketGame>>(){}.getType();

        List<PocketGame> games = gson.fromJson(jsonString, listOfGames);

        Collections.sort(games, new Comparator<PocketGame>()
        {
            public int compare(PocketGame o1, PocketGame o2)
            {
                return o1.getGameName().compareTo(o2.getGameName());
            }
        });
        return games;
    }

    private void loadCoreMap()
    {
        List<PocketCore> coreList = loadCores();

        cachedCores = new HashMap<String, PocketCore>();
        for(PocketCore core : coreList)
        {
            cachedCores.put(core.getNamespace(), core);
        }
    }


    private List<PocketCore> loadCores()
    {
        String filepath = context.dataDirectory() + POCKET_DIR + CORES_JSON_FILE;
        String jsonString = readFileContents(filepath);

        Gson gson = new GsonBuilder().create();

        Type listOfCores = new TypeToken<ArrayList<PocketCore>>(){}.getType();

        List<PocketCore> cores = gson.fromJson(jsonString, listOfCores);

        cores = filterCoresByGames(cores);
        cores.add(PocketCore.createFakeArcadeCore());

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
            String genre = game.getGenreId();

            if (!genres.contains(genre))
            {
                genres.add(genre);
            }
        }

        Collections.sort(genres);

        return genres;
    }
}
