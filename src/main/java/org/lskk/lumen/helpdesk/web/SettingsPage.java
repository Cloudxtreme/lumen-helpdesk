package org.lskk.lumen.helpdesk.web;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.lskk.lumen.helpdesk.core.LumenException;
import org.lskk.lumen.helpdesk.twitter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.wicketstuff.annotation.mount.MountPath;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import javax.inject.Inject;

/**
 * Created by ceefour on 25/12/2016.
 */
@MountPath("settings")
public class SettingsPage extends UserLayout {

    private static final Logger log = LoggerFactory.getLogger(SettingsPage.class);

    @Inject
    private Environment env;
    @Inject
    private TwitterService twitterSvc;

    public SettingsPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final LoadableDetachableModel<TwitterApp> twitterAppModel = new LoadableDetachableModel<TwitterApp>() {
            @Override
            protected TwitterApp load() {
                return twitterSvc.loadApp();
            }
        };
        final LoadableDetachableModel<TwitterAuthz> twitterAuthzModel = new LoadableDetachableModel<TwitterAuthz>() {
            @Override
            protected TwitterAuthz load() {
                return twitterSvc.loadAuthz();
            }
        };

        final Form<Void> twitterAppForm = new Form<>("twitterAppForm");
        twitterAppForm.add(new TextField<String>("apiKeyFld", new PropertyModel<>(twitterAppModel, "apiKey")));
        twitterAppForm.add(new TextField<String>("apiSecretFld", new PropertyModel<>(twitterAppModel, "apiSecret")));
        twitterAppForm.add(new LaddaAjaxButton("saveBtn", new Model<>("Save"), Buttons.Type.Primary) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target, form);
                final TwitterApp twitterApp = twitterAppModel.getObject();
                twitterSvc.modifyApp(twitterAppModel.getObject());
            }
        });
        add(twitterAppForm);

        final Form<Void> twitterAccountForm = new Form<>("twitterAccountForm");
        twitterAccountForm.add(new Label("screenNameLabel", new PropertyModel<>(twitterAuthzModel, "screenName")));
        twitterAccountForm.add(new Label("tokenLabel", new PropertyModel<>(twitterAuthzModel, "accessToken")));
        twitterAccountForm.add(new Label("tokenSecretLabel", new PropertyModel<>(twitterAuthzModel, "accessTokenSecret")));
        twitterAccountForm.add(new LaddaAjaxButton("refreshAccessTokenBtn", new Model<>("Get Access Token"), Buttons.Type.Primary) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target, form);
                final TwitterApp twitterApp = twitterSvc.loadApp();
                final twitter4j.conf.Configuration twitterConf = new ConfigurationBuilder()
                    .setOAuthConsumerKey(twitterApp.getApiKey())
                    .setOAuthConsumerSecret(twitterApp.getApiSecret())
                    .build();
                final String serverUri = env.getRequiredProperty("server.uri");
                try {
                    final Twitter twitter = new TwitterFactory(twitterConf).getInstance();
                    final RequestToken requestToken = twitter.getOAuthRequestToken(serverUri + "twitter_settings");
                    final TwitterAuthz authz = twitterSvc.loadAuthz();
                    twitterSvc.modifyAuthzRequestToken(requestToken);
                    final String authorizeUri = "https://api.twitter.com/oauth/authorize?oauth_token=" + requestToken.getToken();
                    throw new RedirectToUrlException(authorizeUri);
                } catch (TwitterException e) {
                    throw new LumenException("Cannot get request token", e);
                }

            }
        });
        add(twitterAccountForm);

    }

    @Override
    public IModel<String> getTitleModel() {
        return new Model<>("Settings | Lumen Helpdesk");
    }

}
