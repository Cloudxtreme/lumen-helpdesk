package org.lskk.lumen.helpdesk.core;

import com.google.common.base.MoreObjects;
import com.vividsolutions.jts.geom.Polygon;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by ceefour on 31/07/2016.
 */
@Entity
public class District implements Serializable {
    @Id
    private String id;
    private String name;
    private String provinceId;
    private String provinceName;
    private String cityId;
    private String cityName;
    private Polygon geometry;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Polygon getGeometry() {
        return geometry;
    }

    public void setGeometry(Polygon geometry) {
        this.geometry = geometry;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("id", id)
                .add("name", name)
                .add("provinceId", provinceId)
                .add("provinceName", provinceName)
                .add("cityId", cityId)
                .add("cityName", cityName)
                .add("geometry", geometry)
                .toString();
    }
}
