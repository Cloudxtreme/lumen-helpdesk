package org.lskk.lumen.helpdesk.core;

import com.google.common.base.MoreObjects;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by ceefour on 31/07/2016.
 */
@Entity
public class GeneralHospital implements Serializable {

    @Id
    private Integer id;
    private String name;
    private String kind;
    private Location location;
    private String postalCode;
    private String phone0;
    private String phone1;
    private String phone2;
    private String faximile0;
    private String faximile1;
    private String website;
    private String email;
    private String cityId;
    private String districtId;
    private String villageId;
    private Float lat;
    private Float lon;
    private Point<G2D> geometry;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone0() {
        return phone0;
    }

    public void setPhone0(String phone0) {
        this.phone0 = phone0;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getFaximile0() {
        return faximile0;
    }

    public void setFaximile0(String faximile0) {
        this.faximile0 = faximile0;
    }

    public String getFaximile1() {
        return faximile1;
    }

    public void setFaximile1(String faximile1) {
        this.faximile1 = faximile1;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
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

    public Point<G2D> getGeometry() {
        return geometry;
    }

    public void setGeometry(Point<G2D> geometry) {
        this.geometry = geometry;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("id", id)
                .add("name", name)
                .add("kind", kind)
                .add("location", location)
                .add("postalCode", postalCode)
                .add("phone0", phone0)
                .add("phone1", phone1)
                .add("phone2", phone2)
                .add("faximile0", faximile0)
                .add("faximile1", faximile1)
                .add("website", website)
                .add("email", email)
                .add("cityId", cityId)
                .add("districtId", districtId)
                .add("villageId", villageId)
                .add("lat", lat)
                .add("lon", lon)
                .add("geometry", geometry)
                .toString();
    }
}
