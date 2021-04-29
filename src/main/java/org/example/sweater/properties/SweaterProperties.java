package org.example.sweater.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ivan Kurilov on 27.04.2021
 */
@Configuration
@ConfigurationProperties(prefix = "sweater.upload")
public class SweaterProperties {

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
