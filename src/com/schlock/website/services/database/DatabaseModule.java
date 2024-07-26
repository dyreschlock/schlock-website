package com.schlock.website.services.database;

import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.database.apps.bingo.BingoOptionDAO;
import com.schlock.website.services.database.apps.bingo.impl.BingoOptionDAOImpl;
import com.schlock.website.services.database.apps.games.VideoGameDAO;
import com.schlock.website.services.database.apps.games.VideoGameHardwareDAO;
import com.schlock.website.services.database.apps.games.VideoGamePlatformDAO;
import com.schlock.website.services.database.apps.games.impl.VideoGameDAOImpl;
import com.schlock.website.services.database.apps.games.impl.VideoGameHardwareDAOImpl;
import com.schlock.website.services.database.apps.games.impl.VideoGamePlatformDAOImpl;
import com.schlock.website.services.database.apps.japanese.KanjiDAO;
import com.schlock.website.services.database.apps.japanese.impl.KanjiDAOImpl;
import com.schlock.website.services.database.apps.kendo.ExamQuestionDAO;
import com.schlock.website.services.database.apps.kendo.impl.ExamQuestionDAOImpl;
import com.schlock.website.services.database.apps.notfibbage.NotFibbageQuestionDAO;
import com.schlock.website.services.database.apps.notfibbage.impl.NotFibbageQuestionDAOImpl;
import com.schlock.website.services.database.apps.pokemon.PokemonCategoryDAO;
import com.schlock.website.services.database.apps.pokemon.PokemonCustomCounterDAO;
import com.schlock.website.services.database.apps.pokemon.PokemonDataDAO;
import com.schlock.website.services.database.apps.pokemon.PokemonMoveDAO;
import com.schlock.website.services.database.apps.pokemon.impl.PokemonCategoryDAOImpl;
import com.schlock.website.services.database.apps.pokemon.impl.PokemonCustomCounterDAOImpl;
import com.schlock.website.services.database.apps.pokemon.impl.PokemonDataDAOImpl;
import com.schlock.website.services.database.apps.pokemon.impl.PokemonMoveDAOImpl;
import com.schlock.website.services.database.apps.ps2.PlaystationGameDAO;
import com.schlock.website.services.database.apps.ps2.impl.PlaystationGameDAOImpl;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.ImageDAO;
import com.schlock.website.services.database.blog.KeywordDAO;
import com.schlock.website.services.database.blog.PostDAO;
import com.schlock.website.services.database.blog.impl.CategoryDAOImpl;
import com.schlock.website.services.database.blog.impl.ImageDAOImpl;
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
        binder.bind(ImageDAO.class, ImageDAOImpl.class);
        binder.bind(CategoryDAO.class, CategoryDAOImpl.class);
        binder.bind(KeywordDAO.class, KeywordDAOImpl.class);

        binder.bind(BingoOptionDAO.class, BingoOptionDAOImpl.class);

        binder.bind(KanjiDAO.class, KanjiDAOImpl.class);

        binder.bind(ExamQuestionDAO.class, ExamQuestionDAOImpl.class);

        binder.bind(NotFibbageQuestionDAO.class, NotFibbageQuestionDAOImpl.class);

        binder.bind(VideoGameDAO.class, VideoGameDAOImpl.class);
        binder.bind(VideoGameHardwareDAO.class, VideoGameHardwareDAOImpl.class);
        binder.bind(VideoGamePlatformDAO.class, VideoGamePlatformDAOImpl.class);

        binder.bind(PokemonDataDAO.class, PokemonDataDAOImpl.class);
        binder.bind(PokemonMoveDAO.class, PokemonMoveDAOImpl.class);
        binder.bind(PokemonCategoryDAO.class, PokemonCategoryDAOImpl.class);
        binder.bind(PokemonCustomCounterDAO.class, PokemonCustomCounterDAOImpl.class);

        binder.bind(PlaystationGameDAO.class, PlaystationGameDAOImpl.class);
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
