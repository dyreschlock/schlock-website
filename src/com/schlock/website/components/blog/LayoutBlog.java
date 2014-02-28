package com.schlock.website.components.blog;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

@Import(stylesheet = {"context:layout/site.css",
                        "context:layout/layout.css"})
public class LayoutBlog
{
    @Parameter(required = true)
    @Property
    private String title;
}
