package org.lskk.lumen.helpdesk.web.searchindex;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.lskk.lumen.helpdesk.searchindex.SearchIndex;
import org.lskk.lumen.helpdesk.searchindex.SearchIndexRepository;
import org.lskk.lumen.helpdesk.web.UserLayout;
import org.wicketstuff.annotation.mount.MountPath;

import javax.inject.Inject;

@MountPath("search_indexes/${searchIndexId}")
public class SearchIndexEditPage extends UserLayout {

    @Inject
    private SearchIndexRepository searchIndexRepo;

    private final LoadableDetachableModel<SearchIndex> model;

    public SearchIndexEditPage(PageParameters parameters) {
        super(parameters);
        final String searchIndexId = parameters.get("searchIndexId").toString();
        model = new LoadableDetachableModel<SearchIndex>() {
            @Override
            protected SearchIndex load() {
                return searchIndexRepo.findOne(searchIndexId);
            }
        };
        add(new Label("searchIndexId", searchIndexId));
        final Form<SearchIndex> form = new Form<>("form", new CompoundPropertyModel<SearchIndex>(model));
        form.add(new NumberTextField<>("threshold", Float.class));
        form.add(new TextField<>("responseTemplate"));
        form.add(new LaddaAjaxButton("saveBtn", new Model<>("Save"), Buttons.Type.Primary) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target, form);
            }
        });
        add(form);
    }

    @Override
    public IModel<String> getTitleModel() {
        return new Model<>("Lumen Helpdesk");
    }

}
