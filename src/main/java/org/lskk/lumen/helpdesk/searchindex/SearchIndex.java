package org.lskk.lumen.helpdesk.searchindex;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Created by ceefour on 26/12/2016.
 */
@Entity
public class SearchIndex implements Serializable {
    @Id
    private String id;

    @Transient
    private OffsetDateTime creationTime;
    @Transient
    private List<String> properties;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OffsetDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(OffsetDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public List<String> getProperties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
    }
}
