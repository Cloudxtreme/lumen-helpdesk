package org.lskk.lumen.helpdesk.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import javax.inject.Inject;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Created by ceefour on 25/12/2016.
 */
@Service
public class TwitterService {

    private static final Logger log = LoggerFactory.getLogger(TwitterService.class);

    @Inject
    private TwitterAppRepository twitterAppRepo;
    @Inject
    private TwitterAuthzRepository twitterAuthzRepo;

    @Transactional
    public TwitterApp loadApp() {
        TwitterApp twitterApp = twitterAppRepo.findOne(TwitterConfig.APP_ID);
        if (null == twitterApp) {
            twitterApp = new TwitterApp();
            twitterApp.setId(TwitterConfig.APP_ID);
            twitterApp = twitterAppRepo.save(twitterApp);
        }
        return twitterApp;
    }

    @Transactional
    public TwitterAuthz loadAuthz() {
        final List<TwitterAuthz> authzes = twitterAuthzRepo.findAllByAppId(TwitterConfig.APP_ID);
        TwitterAuthz authz;
        if (authzes.isEmpty()) {
            authz = new TwitterAuthz();
            authz.setApp(loadApp());
            authz = twitterAuthzRepo.save(authz);
        } else {
            authz = authzes.get(0);
        }
        return authz;
    }

    @Transactional
    public TwitterApp modifyApp(TwitterApp upApp) {
        TwitterApp app = loadApp();
        app.setApiKey(upApp.getApiKey());
        app.setApiSecret(upApp.getApiSecret());
        log.info("Saving Twitter API key={} secret={}", app.getApiKey(), app.getApiSecret());
        return twitterAppRepo.save(app);
    }

    public TwitterAuthz modifyAuthzRequestToken(RequestToken requestToken) {
        TwitterAuthz authz = loadAuthz();
        authz.setRequestToken(requestToken.getToken());
        authz.setRequestTokenSecret(requestToken.getTokenSecret());
        log.info("Saving Twitter request token");
        return twitterAuthzRepo.save(authz);
    }

    public TwitterAuthz modifyAuthzAccessToken(AccessToken accessToken) {
        TwitterAuthz authz = loadAuthz();
        authz.setAccessToken(accessToken.getToken());
        authz.setAccessTokenSecret(accessToken.getTokenSecret());
        authz.setUserId(accessToken.getUserId());
        authz.setScreenName(accessToken.getScreenName());
        authz.setCreationTime(OffsetDateTime.now());
        authz.setRequestToken(null);
        authz.setRequestTokenSecret(null);
        log.info("Saving Twitter access token for @{}", authz.getScreenName());
        return twitterAuthzRepo.save(authz);
    }
}
