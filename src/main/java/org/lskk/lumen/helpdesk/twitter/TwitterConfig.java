package org.lskk.lumen.helpdesk.twitter;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ceefour on 6/24/15.
 */
@Configuration
public class TwitterConfig {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TwitterConfig.class);
    public static final String APP_ID = "lumen";
//    public static final String AGENT_ID = "arkan";

//    @Inject
//    private Environment env;
//    @Inject
//    private ObjectMapper objectMapper;
//    @Inject
//    private TwitterAppRepository twitterAppRepo;
//    @Inject
//    private TwitterAuthzRepository twitterAuthzRepo;
//
////    @Bean
////    public TwitterApp twitterApp() throws IOException {
////        return objectMapper.readValue(new File("config/" + APP_ID + ".TwitterApp.jsonld"), TwitterApp.class);
////    }
//
//    @Bean @Scope("prototype")
//    public TwitterAuthz twitterAuthz() throws IOException {
//        final Page<TwitterAuthz> page = twitterAuthzRepo.findAll(new PageRequest(0, 1));
//        Preconditions.checkState(page.hasContent(), "Cannot get TwitterAuthz");
//        return page.getContent().get(0);
////        return objectMapper.readValue(new File("config/agent/" + AGENT_ID + ".TwitterAuthorization.jsonld"), TwitterAuthz.class);
//    }

//    @Bean @Scope("prototype")
//    public TwitterFactory twitterFactory() throws IOException {
//        final TwitterApp twitterApp = Preconditions.checkNotNull(twitterAppRepo.findOne(APP_ID),
//                "Cannot get TwitterApp for '%s'", APP_ID);
//        final twitter4j.conf.Configuration twitterConf = new ConfigurationBuilder()
//                .setOAuthConsumerKey(twitterApp.getApiKey())
//                .setOAuthConsumerSecret(twitterApp.getApiSecret())
//                .build();
//        return new TwitterFactory(twitterConf);
//    }
//
//    @Bean @Scope("prototype")
//    public TwitterStreamFactory twitterStreamFactory() throws IOException {
//        final TwitterApp twitterApp = Preconditions.checkNotNull(twitterAppRepo.findOne(APP_ID),
//                "Cannot get TwitterApp for '%s'", APP_ID);
//        final twitter4j.conf.Configuration twitterConf = new ConfigurationBuilder()
//                .setOAuthConsumerKey(twitterApp.getApiKey())
//                .setOAuthConsumerSecret(twitterApp.getApiSecret())
//                .build();
//        return new TwitterStreamFactory(twitterConf);
//    }

}