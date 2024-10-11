package io.github.kongyu666.common.config.spring;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 校验框架配置类
 *
 * @author Lion Li
 */
@Configuration
public class ValidatorConfig {

    /**
     * 配置校验框架 快速失败模式
     */
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)  // 启用快速失败模式
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

}
