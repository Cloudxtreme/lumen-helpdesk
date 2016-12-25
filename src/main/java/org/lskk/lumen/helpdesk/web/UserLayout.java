package org.lskk.lumen.helpdesk.web;

import com.google.common.base.Preconditions;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.html.HtmlTag;
import de.agilecoders.wicket.core.markup.html.bootstrap.html.MetaTag;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeCssReference;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.inject.Inject;
import java.util.Locale;

public abstract class UserLayout extends WebPage {

    protected NotificationPanel notificationPanel;

    public abstract IModel<String> getTitleModel();

    @Inject
    protected Environment env;

    public UserLayout(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Preconditions.checkNotNull(authentication.getPrincipal(), "Must be logged in");

        //add(new GrowlBehavior());
        add(new HtmlTag("html", Locale.US));
        add(new Label("title", getTitleModel()));
        add(new Label("principalLabel", authentication.getName()));

//        add(new BookmarkablePageLink<>("skillManagementLink", SkillManagementPage.class, new PageParameters().set("skillId", "")));
//        add(new BookmarkablePageLink<>("roadsLink", RoadListPage.class,
//                new PageParameters().set(SeoBookmarkableMapper.LOCALE_PREF_ID_PARAMETER, localePrefId)));
//        add(new BookmarkablePageLink<>("camerasLink", CameraListPage.class,
//                new PageParameters().set(SeoBookmarkableMapper.LOCALE_PREF_ID_PARAMETER, localePrefId)));
//
//        add(new BookmarkablePageLink<>("tweetListLink", TweetListPage.class,
//                new PageParameters().set(SeoBookmarkableMapper.LOCALE_PREF_ID_PARAMETER, localePrefId)));
//        add(new BookmarkablePageLink<>("tweetMapLink", TweetMapPage.class,
//                new PageParameters().set(SeoBookmarkableMapper.LOCALE_PREF_ID_PARAMETER, localePrefId)));
        notificationPanel = new NotificationPanel("notificationPanel");
        notificationPanel.setOutputMarkupId(true);
        add(notificationPanel);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem.forReference(FontAwesomeCssReference.instance()));
    }

}
