package com.schlock.website.services.apps.pocket.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import com.schlock.website.entities.apps.games.DataPanelData;
import com.schlock.website.entities.apps.pocket.Device;
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
    public final static int MOST_COMMON_MAX_RESULTS = 15;

    private final static String POCKET_DIR = "pocket-utils/";

    private final static String GAMES_JSON_FILE = "games.json";
    private final static String CORES_JSON_FILE = "cores.json";

    private final DeploymentContext context;

    private List<PocketGame> cachedGames = new ArrayList<PocketGame>();
    private Map<String, PocketCore> cachedCores = new HashMap<String, PocketCore>();

    private List<String> cachedGenres = new ArrayList<String>();

    private Map<String, List<PocketGame>> cachedSearches = new HashMap<>();

    private Map<String, List<DataPanelData>> cachedMostCommonDeveloper = new HashMap<>();
    private Map<String, List<DataPanelData>> cachedMostCommonPublisher = new HashMap<>();
    private Map<String, List<DataPanelData>> cachedMostCommonYear = new HashMap<>();


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

    private String createGameSearchCacheKey(Device device, PocketCore core, String genre)
    {
        String key = "";
        key += (device != null) ? device.name() : "null";
        key += (core != null) ? core.getPlatformId() : "null";
        key += (genre != null) ? genre : "null";
        return key;
    }

    public List<PocketGame> getGamesByCore(PocketCore core)
    {
        return getGamesByDeviceCoreGenre(null, core, null);
    }

    public List<PocketGame> getGamesByDeviceCoreGenre(Device device, PocketCore core, String genre)
    {
        String key = createGameSearchCacheKey(device, core, genre);
        if (cachedSearches.containsKey(key))
        {
            return cachedSearches.get(key);
        }

        List<PocketGame> games = new ArrayList<PocketGame>();
        for(PocketGame game : getGames())
        {
            boolean device_ok = device == null ||
                                game.getDevices().contains(device);

            boolean genre_ok = StringUtils.isBlank(genre) ||
                                StringUtils.equalsIgnoreCase(genre, game.getGenreId());

            boolean core_ok;
            if (core == null)
            {
                core_ok = true;
            }
            else
            {
                if (core.isFakeArcadeCore())
                {
                    String gameCore = game.getCore();
                    String gameCoreCat = null;
                    if (gameCore != null)
                    {
                        gameCoreCat = getCoreByPlatformId(gameCore).getCategory();
                    }

                    core_ok = gameCoreCat == null ||
                                StringUtils.equalsIgnoreCase(PocketCore.CAT_ARCADE_1, gameCoreCat) ||
                                StringUtils.equalsIgnoreCase(PocketCore.CAT_ARCADE_2, gameCoreCat);
                }
                else
                {
                    core_ok = StringUtils.equalsIgnoreCase(core.getPlatformId(), game.getCore());
                }
            }

            if (device_ok && core_ok && genre_ok)
            {
                games.add(game);
            }
        }

        cachedSearches.put(key, games);
        return games;
    }

    private List<PocketGame> getGamesByCoreOrGenre(Device device, PocketCore core, String genreId)
    {
        if (core != null)
        {
            return getGamesByDeviceCoreGenre(device, core, null);
        }
        return getGamesByDeviceCoreGenre(device, null, genreId);
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

    public PocketCore getCoreByPlatformId(String platformId)
    {
        loadCoreMap();
        if (platformId == null)
        {
            return null;
        }
        return cachedCores.get(platformId);
    }

    public List<DataPanelData> getCountByMostCommonDeveloper(Device device, PocketCore core, String genre, Integer maxResults)
    {
        String key = createGameSearchCacheKey(device, core, genre);

        List<DataPanelData> results = cachedMostCommonDeveloper.get(key);
        if (results == null)
        {
            List<PocketGame> games = getGamesByCoreOrGenre(device, core, genre);
            results = getCountByMostCommonDeveloper(games, MOST_COMMON_MAX_RESULTS);

            cachedMostCommonDeveloper.put(key, results);
        }
        return subList(results, maxResults);
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

    public List<DataPanelData> getCountByMostCommonPublisher(Device device, PocketCore core, String genre, Integer maxResults)
    {
        String key = createGameSearchCacheKey(device, core, genre);

        List<DataPanelData> results = cachedMostCommonPublisher.get(key);
        if (results == null)
        {
            List<PocketGame> games = getGamesByCoreOrGenre(device, core, genre);
            results = getCountByMostCommonPublisher(games, MOST_COMMON_MAX_RESULTS);

            cachedMostCommonPublisher.put(key, results);
        }
        return subList(results, maxResults);
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

    public List<DataPanelData> getCountByMostCommonYear(Device device, PocketCore core, String genre, Integer maxResults)
    {
        String key = createGameSearchCacheKey(device, core, genre);

        List<DataPanelData> results = cachedMostCommonYear.get(key);
        if (results == null)
        {
            List<PocketGame> games = getGamesByCoreOrGenre(device, core, genre);
            results = getCountByMostCommonYear(games, MOST_COMMON_MAX_RESULTS);

            cachedMostCommonYear.put(key, results);
        }
        return subList(results, maxResults);
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

    private List subList(List list, int results)
    {
        if (list.size() < results)
        {
            return list;
        }
        return list.subList(0, results);
    }


    private List<PocketGame> loadGames()
    {
        String filepath = context.dataDirectory() + POCKET_DIR + GAMES_JSON_FILE;
        String jsonString = readFileContents(filepath);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Device.class, new JsonDeserializer<Device>()
                {
                    public Device deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
                    {
                        return Device.value(json.getAsString());
                    }
                })
                .create();

        Type listOfGames = new TypeToken<ArrayList<PocketGame>>(){}.getType();

        List<PocketGame> games = gson.fromJson(jsonString, listOfGames);
        Collections.sort(games, new Comparator<PocketGame>()
        {
            public int compare(PocketGame o1, PocketGame o2)
            {
                return o1.getGameName().compareTo(o2.getGameName());
            }
        });

        games = removeMultiDiscGames(games);
        return games;
    }

    private List<PocketGame> removeMultiDiscGames(List<PocketGame> games)
    {
        final String DISC_PREFIX = " (Disc ";
        final String DISC_1 = " (Disc 1)";

        List<PocketGame> output = new ArrayList<PocketGame>();

        for(PocketGame game : games)
        {
            String name = game.getGameName();
            if (!StringUtils.containsIgnoreCase(name, DISC_PREFIX) || StringUtils.containsIgnoreCase(name, DISC_1))
            {
                name = name.replace(DISC_1, "");
                game.setGameName(name);

                output.add(game);
            }
        }
        return output;
    }

    private void loadCoreMap()
    {
        if (cachedCores.isEmpty())
        {
            List<PocketCore> coreList = loadCores();

            cachedCores = new HashMap<String, PocketCore>();
            for(PocketCore core : coreList)
            {
                cachedCores.put(core.getPlatformId(), core);
            }
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
            if (StringUtils.equalsIgnoreCase(game.getCore(), core.getPlatformId()))
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
