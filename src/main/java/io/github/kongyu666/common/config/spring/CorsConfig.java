package local.ateng.boot.common.config.spring;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置类，用于配置跨域资源共享（CORS）策略。
 * 作者：孔余
 * 日期：2024-05-11 20:56
 */
@Configuration
public class CorsConfig {

    /**
     * 配置CORS规则的方法。
     *
     * @return WebMvcConfigurer对象，用于配置CORS规则
     */
    /*@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // 添加CORS映射，允许所有来源的请求
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")  // 允许的来源
                        .allowedMethods("GET", "POST", "PUT", "DELETE")  // 允许的方法
                        .allowedHeaders("*")  // 允许的请求头
                        .allowCredentials(true)  // 是否允许携带凭证
                        .maxAge(3600);  // 预检请求的缓存时间
            }
        };
    }*/

    /**
     * 配置CORS规则的方法。
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        // 创建基于 URL 的跨域配置源
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // 创建跨域配置对象
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 允许携带凭据（如 Cookie）
        config.addAllowedOriginPattern("*"); // 允许所有来源
        config.addAllowedHeader("*"); // 允许所有请求头
        config.addAllowedMethod("*"); // 允许所有 HTTP 方法
        config.setMaxAge(3600L);

        // 注册跨域配置对象
        source.registerCorsConfiguration("/**", config);

        // 创建过滤器注册对象
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE); // 设置过滤器执行顺序，最高优先级

        return bean;
    }
}
