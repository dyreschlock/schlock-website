package com.schlock.website.services.database;

import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.database.apps.bingo.BingoOptionDAO;
import com.schlock.website.services.database.apps.bingo.impl.BingoOptionDAOImpl;
import com.schlock.website.services.database.apps.japanese.KanjiDAO;
import com.schlock.website.services.database.apps.japanese.impl.KanjiDAOImpl;
import com.schlock.website.services.database.apps.kendo.ExamQuestionDAO;
import com.schlock.website.services.database.apps.kendo.impl.ExamQuestionDAOImpl;
import com.schlock.website.services.database.apps.notfibbage.NotFibbageQuestionDAO;
import com.schlock.website.services.database.apps.notfibbage.impl.NotFibbageQuestionDAOImpl;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.KeywordDAO;
import com.schlock.website.services.database.blog.PostDAO;
import com.schlock.website.services.database.blog.impl.CategoryDAOImpl;
import com.schlock.website.services.database.blog.impl.KeywordDAOImpl;
import com.schlock.website.services.database.blog.impl.PostDAOImpl;
import org.apache.tapestry5.hibernate.HibernateConfigurer;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Inject;

public class DatabaseModule
{
    public static void bind(ServiceBinder binder)
    {
        binder.bind(PostDAO.class, PostDAOImpl.class);
        binder.bind(CategoryDAO.class, CategoryDAOImpl.class);
        binder.bind(KeywordDAO.class, KeywordDAOImpl.class);

        binder.bind(BingoOptionDAO.class, BingoOptionDAOImpl.class);

        binder.bind(KanjiDAO.class, KanjiDAOImpl.class);

        binder.bind(ExamQuestionDAO.class, ExamQuestionDAOImpl.class);

        binder.bind(NotFibbageQuestionDAO.class, NotFibbageQuestionDAOImpl.class);
    }


    private final static String HIBERNATE_USERNAME = "hibernate.connection.username";
    private final static String HIBERNATE_PASSWORD = "hibernate.connection.password";
    private final static String HIBERNATE_URL = "hibernate.connection.url";

    public void contributeHibernateSessionSource(OrderedConfiguration<HibernateConfigurer> configuration,
                                                 @Inject DeploymentContext context)
    {
        final String username = context.getHibernateProperty(HIBERNATE_USERNAME);
        final String password = context.getHibernateProperty(HIBERNATE_PASSWORD);
        final String url = context.getHibernateProperty(HIBERNATE_URL) + "?characterEncoding=utf-8";

        HibernateConfigurer configurer = new HibernateConfigurer() {

            public void configure(org.hibernate.cfg.Configuration configuration)
            {
                configuration.configure("hibernate.cfg.xml");

                configuration.setProperty(HIBERNATE_URL, url);
                configuration.setProperty(HIBERNATE_USERNAME, username);
                configuration.setProperty(HIBERNATE_PASSWORD, password);
            }
        };

        configuration.add("hibernate-session-source", configurer);
    }
}
