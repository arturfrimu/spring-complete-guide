package com.arturfrimu.logtime.autoconfigure;

import com.arturfrimu.logtime.aspect.LogTimeAspect;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@AutoConfigureAfter(AopAutoConfiguration.class)
@ConditionalOnProperty(prefix = "logtime.aspect", name = "enabled", havingValue = "true")
@EnableAspectJAutoProxy
public class LogTimeAutoConfiguration {

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)  // dacă ai și alte aspecte, pune-l la coadă
    public LogTimeAspect logTimeAspect() {
        return new LogTimeAspect();
    }
}
