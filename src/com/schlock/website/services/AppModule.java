package com.schlock.website.services;

import com.schlock.website.services.blog.BlogModule;
import com.schlock.website.services.codejam.may2012.May2012Module;
import com.schlock.website.services.database.DatabaseModule;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.SubModule;

@SubModule({
        BlogModule.class,
        DatabaseModule.class,
        May2012Module.class
})
public class AppModule
{
    public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration)
    {
        configuration.add(SymbolConstants.PRODUCTION_MODE, false);
    }
}
