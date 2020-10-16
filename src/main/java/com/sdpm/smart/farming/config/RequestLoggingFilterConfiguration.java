package com.sdpm.smart.farming.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;


/**
 * config for request logs by CommonsRequestLoggingFilter
 *
 * @author rukey
 */
@Configuration
public class RequestLoggingFilterConfiguration {
    @Bean
    public CommonsRequestLoggingFilter filter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setAfterMessagePrefix("Request: ");
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(64000);
        return loggingFilter;
    }
}
