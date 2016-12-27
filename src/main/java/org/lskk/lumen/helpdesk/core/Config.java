package org.lskk.lumen.helpdesk.core;

import com.google.common.collect.ImmutableList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * Created by ceefour on 25/12/2016.
 */
@Entity
public class Config implements Serializable {

    public static class ConfigDef<T> {
        String id;
        T defaultValue;

        public ConfigDef(String id, T defaultValue) {
            this.id = id;
            this.defaultValue = defaultValue;
        }

        public String getId() {
            return id;
        }

        public T getDefaultValue() {
            return defaultValue;
        }
    }

    /**
     * Tracked users, in lowercase.
     */
    public static final ConfigDef<List> TWITTER_TRACKED_USERS = new ConfigDef<>("twitter.tracked-users", ImmutableList.of("lumenrobot", "dkijakarta"));

    @Id
    String id;
    @Column(columnDefinition = "text")
    String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
