package org.lskk.lumen.helpdesk.twitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.lskk.lumen.helpdesk.submit.HelpdeskInput;
import org.lskk.lumen.helpdesk.submit.HelpdeskMessage;
import org.lskk.lumen.helpdesk.submit.HelpdeskResult;
import org.lskk.lumen.helpdesk.submit.SubmitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by ceefour on 02/08/2016.
 */
@Configuration
@ConditionalOnProperty("helpdesk.twitter.enabled")
public class TwitterHelpdeskConfig {

    private static Logger log = LoggerFactory.getLogger(TwitterHelpdeskConfig.class);

    @Inject
    private Environment env;
    @Inject
    private TwitterService twitterSvc;
    @Inject
    private ObjectMapper objectMapper;
    @Inject
    private SubmitService submitService;

    private long curHelpdeskMessageId = 0;

    @Bean(destroyMethod = "shutdown")
    public TwitterStream twitterStreaming() {
        final List<String> trackedScreenNames = ImmutableList.of("lumenrobot", "dkijakarta"); // LOWERCASE PLEASE!

        final TwitterApp twitterApp = twitterSvc.loadApp();
        final TwitterAuthz authz = twitterSvc.loadAuthz();
        final twitter4j.conf.Configuration twitterConf = new ConfigurationBuilder()
                .setOAuthConsumerKey(twitterApp.getApiKey())
                .setOAuthConsumerSecret(twitterApp.getApiSecret())
                .setOAuthAccessToken(authz.getAccessToken())
                .setOAuthAccessTokenSecret(authz.getAccessTokenSecret())
                .build();
        final Twitter twitter = new TwitterFactory(twitterConf).getInstance();
        final TwitterStream twitterStream = new TwitterStreamFactory(twitterConf).getInstance();

        final UserStreamAdapter listener = new UserStreamAdapter() {
            @Override
            public void onStatus(Status status) {
                super.onStatus(status);
                final Optional<UserMentionEntity> mentionsTracked = Stream.of(status.getUserMentionEntities()).filter(it -> trackedScreenNames.contains(it.getScreenName().toLowerCase())).findAny();
                // ignore our own responses
                final boolean fromTracked = trackedScreenNames.contains(status.getUser().getScreenName().toLowerCase());
                if (!fromTracked && mentionsTracked.isPresent()) {
                    final HelpdeskInput helpdeskInput = new HelpdeskInput();
                    final HelpdeskMessage helpdeskMessage = new HelpdeskMessage();
                    curHelpdeskMessageId++;
                    helpdeskMessage.setId(curHelpdeskMessageId);
                    final String inputCleanText = StringUtils.removeStartIgnoreCase(status.getText(), "@" + mentionsTracked.get().getScreenName()).trim();
                    helpdeskMessage.setInputText(inputCleanText);
                    helpdeskMessage.setChannelSenderId(status.getUser().getId());
                    helpdeskMessage.setChannelSenderScreenName(status.getUser().getScreenName());
                    helpdeskInput.getMessages().add(helpdeskMessage);

                    log.info("Submitting helpdesk input from Twitter {} as {}",
                            status, helpdeskMessage);
                    final HelpdeskResult helpdeskResult = submitService.submit(helpdeskInput);
                    log.info("Helpdesk from Twitter returned {} messages: {}",
                            helpdeskResult.getMessages().size(), helpdeskResult.getMessages());
                    for (final HelpdeskMessage msg : helpdeskResult.getMessages()) {
                        if (null != msg.getResponseText()) {
                            String tweetText = "@" + status.getUser().getScreenName() + " @" + mentionsTracked.get().getScreenName() + " " + msg.getResponseText();
                            tweetText = StringUtils.abbreviate(tweetText, 115);
                            if (null != msg.getGmapsUri()) {
                                tweetText += " " + msg.getGmapsUri();
                            }
                            final StatusUpdate responseTweet = new StatusUpdate(tweetText);
                            responseTweet.setInReplyToStatusId(status.getId());
                            if (null != msg.getHospitalLat() && null != msg.getHospitalLon()) {
                                responseTweet.setLocation(new GeoLocation(msg.getHospitalLat(), msg.getHospitalLon()));
                                responseTweet.setDisplayCoordinates(true);
                            }
                            log.info("Responding Twitter {}", responseTweet);
                            try {
                                twitter.tweets().updateStatus(responseTweet);
                            } catch (TwitterException e) {
                                log.error("Cannot tweet " + responseTweet, e);
                            }
                        }
                    }
                } else {
                    log.info("Ignoring Twitter status: {}", status);
                }
            }
        };
        twitterStream.addListener(listener);
        final FilterQuery filterQuery = new FilterQuery(
                trackedScreenNames.stream().map(it -> "@" + it).toArray(String[]::new));
        log.info("Starting Twitter stream filter {} ...", filterQuery);
        twitterStream.filter(filterQuery);
        return twitterStream;
    }

    public void updateStatus(StatusUpdate update) throws TwitterException {
        final TwitterApp twitterApp = twitterSvc.loadApp();
        final TwitterAuthz authz = twitterSvc.loadAuthz();
        final twitter4j.conf.Configuration twitterConf = new ConfigurationBuilder()
                .setOAuthConsumerKey(twitterApp.getApiKey())
                .setOAuthConsumerSecret(twitterApp.getApiSecret())
                .setOAuthAccessToken(authz.getAccessToken())
                .setOAuthAccessTokenSecret(authz.getAccessTokenSecret())
                .build();
        final Twitter twitter = new TwitterFactory(twitterConf).getInstance();
        twitter.updateStatus(update);
    }

}