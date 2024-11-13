package com.schlock.website.pages.regeneration;

import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pokemon.PokemonDataGameMasterService;
import com.schlock.website.services.apps.ps2.PlaystationService;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.blog.SitemapManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class RegenerationIndex
{
    @Inject
    private DeploymentContext deploymentContext;

    @Inject
    private SitemapManagement sitemapManagement;

    @Inject
    private ImageManagement imageManagement;

    @Inject
    private PostManagement postManagement;

    @Inject
    private PokemonDataGameMasterService pokemonGameMasterService;

    @Inject
    private PlaystationService playstationService;

    public boolean isLocal()
    {
        return deploymentContext.isLocal();
    }

    /*
     * No slash at the beginning. Slash at the end.
     */
    private static final String LOCATION = "photo/241114_ghibli_warehouse/";


    @CommitAfter
    void onProcessImageDirectory()
    {
        onSanitizeImages();
        onGenerateThumbnails();
    }

    @CommitAfter
    void onSanitizeImages()
    {
        if (StringUtils.isNotBlank(LOCATION))
        {
            String imageLocation = deploymentContext.webDirectory() + LOCATION;
            try
            {
                imageManagement.removeInvalidCharactersFromImageFilenames(imageLocation);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    @CommitAfter
    void onGenerateThumbnails()
    {
        if (StringUtils.isNotBlank(LOCATION))
        {
            String imageLocation = deploymentContext.webDirectory() + LOCATION;
            try
            {
                imageManagement.createThumbnailsForDirectory(imageLocation);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    @CommitAfter
    void onRegenPostItems()
    {
        onRegeneratePostNumbers();
        onCreatePostPreviewImages();
        onGenerateImageObjects();
        onRegenHTML();
        onGenerateWebpFiles();
    }

    @CommitAfter
    void onRegeneratePostNumbers()
    {
        postManagement.regeneratePostNumbers();
    }

    @CommitAfter
    void onCreatePostPreviewImages()
    {
        imageManagement.createPostPreviewImages();
    }

    @CommitAfter
    void onGenerateImageObjects()
    {
        imageManagement.generateImageObjects();
    }

    @CommitAfter
    void onRegenHTML()
    {
        postManagement.regenerateAllPostHTML();
    }

    @CommitAfter
    void onGenerateWebpFiles()
    {
        try
        {
            imageManagement.generateWebpFilesFromImages();
        }
        catch (Exception e)
        {
            e.printStackTrace();
//            throw new RuntimeException(e);
        }
    }

    void onReportPokemonGameMaster()
    {
        List<String> messages = pokemonGameMasterService.reportDifferences();
        outputMessages(messages);
    }

    @CommitAfter
    void onUpdateMovesAddNew()
    {
        List<String> messages = pokemonGameMasterService.updateMovesAddNew();
        outputMessages(messages);
    }

    @CommitAfter
    void onUpdateMovesMainStats()
    {
        List<String> messages = pokemonGameMasterService.updateMovesMainStats();
        outputMessages(messages);
    }

    @CommitAfter
    void onUpdateMovesPvpStats()
    {
        List<String> messages = pokemonGameMasterService.updateMovesPvpStats();
        outputMessages(messages);
    }

    @CommitAfter
    void onUpdatePokemonAddNew()
    {
        List<String> messages = pokemonGameMasterService.updatePokemonAddNew();
        outputMessages(messages);
    }

    @CommitAfter
    void onUpdatePokemonStats()
    {
        List<String> messages = pokemonGameMasterService.updatePokemonStats();
        outputMessages(messages);
    }

    @CommitAfter
    void onUpdatePokemonMoves()
    {
        List<String> messages = pokemonGameMasterService.updatePokemonMoves();
        outputMessages(messages);
    }

    @CommitAfter
    void onUpdatePokemonCategories()
    {
        pokemonGameMasterService.updatePokemonCategories();
    }

    private void outputMessages(List<String> messages)
    {
        if (messages.isEmpty())
        {
            System.out.println("There are no differences/changes.");
        }

        for(String message : messages)
        {
            System.out.println(message);
        }
    }


    @CommitAfter
    void onUpdateGameInventory()
    {
        playstationService.updateGameInventory();
    }

    @CommitAfter
    void onUpdateGameSaveFiles()
    {
        playstationService.updateGameSaveFiles();
    }

    @CommitAfter
    void onReadConfigFiles()
    {
        try
        {
            playstationService.readConfigFiles();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    void onWriteConfigFiles()
    {
        try
        {
            playstationService.writeConfigFiles();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @CommitAfter
    void onWriteArtFiles()
    {
        try
        {
            playstationService.writeArtFilesToLocal();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    void onCopyLocalFilesToDrive()
    {
        try
        {
            playstationService.copyLocalFilesToDrive();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }




    @Property
    private Object[] currentData;

    public String getCurrentDataName()
    {
        return (String) currentData[0];
    }

    public String getCurrentDataCount()
    {
        Integer count = (Integer) currentData[1];
        return Integer.toString(count);
    }

    public String getCurrentDataReleases()
    {
        Integer releases = (Integer) currentData[2];
        return Integer.toString(releases);
    }

    public String getCurrentDataAverage()
    {
        Integer count = (Integer) currentData[1];
        Integer releases = (Integer) currentData[2];

        double average = count.doubleValue() / releases.doubleValue();

        return String.format("%.2f", average);
    }

    private List<Object[]> downloadData;

    public List<Object[]> getDownloadData()
    {
        if (downloadData == null)
        {
            Object[] images = getDownloadsFromRepo("pocket-platform-images");
            Object[] extras = getDownloadsFromRepo("pocket-extras");

            downloadData = Arrays.asList(images, extras);
        }
        return downloadData;
    }

    private Object[] getDownloadsFromRepo(String repoName)
    {
        int downloads = 0;
        int releases = 0;

        final String API_URL = "https://api.github.com/repos/dyreschlock/%s/releases?per_page=100";

        String apiCall = String.format(API_URL, repoName);
        JSONArray contents = readJSONArrayFromUrl(apiCall);

        Iterator releaseIter = contents.iterator();
        while(releaseIter.hasNext())
        {
            JSONObject release = (JSONObject) releaseIter.next();
            JSONArray assets = release.getJSONArray("assets");

            Iterator assetIter = assets.iterator();
            while(assetIter.hasNext())
            {
                JSONObject asset = (JSONObject) assetIter.next();

                int count = asset.getInt("download_count");

                downloads += count;
            }

            String releaseTag = release.getString("tag_name");
            if (StringUtils.endsWith(releaseTag, ".0"))
            {
                releases++;
            }
        }

        Object[] output = new Object[3];
        output[0] = repoName;
        output[1] = downloads;
        output[2] = releases;

        return output;
    }

    private JSONArray readJSONArrayFromUrl(String url)
    {
        try
        {
            URL website = new URL(url);
            URLConnection connection = website.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder response = new StringBuilder();

            String inputLine;
            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);
            }
            in.close();

            return new JSONArray(response.toString());
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }


    void onPostToIndexNow()
    {
        final String DOMAIN = deploymentContext.webDomain();

        JSONArray urls = new JSONArray();

        urls.put(DOMAIN);
        for(String url : sitemapManagement.getAllUrlsToIndex())
        {
            urls.put(DOMAIN + url);
        }

        if (urls.length() > 0)
        {
//            postToIndexNow(urls);
        }
    }

    private void postToIndexNow(JSONArray urlList)
    {
        String apikey = deploymentContext.indexnowApiKey();

        JSONObject contents = new JSONObject();
        contents.put("host", deploymentContext.webDomain());
        contents.put("key", apikey);
        contents.put("keyLocation", deploymentContext.webDomain() + apikey + ".txt");
        contents.put("urlList", urlList);

        byte[] output = contents.toString().getBytes(StandardCharsets.UTF_8);
        int length = output.length;

        try
        {
            HttpURLConnection http = (HttpURLConnection) new URL("https://bing.com/indexnow").openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream())
            {
                os.write(output);
            }


            StringBuilder resp = new StringBuilder();
            Reader in = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));

            int c = in.read();
            while(c >= 0)
            {
                resp.append((char) c);

                c = in.read();
            }
            System.out.println("Response from Post: " + resp.toString());
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
