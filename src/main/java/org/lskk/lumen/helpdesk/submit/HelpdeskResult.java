package org.lskk.lumen.helpdesk.submit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ceefour on 01/08/2016.
 */
public class HelpdeskResult implements Serializable {

    private List<HelpdeskMessage> messages = new ArrayList<>();
    private int elapsedMillis;

    public List<HelpdeskMessage> getMessages() {
        return messages;
    }

    public int getElapsedMillis() {
        return elapsedMillis;
    }

    public void setElapsedMillis(int elapsedMillis) {
        this.elapsedMillis = elapsedMillis;
    }
}
