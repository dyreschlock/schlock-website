package com.schlock.website.components.apps.games;

import com.schlock.website.entities.apps.games.DataPanelData;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import java.util.List;

public class DataPanel
{
    @Parameter(required = true)
    @Property
    private String title;

    @Parameter(required = true)
    @Property
    private List<DataPanelData> displayData;

    @Property
    private DataPanelData currentData;

    @Property
    private Integer currentIndex;


    public String getOuterLeftCss()
    {
        String css = "outerLeft ";

        if (currentIndex == displayData.size() - 1)
        {
            css += "outerBottom";
        }
        return css;
    }

    public String getOuterRightCss()
    {
        String css = "outerRight ";

        if (currentIndex == 0)
        {
            css += "outerTop";
        }
        if (currentIndex == displayData.size() - 1)
        {
            css += "outerBottom";
        }
        return css;
    }

    public String getEvenOdd()
    {
        if (currentIndex % 2 == 0)
        {
            return PlatformCount.EVEN;
        }
        return PlatformCount.ODD;
    }
}
