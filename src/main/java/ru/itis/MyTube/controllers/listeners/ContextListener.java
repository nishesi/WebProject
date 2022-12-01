package ru.itis.MyTube.controllers.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.auxiliary.constants.Attributes;
import ru.itis.MyTube.auxiliary.constants.Beans;
import ru.itis.MyTube.auxiliary.validators.*;
import ru.itis.MyTube.model.MVUpdater;
import ru.itis.MyTube.model.dao.ChannelRepository;
import ru.itis.MyTube.model.dao.ReactionRepository;
import ru.itis.MyTube.model.dao.UserRepository;
import ru.itis.MyTube.model.dao.VideoRepository;
import ru.itis.MyTube.model.dao.implementations.ChannelRepositoryJdbcImpl;
import ru.itis.MyTube.model.dao.implementations.ReactionRepositoryJdbcImpl;
import ru.itis.MyTube.model.dao.implementations.UserRepositoryJdbcImpl;
import ru.itis.MyTube.model.dao.implementations.VideoRepositoryJdbcImpl;
import ru.itis.MyTube.model.services.implementations.ChannelServiceImpl;
import ru.itis.MyTube.model.services.implementations.ReactionServiceImpl;
import ru.itis.MyTube.model.services.implementations.UserServiceImpl;
import ru.itis.MyTube.model.services.implementations.VideoServiceImpl;
import ru.itis.MyTube.model.storage.FileStorageImpl;
import ru.itis.MyTube.model.storage.Storage;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {

    private HikariDataSource dataSource;

    private UrlCreator urlCreator;

    private Storage storage;

    private MVUpdater mvUpdater;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        urlCreator = new UrlCreator(context.getContextPath());
        context.setAttribute(Beans.URL_CREATOR, urlCreator);

        storage = new FileStorageImpl();
        context.setAttribute(Beans.STORAGE, storage);

        initDataSource();

        setServices(context);

        initPageAttributes(context);

        mvUpdater = new MVUpdater(dataSource, 5000);
        mvUpdater.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        dataSource.close();
        mvUpdater.end();
    }

    private void initDataSource() {
        Properties properties = new Properties();

        try {
            properties.load(ContextListener.class.getResourceAsStream("/db.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.username"));
        config.setPassword(properties.getProperty("db.password"));

        dataSource = new HikariDataSource(config);
    }

    private void initPageAttributes(ServletContext context) {
        context.setAttribute("logoUrl",
                context.getContextPath() + "/images/reg-background-img.jpg"
        );
        context.setAttribute("appName", "MyTube");
        context.setAttribute(Attributes.COMMON_CSS_URL, context.getContextPath() + "/css/common.css");
    }

    private void setServices(ServletContext context) {
        UserRepository userRepository = new UserRepositoryJdbcImpl(dataSource);
        VideoRepository videoRepository = new VideoRepositoryJdbcImpl(dataSource);
        ChannelRepository channelRepository = new ChannelRepositoryJdbcImpl(dataSource);
        ReactionRepository reactionRepository = new ReactionRepositoryJdbcImpl(dataSource);

        VideoValidator videoValidator = new VideoValidator();
        SearchValidator searchValidator = new SearchValidator();
        UserUpdateValidator userUpdateValidator = new UserUpdateValidator();
        VideoUpdateValidator videoUpdateValidator = new VideoUpdateValidator();
        RegistrationValidator registrationValidator = new RegistrationValidator(userRepository);
        ChannelCreateValidator channelCreateValidator = new ChannelCreateValidator();
        AuthenticationValidator authenticationValidator = new AuthenticationValidator();

        context.setAttribute(
                Beans.USER_SERVICE,
                new UserServiceImpl(userRepository,
                        reactionRepository,
                        storage,
                        urlCreator,
                        userUpdateValidator,
                        registrationValidator,
                        authenticationValidator)
        );
        context.setAttribute(
                Beans.VIDEO_SERVICE,
                new VideoServiceImpl(videoRepository,
                        storage,
                        urlCreator,
                        searchValidator,
                        videoValidator,
                        videoUpdateValidator)
        );
        context.setAttribute(
                Beans.CHANNEL_SERVICE,
                new ChannelServiceImpl(channelRepository, userRepository, storage, urlCreator, channelCreateValidator)
        );
        context.setAttribute(
                Beans.REACTION_SERVICE,
                new ReactionServiceImpl(reactionRepository));
    }
}
