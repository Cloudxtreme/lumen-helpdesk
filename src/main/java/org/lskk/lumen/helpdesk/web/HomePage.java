package org.lskk.lumen.helpdesk.web;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.annotation.mount.MountPath;

public class HomePage extends UserLayout {

    public HomePage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    public IModel<String> getTitleModel() {
        return new Model<>("Lumen Helpdesk");
    }

}
