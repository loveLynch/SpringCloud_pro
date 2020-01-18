package com.lynch.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 使用RefreshScope和ConfigurationProperties配置进行要刷新的范围
 * <p>
 * Created by lynch on 2020-01-15.
 **/

@Component
@ConfigurationProperties("girl")
@RefreshScope
@Data
public class GirlConfig {

    private String name;
    private Integer age;
}
