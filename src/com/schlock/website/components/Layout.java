package com.schlock.website.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class Layout
{
    @Parameter(required = true)
    @Property
    private String title;
}
