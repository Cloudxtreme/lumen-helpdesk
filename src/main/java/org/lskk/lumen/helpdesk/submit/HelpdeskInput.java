package org.lskk.lumen.helpdesk.submit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ceefour on 01/08/2016.
 */
public class HelpdeskInput implements Serializable {

    private List<HelpdeskMessage> messages = new ArrayList<>();

    public List<HelpdeskMessage> getMessages() {
        return messages;
    }
}
