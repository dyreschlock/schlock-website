package com.schlock.website.services;

import com.schlock.website.services.codejam.May2012Module;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.SubModule;

@SubModule({
        May2012Module.class
})
public class AppModule
{
    public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration)
    {
        configuration.add(SymbolConstants.PRODUCTION_MODE, false);
    }
}
