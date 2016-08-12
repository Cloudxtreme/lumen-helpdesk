package org.lskk.lumen.helpdesk.submit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * Created by ceefour on 01/08/2016.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HelpdeskMessage implements Serializable {

    public enum EscalationState {
        /**
         * Not yet escalated or pending escalation.
         */
        NONE,
        /**
         * Asked to staff, but hasn't yet received an answer.
         */
        ASKED,
        /**
         * Answer has been given by staff (should be removed from {@link org.lskk.lumen.helpdesk.escalation.EscalationService#messageQueue}).
         */
        ANSWERED
    }

    private Long id;
    private Long channelSenderId;
    private String channelSenderScreenName;
    private String senderName;
    private String inputText;
    private InputKind inputKind;
    private String districtUpName;
    private String districtName;
    private String districtId;
    private Integer hospitalId;
    private String hospitalName;
    private String hospitalAddress;
    private String hospitalPhone;
    private Float hospitalLat;
    private Float hospitalLon;
    private String responseText;
    private String gmapsUri;
    private EscalationState escalationState;
    private int askedCount;

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

    public InputKind getInputKind() {
        return inputKind;
    }

    public void setInputKind(InputKind inputKind) {
        this.inputKind = inputKind;
    }

    public String getDistrictUpName() {
        return districtUpName;
    }

    public void setDistrictUpName(String districtUpName) {
        this.districtUpName = districtUpName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Float getHospitalLat() {
        return hospitalLat;
    }

    public void setHospitalLat(Float hospitalLat) {
        this.hospitalLat = hospitalLat;
    }

    public Float getHospitalLon() {
        return hospitalLon;
    }

    public void setHospitalLon(Float hospitalLon) {
        this.hospitalLon = hospitalLon;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getHospitalPhone() {
        return hospitalPhone;
    }

    public void setHospitalPhone(String hospitalPhone) {
        this.hospitalPhone = hospitalPhone;
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

    public String getGmapsUri() {
        return gmapsUri;
    }

    public void setGmapsUri(String gmapsUri) {
        this.gmapsUri = gmapsUri;
    }

    public EscalationState getEscalationState() {
        return escalationState;
    }

    public void setEscalationState(EscalationState escalationState) {
        this.escalationState = escalationState;
    }

    public int getAskedCount() {
        return askedCount;
    }

    public void setAskedCount(int askedCount) {
        this.askedCount = askedCount;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("id", id)
                .add("channelSenderId", channelSenderId)
                .add("channelSenderScreenName", channelSenderScreenName)
                .add("senderName", senderName)
                .add("inputText", inputText)
                .add("inputKind", inputKind)
                .add("districtUpName", districtUpName)
                .add("districtName", districtName)
                .add("districtId", districtId)
                .add("hospitalId", hospitalId)
                .add("hospitalName", hospitalName)
                .add("hospitalAddress", hospitalAddress)
                .add("hospitalPhone", hospitalPhone)
                .add("hospitalLat", hospitalLat)
                .add("hospitalLon", hospitalLon)
                .add("responseText", responseText)
                .add("gmapsUri", gmapsUri)
                .toString();
    }
}
