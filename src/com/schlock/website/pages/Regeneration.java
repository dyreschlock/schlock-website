package com.schlock.website.pages;

import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pokemon.PokemonDataGameMasterService;
import com.schlock.website.services.apps.ps2.PlaystationService;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.PostManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.io.IOException;
import java.util.List;

public class Regeneration
{
    @Inject
    private DeploymentContext deploymentContext;

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
    private static final String LOCATION = "";


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
}
