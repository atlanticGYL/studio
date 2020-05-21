package com.my.studio.adoTest.zk;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@ConfigurationProperties(prefix = "zk")
public class ZKConfig {
    private String address;
    private String sessionTimeout;
    private String connectionTimeout;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(String sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public String getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(String connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    @Override
    public String toString() {
        HashSet set = new HashSet();

        return "ZKConfig{" +
                "address='" + address + '\'' +
                ", sessionTimeout='" + sessionTimeout + '\'' +
                ", connectionTimeout='" + connectionTimeout + '\'' +
                '}';
    }
}
