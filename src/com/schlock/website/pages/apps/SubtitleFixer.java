package com.schlock.website.pages.apps;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.services.apps.subtitles.SubtitleFixerService;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class SubtitleFixer
{
    @Inject
    private Messages messages;

    @Inject
    private SubtitleFixerService fixer;

    @Inject
    private PostDAO postDAO;


    @Property
    private String inputText;

    @Property
    private String offsetTime;

    @Property
    private String outputText;


    @InjectComponent
    private Zone outputZone;



    void onValidateFromInputForm()
    {

    }


    Object onSuccessFromInputForm()
    {
        int time = Integer.parseInt(offsetTime);

        outputText = fixer.offsetSubtitles(inputText, time);

        return outputZone;
    }


    private AbstractPost cachedPage;

    public AbstractPost getPage()
    {
        if(cachedPage == null)
        {
            cachedPage = postDAO.getByUuid(AbstractPost.SUBTITLES_UUID);
        }
        return cachedPage;
    }

    public String getPageTitle()
    {
        return messages.get("page-title");
    }
}
