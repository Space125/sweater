package org.example.sweater.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ivan Kurilov on 12.05.2021
 */
@Configuration
@ConfigurationProperties(prefix = "sweater")
public class ServerProperties {

    private String hostname;
    private int port;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
