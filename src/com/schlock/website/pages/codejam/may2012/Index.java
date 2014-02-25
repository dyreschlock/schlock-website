package com.schlock.website.pages.codejam.may2012;

import com.schlock.website.entities.codejam.may2012.DayOption;
import com.schlock.website.entities.codejam.may2012.DecisionController;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;

@Import(stylesheet = "context:layout/codejam/codejam.css")
public class Index
{
    @Property
    private DayOption currentDay;

    @InjectComponent
    private Zone pageZone;
    
    @SessionState
    private DecisionController controller;
    
    
    public DayOption[] getDays()
    {
        return DayOption.values();
    }
    
    Object onReset()
    {
        controller.reset();

        return pageZone;
    }

    public Zone getPageZone()
    {
        return pageZone;
    }
}
