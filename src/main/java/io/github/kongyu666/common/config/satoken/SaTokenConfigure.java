package local.ateng.boot.common.config.satoken;

import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.httpauth.basic.SaHttpBasicUtil;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import local.ateng.boot.common.enums.AppCodeEnum;
import local.ateng.boot.common.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token 注解鉴权
 * https://sa-token.cc/doc.html#/use/at-check
 *
 * @author 孔余
 * @since 2024-05-21 17:17
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    private static final Logger log = LoggerFactory.getLogger(SaTokenConfigure.class);

    // 注册 Sa-Token 拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry
                .addInterceptor(
                        new SaInterceptor(
                                handle -> StpUtil.checkLogin()
                        ).isAnnotation(true) // 注解鉴权
                )
                .addPathPatterns("/**")
                .excludePathPatterns("/actuator/**", "/demo/**")
        ;
    }

    /**
     * 注册 [Sa-Token 全局过滤器]
     */
    @Bean
    public FilterRegistrationBean<SaServletFilter> getSaServletFilter() {
        FilterRegistrationBean<SaServletFilter> frBean = new FilterRegistrationBean<>();
        frBean.setFilter(
                new SaServletFilter()
                        .addInclude("/actuator/**")
                        .setAuth(obj -> {
                            // 放开 /actuator/health 节点，其余接口需要基础验证
                            SaRouter
                                    .notMatch("/actuator/health")
                                    .match("/actuator/**", () -> SaHttpBasicUtil.check("admin:Admin@123"));
                        }).setError(e -> {
                            log.error(e.getMessage());
                            return Result.error(AppCodeEnum.OPERATION_CANCELED.getCode(), AppCodeEnum.OPERATION_CANCELED.getDescription());
                        })
        );
        frBean.setOrder(-101);  // 更改顺序为 -101
        return frBean;
    }

}




