package com.schlock.website.components.apps.notfibbage.content.player;

import com.schlock.website.pages.apps.notfibbage.Player;
import com.schlock.website.services.apps.notfibbage.NotFibbageManagement;
import com.schlock.website.services.blog.PostManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import javax.xml.bind.ValidationException;

public class PlayerRegisterPhase
{
    @Inject
    private PostManagement postManagement;

    @Inject
    private NotFibbageManagement management;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private Messages messages;

    @Parameter(required = true)
    @Property
    private String playerName;

    @Property
    @Persist
    private String newPlayerName;

    public String getTitleHtml()
    {
        String title = messages.get("title");
        String html = postManagement.wrapJapaneseTextInTags(title);

        return html;
    }


    public boolean isPlayerRegistered()
    {
        return StringUtils.isNotBlank(playerName);
    }


    void onValidateFromPlayerForm() throws ValidationException
    {

    }

    Object onSuccessFromPlayerForm()
    {
        String playerName = newPlayerName;

        management.registerPlayer(playerName);

        newPlayerName = null;

        return linkSource.createPageRenderLinkWithContext(Player.class, playerName);
    }
}
