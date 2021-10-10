package com.yu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 15:32 2021/10/10
 * @Modified By:
 */
@ConfigurationProperties(prefix = "spring.yu")
@Component
public class testConfig {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
