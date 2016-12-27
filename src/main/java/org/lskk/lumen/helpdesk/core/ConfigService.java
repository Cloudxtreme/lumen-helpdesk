package org.lskk.lumen.helpdesk.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * Created by ceefour on 25/12/2016.
 */
@Service
public class ConfigService {

    @Inject
    private ConfigRepository configRepo;
    @Inject
    private ObjectMapper mapper;

    public String get(Config.ConfigDef<String> configDef) {
        final Config config = configRepo.findOne(configDef.getId());
        if (null != config) {
            final String valueJson = config.getValue();
            try {
                final String value = mapper.readValue(valueJson, String.class);
                return value;
            } catch (IOException e) {
                throw new LumenException("Cannot parse JSON to string: " + valueJson, e);
            }
        } else {
            return (String) configDef.getDefaultValue();
        }
    }

    public String getJson(Config.ConfigDef<?> configDef) {
        final Config config = configRepo.findOne(configDef.getId());
        if (null != config) {
            return config.getValue();
        } else {
            try {
                return mapper.writeValueAsString(configDef.getDefaultValue());
            } catch (JsonProcessingException e) {
                throw new LumenException("Cannot write to JSON", e);
            }
        }
    }

    public <T> T get(Config.ConfigDef<T> configDef, Class<T> clazz) {
        final Config config = configRepo.findOne(configDef.getId());
        if (null != config) {
            final String valueJson = config.getValue();
            try {
                final T value = mapper.readValue(valueJson, clazz);
                return value;
            } catch (IOException e) {
                throw new LumenException("Cannot parse JSON to " + clazz.getName() + ": " + valueJson, e);
            }
        } else {
            return (T) configDef.getDefaultValue();
        }
    }

    public <T> void set(Config.ConfigDef<T> configDef, T value) {
        Config config = configRepo.findOne(configDef.getId());
        if (null == config) {
            config = new Config();
            config.setId(configDef.getId());
        }
        try {
            config.setValue(mapper.writeValueAsString(value));
            configRepo.save(config);
        } catch (JsonProcessingException e) {
            throw new LumenException("Cannot write to JSON", e);
        }
    }

    public void setJson(Config.ConfigDef<?> configDef, String json) {
        Config config = configRepo.findOne(configDef.getId());
        if (null == config) {
            config = new Config();
            config.setId(configDef.getId());
        }
        config.setValue(json);
        configRepo.save(config);
    }

}
