package com.lynch.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * 跨域配置
 * Created by lynch on 2020-02-21.
 * C - Cross  O - Origin  R - Resource  S - Sharing
 **/
@Component
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("*")); //原始域，如：http://www.a.com
        config.setAllowedHeaders(Arrays.asList("*"));//允许头
        config.setAllowedMethods(Arrays.asList("*"));//允许的方法，get,post ...
        config.setMaxAge(300l);//缓存时间
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
