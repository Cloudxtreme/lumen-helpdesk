package org.lskk.lumen.helpdesk.twitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.soluvas.commons.config.SoluvasApplication;
import org.soluvas.json.JsonUtils;
import org.soluvas.socmed.TwitterApp;
import org.soluvas.socmed.TwitterAuthorization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.conf.PropertyConfiguration;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by ceefour on 6/24/15.
 */
@Configuration
public class TwitterConfig {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TwitterConfig.class);
    public static final String APP_ID = "lumen";
    public static final String AGENT_ID = "arkan";

    @Inject
    private Environment env;
    @Inject
    private ObjectMapper objectMapper;

    @Bean
    public TwitterApp twitterApp() throws IOException {
        return objectMapper.readValue(new File("config/" + APP_ID + ".TwitterApp.jsonld"), TwitterApp.class);
    }

    @Bean
    public TwitterAuthorization twitterAuthorization() throws IOException {
        return objectMapper.readValue(new File("config/agent/" + AGENT_ID + ".TwitterAuthorization.jsonld"), TwitterAuthorization.class);
    }

    @Bean
    public TwitterFactory twitterFactory() throws IOException {
        final twitter4j.conf.Configuration twitterConf = new ConfigurationBuilder()
                .setOAuthConsumerKey(twitterApp().getApiKey())
                .setOAuthConsumerSecret(twitterApp().getApiSecret())
                .build();
        return new TwitterFactory(twitterConf);
    }

    @Bean
    public TwitterStreamFactory twitterStreamFactory() throws IOException {
        final twitter4j.conf.Configuration twitterConf = new ConfigurationBuilder()
                .setOAuthConsumerKey(twitterApp().getApiKey())
                .setOAuthConsumerSecret(twitterApp().getApiSecret())
                .build();
        return new TwitterStreamFactory(twitterConf);
    }

}