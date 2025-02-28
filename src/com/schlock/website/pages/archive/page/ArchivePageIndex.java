package com.schlock.website.pages.archive.page;

import com.schlock.website.entities.apps.pokemon.CounterType;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.pages.apps.pokemon.PokemonRaidCounter;
import com.schlock.website.pages.apps.pokemon.PokemonUnown;
import com.schlock.website.pages.archive.ArchiveIndex;
import com.schlock.website.pages.old.v1.V1Index;
import com.schlock.website.pages.old.v2.V2Index;
import com.schlock.website.pages.old.v3.V3Index;
import com.schlock.website.pages.old.v4.V4Index;
import com.schlock.website.pages.old.v5.V5Index;
import com.schlock.website.pages.old.v6.V6Index;
import com.schlock.website.pages.old.v7.V7Index;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArchivePageIndex
{
    private final String CRYSTANIA_UUID = "the-secret-of-crystania";

    @Inject
    private PostDAO postDAO;

    @Inject
    private DeploymentContext context;

    @Inject
    private ImageManagement imageManagement;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private Messages messages;

    @Property
    private Page currentPage;

    @Property
    private CustomPageDetails currentPageDetails;

    @Property
    private String[] currentDetails;


    public List<Page> getPages()
    {
        return postDAO.getAllPagesNoCourses(true);
    }

    public Page getCrystaniaPage()
    {
        AbstractPost post = postDAO.getByUuid(CRYSTANIA_UUID);
        return (Page) post;
    }

    public List<CustomPageDetails> getCustomPageDetails()
    {
        return Arrays.asList(
                getCollectionPageDetails(),
                getPokemonGoPageDetails(),
                getShinyPokemonPageDetails(),
                getSiteHistoryPageDetails()
        );
    }


    private CustomPageDetails getCollectionPageDetails()
    {
        List<String> titles = Arrays.asList(
                "video-game-collection",
                "modded-console-game-inventory",
                "digital-retro-game-library"
        );

        List<String> links = Arrays.asList(
                linkSource.createPageRenderLink(com.schlock.website.pages.apps.games.Index.class).toURI(),
                linkSource.createPageRenderLink(com.schlock.website.pages.apps.ps2.Index.class).toURI(),
                linkSource.createPageRenderLink(com.schlock.website.pages.apps.pocket.Index.class).toURI()

        );

        List<String[]> pageDetails = collectStrings(titles, links);

        String title = messages.get("collection-pages");

        String previewTitle = messages.get(pageDetails.get(0)[0]);
        String previewLink = pageDetails.get(0)[1];

        AbstractPost post = postDAO.getByUuid("video-game-collection");
        String previewImage = imageManagement.getPostPreviewImageLink(post);

        return new CustomPageDetails(title, pageDetails, previewTitle, previewLink, previewImage);
    }

    private CustomPageDetails getPokemonGoPageDetails()
    {
        List<String> titles = Arrays.asList(
                "pokemon-go-raid-counters",
                "pokemon-go-raid-counters-custom",
                "pokemon-go-unown"
        );

        List<String> links = Arrays.asList(
                linkSource.createPageRenderLink(PokemonRaidCounter.class).toURI(),
                linkSource.createPageRenderLinkWithContext(PokemonRaidCounter.class, CounterType.CUSTOM.toString().toLowerCase()).toURI(),
                linkSource.createPageRenderLink(PokemonUnown.class).toURI()
        );

        List<String[]> pageDetails = collectStrings(titles, links);

        String title = messages.get("pokemon-go-pages");

        String previewTitle = messages.get(pageDetails.get(0)[0]);
        String previewLink = pageDetails.get(0)[1];

        AbstractPost post = postDAO.getByUuid("pokemon-go-raid-counters");
        String previewImage = imageManagement.getPostPreviewImageLink(post);

        return new CustomPageDetails(title, pageDetails, previewTitle, previewLink, previewImage);
    }

    private CustomPageDetails getShinyPokemonPageDetails()
    {
        List<String> titles = Arrays.asList(
                "shiny-pokemon-all",
                "shiny-pokemon-go",
                "shiny-pokemon-lets",
                "shiny-pokemon-legends-list",
                "shiny-pokemon-legends-catches",
                "shiny-pokemon-legends-remaining"
        );

        String domain = context.webDomain();

        List<String> links = Arrays.asList(
                domain + "pokemon/shinydex/",
                domain + "pokemon/shinydex/go",
                domain + "pokemon/shinydex/letsgo",
                domain + "pokemon/shinydex/hisui/dex",
                domain + "pokemon/shinydex/hisui/order",
                domain + "pokemon/shinydex/hisui/missing"
        );

        List<String[]> pageDetails = collectStrings(titles, links);

        String title = messages.get("shiny-pokemon-pages");

        String previewTitle = messages.get(pageDetails.get(0)[0]);
        String previewLink = pageDetails.get(0)[1];

        AbstractPost post = postDAO.getByUuid("shiny-pokemon-inventory");
        String previewImage = imageManagement.getPostPreviewImageLink(post);

        return new CustomPageDetails(title, pageDetails, previewTitle, previewLink, previewImage);
    }

    private CustomPageDetails getSiteHistoryPageDetails()
    {
        List<String> titles = Arrays.asList(
                "site-version-7",
                "site-version-6",
                "site-version-5",
                "site-version-4",
                "site-version-3",
                "site-version-2",
                "site-version-1"
        );

        List<String> links = Arrays.asList(
                linkSource.createPageRenderLink(V7Index.class).toURI(),
                linkSource.createPageRenderLink(V6Index.class).toURI(),
                linkSource.createPageRenderLink(V5Index.class).toURI(),
                linkSource.createPageRenderLink(V4Index.class).toURI(),
                linkSource.createPageRenderLink(V3Index.class).toURI(),
                linkSource.createPageRenderLink(V2Index.class).toURI(),
                linkSource.createPageRenderLink(V1Index.class).toURI()
        );

        List<String[]> pageDetails = collectStrings(titles, links);

        String title = messages.get("old-site-pages");

        return new CustomPageDetails(title, pageDetails);
    }

    private List<String[]> collectStrings(List<String> titles, List<String> links)
    {
        List<String[]> strings = new ArrayList<>();

        for(int i = 0; i < titles.size(); i++)
        {
            String title = titles.get(i);
            String link = links.get(i);

            String[] details = new String[2];
            details[0] = title;
            details[1] = link;

            strings.add(details);
        }
        return strings;
    }



    public String getCurrentTitle()
    {
        String key = currentDetails[0];
        return messages.get(key);
    }

    public String getCurrentLink()
    {
        String link = currentDetails[1];
        return link;
    }




    public String getPageTitle()
    {
        String title = messages.get("title");
        return title;
    }

    public String getPageDescription()
    {
        String text = messages.get("description");
        return text;
    }

    public String getPageUuid()
    {
        return Page.PAGE_ARCHIVE_UUID;
    }

    public String getPageUrl()
    {
        String link = linkSource.createPageRenderLink(ArchiveIndex.class).toURI();
        return link;
    }


    public class CustomPageDetails
    {
        private String groupTitle;
        private List<String[]> pageDetails;

        private String previewPostTitle;
        private String previewPostLink;
        private String previewPostImage;

        public CustomPageDetails(String groupTitle,
                                 List<String[]> pageDetails,
                                 String previewPostTitle,
                                 String previewPostLink,
                                 String previewPostImage)
        {
            this.groupTitle = groupTitle;
            this.pageDetails = pageDetails;
            this.previewPostTitle = previewPostTitle;
            this.previewPostLink = previewPostLink;
            this.previewPostImage = previewPostImage;
        }

        public CustomPageDetails(String groupTitle,
                                 List<String[]> pageDetails)
        {
            this.groupTitle = groupTitle;
            this.pageDetails = pageDetails;
        }

        public boolean isHasPreviewPost()
        {
            return StringUtils.isNotBlank(previewPostTitle);
        }

        public String getGroupTitle()
        {
            return groupTitle;
        }

        public List<String[]> getPageDetails()
        {
            return pageDetails;
        }

        public String getPreviewPostTitle()
        {
            return previewPostTitle;
        }

        public String getPreviewPostLink()
        {
            return previewPostLink;
        }

        public String getPreviewPostImage()
        {
            return previewPostImage;
        }
    }
}
