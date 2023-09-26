package com.schlock.website.components.apps.pokemon.raid;

import com.schlock.website.entities.apps.pokemon.RaidCounterInstance;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class RaidCounterDisplay
{
    @Inject
    private Messages messages;

    @Parameter(required = true)
    @Property
    private RaidCounterInstance pokemon;

    @Parameter
    @Property
    private Integer number;

    @Parameter
    @Property
    private Boolean displayCount = false;

    @Parameter
    @Property
    private Boolean displayDpsDetails = true;

    @Parameter
    @Property
    private String extraClass;

    public boolean isHasNumber()
    {
        return number != null;
    }

    public String getShadowClass()
    {
        if (pokemon.isShadow())
        {
            return "shadow";
        }
        return "notShadow";
    }

    public String getCategories()
    {
        StringBuilder sb = new StringBuilder();

        for(String catId : pokemon.getCategoryIds())
        {
            if (sb.length() > 0)
            {
                sb.append("・");
            }
            sb.append(messages.get(catId));
        }
        return sb.toString();
    }
}
