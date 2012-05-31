package com.schlock.website.pages.codejam.may2012;

import com.schlock.website.codejam.may2012.model.DayOption;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;

@Import(stylesheet = "context:layout/codejam/codejam.css")
public class Index
{
    @Property
    private DayOption currentDay;

    @InjectComponent
    private Zone pageZone;
    
    
    public DayOption[] getDays()
    {
        return DayOption.values();
    }

    public Zone getPageZone()
    {
        return pageZone;
    }
}
