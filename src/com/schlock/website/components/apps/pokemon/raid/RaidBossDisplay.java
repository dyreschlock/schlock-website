package com.schlock.website.components.apps.pokemon.raid;

import com.schlock.website.entities.apps.pokemon.PokemonCategory;
import com.schlock.website.entities.apps.pokemon.RaidBossPokemon;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class RaidBossDisplay
{
    @Inject
    private Messages messages;

    @Parameter(required = true)
    @Property
    private RaidBossPokemon raidBoss;

    public String getRaidBossTypesHTML()
    {
        final String SPAN = "<span class=\"%s\">%s</span>";

        String type1 = raidBoss.getType1();

        String html = String.format(SPAN, type1.toLowerCase(), type1);

        String type2 = raidBoss.getType2();
        if (StringUtils.isNotEmpty(type2))
        {
            html += "&nbsp;";
            html += String.format(SPAN, type2.toLowerCase(), type2);
        }

        return html;
    }

    public String getCategories()
    {
        StringBuilder sb = new StringBuilder();

        for(PokemonCategory cat : raidBoss.getCategories())
        {
            if (sb.length() > 0)
            {
                sb.append("・");
            }
            sb.append(messages.get(cat.getNameId()));
        }
        return sb.toString();
    }
}
