package org.lskk.lumen.helpdesk.twitter;

import com.google.common.base.Preconditions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ceefour on 25/12/2016.
 */
@RestController
public class TwitterController {

    @Inject
    private TwitterService twitterSvc;

    @GetMapping("twitter_settings")
    public void verifier(HttpServletResponse response, @RequestParam("oauth_verifier") String verifier) throws IOException, TwitterException {
        final TwitterApp twitterApp = twitterSvc.loadApp();
        final TwitterAuthz authz = twitterSvc.loadAuthz();
        final twitter4j.conf.Configuration twitterConf = new ConfigurationBuilder()
                .setOAuthConsumerKey(twitterApp.getApiKey())
                .setOAuthConsumerSecret(twitterApp.getApiSecret())
                .build();
        final Twitter twitter = new TwitterFactory(twitterConf).getInstance();
        final AccessToken accessToken = twitter.getOAuthAccessToken(new RequestToken(authz.getRequestToken(), authz.getRequestTokenSecret()), verifier);
        twitterSvc.modifyAuthzAccessToken(accessToken);
        response.sendRedirect("/settings");
    }
}
