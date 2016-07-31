package org.lskk.lumen.helpdesk.submit;

import java.io.Serializable;

/**
 * Created by ceefour on 01/08/2016.
 */
public class HelpdeskMessage implements Serializable {

    private Long id;
    private Long channelSenderId;
    private String channelSenderScreenName;
    private String senderName;
    private String inputText;
    private String responseText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChannelSenderId() {
        return channelSenderId;
    }

    public void setChannelSenderId(Long channelSenderId) {
        this.channelSenderId = channelSenderId;
    }

    public String getChannelSenderScreenName() {
        return channelSenderScreenName;
    }

    public void setChannelSenderScreenName(String channelSenderScreenName) {
        this.channelSenderScreenName = channelSenderScreenName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    /**
     * Response from Lumen Helpdesk AI.
     * @return
     */
    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
}
