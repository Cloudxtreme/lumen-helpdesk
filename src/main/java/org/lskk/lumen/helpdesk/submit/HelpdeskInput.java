package org.lskk.lumen.helpdesk.submit;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ceefour on 01/08/2016.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HelpdeskInput implements Serializable {

    private List<HelpdeskMessage> messages = new ArrayList<>();

    public List<HelpdeskMessage> getMessages() {
        return messages;
    }
}
