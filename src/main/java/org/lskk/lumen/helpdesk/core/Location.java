package org.lskk.lumen.helpdesk.core;

import com.google.common.base.MoreObjects;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by ceefour on 31/07/2016.
 */
@Embeddable
public class Location implements Serializable {

    private String address;
    private Float lat;
    private Float lon;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("address", address)
                .add("lat", lat)
                .add("lon", lon)
                .toString();
    }
}
