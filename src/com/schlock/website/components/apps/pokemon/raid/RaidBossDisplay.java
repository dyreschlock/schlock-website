package com.schlock.website.components.apps.pokemon.raid;

import com.schlock.website.entities.apps.pokemon.RaidBossPokemon;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class RaidBossDisplay
{
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

}
