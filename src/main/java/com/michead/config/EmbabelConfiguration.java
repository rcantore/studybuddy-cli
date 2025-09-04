package com.michead.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration to provide missing beans that Embabel framework expects
 * but we don't need for our Anthropic-only CLI setup.
 */
@Configuration
public class EmbabelConfiguration {

    @Bean(name = "bedrockModels")
    @ConditionalOnMissingBean(name = "bedrockModels")
    public Object bedrockModels() {
        // Return empty object to satisfy dependency injection
        // This bean won't be used since we're using Anthropic models only
        return new Object();
    }
}