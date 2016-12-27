package org.lskk.lumen.helpdesk.web.searchindex;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by ceefour on 26/12/2016.
 */
public class IdPanel extends Panel {
    public IdPanel(String id, IModel<?> model, Class<? extends Page> page, String paramName, String paramValue) {
        super(id, model);
        setRenderBodyOnly(true);

        final BookmarkablePageLink<Object> link = new BookmarkablePageLink<>("link", SearchIndexEditPage.class,
                new PageParameters().set("searchIndexId", model.getObject()));
        link.setBody(model);
        link.setRenderBodyOnly(false);
        add(link);
    }
}
