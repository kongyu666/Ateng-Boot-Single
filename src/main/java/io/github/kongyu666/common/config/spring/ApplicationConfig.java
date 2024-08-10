package io.github.kongyu666.common.config.spring;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 程序注解配置
 *
 * @author Lion Li
 */
@Configuration
@EnableAspectJAutoProxy
@EnableAsync(proxyTargetClass = true)
@EnableAutoConfiguration
public class ApplicationConfig {

}
