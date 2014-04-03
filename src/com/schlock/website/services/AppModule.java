package com.schlock.website.services;

import com.schlock.website.services.blog.BlogModule;
import com.schlock.website.services.codejam.may2012.May2012Module;
import com.schlock.website.services.database.DatabaseModule;
import com.schlock.website.services.impl.DeploymentContextImpl;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.hibernate.HibernateSymbols;
import org.apache.tapestry5.internal.services.RequestImpl;
import org.apache.tapestry5.internal.services.ResponseImpl;
import org.apache.tapestry5.internal.services.TapestrySessionFactory;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SubModule({
        BlogModule.class,
        DatabaseModule.class,
        May2012Module.class
})
public class AppModule
{
    public static void bind(ServiceBinder binder)
    {
        binder.bind(DeploymentContext.class, DeploymentContextImpl.class);
    }

    @Contribute(SymbolProvider.class)
    @ApplicationDefaults
    public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration)
    {
        configuration.add(SymbolConstants.PRODUCTION_MODE, true);
        configuration.add(SymbolConstants.MINIFICATION_ENABLED, true);

        configuration.add(HibernateSymbols.DEFAULT_CONFIGURATION, false);
    }

    public static void contributeIgnoredPathsFilter(Configuration<String> configuration)
    {
        configuration.add("/layout/blog.css");

        configuration.add("/photo/.*");
        configuration.add("/image/.*");

        configuration.add("/misc/.*");
        configuration.add("/pages/.*");
        configuration.add("/spamm/.*");

        configuration.add("/cpanel/.*");
    }

    /**
     * Adding the RequestGlobal into the servlet request so T5 services can be used in a regular 'ol servlet
     */
    public void contributeHttpServletRequestHandler(OrderedConfiguration<HttpServletRequestFilter> configuration,
                                                    @Inject @Symbol(SymbolConstants.CHARSET) final String applicationCharset,
                                                    @Inject final TapestrySessionFactory sessionFactory,
                                                    final RequestGlobals requestGlobals)
    {
        HttpServletRequestFilter storeRequestResponse = new HttpServletRequestFilter() {

            public boolean service(HttpServletRequest servletRequest,
                                   HttpServletResponse servletResponse, HttpServletRequestHandler handler) throws IOException
            {
                Request request = new RequestImpl(servletRequest, applicationCharset, sessionFactory);
                Response response = new ResponseImpl(servletRequest, servletResponse);
                requestGlobals.storeRequestResponse(request, response);
                return handler.service(servletRequest, servletResponse);
            }
        };

        configuration.add("StoreRequestResponse", storeRequestResponse, "before:*");
    }
}
