package io.github.kongyu666.common.config.spring;

import cn.hutool.core.util.ArrayUtil;
import io.github.kongyu666.common.exception.ServiceException;
import io.github.kongyu666.common.utils.SpringUtils;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.Arrays;
import java.util.concurrent.Executor;

/**
 * 开启异步
 *
 * @author 孔余
 * @since 2024-05-22 09:33:25
 */
@Configuration
public class AsyncConfig implements AsyncConfigurer {
    /**
     * 自定义 @Async 注解使用系统线程池
     */
    @Override
    public Executor getAsyncExecutor() {
        if (SpringUtils.isVirtual()) {
            return new VirtualThreadTaskExecutor("async-");
        }
        return SpringUtils.getBean("scheduledExecutorService");
    }

    /**
     * 异步执行异常处理
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            throwable.printStackTrace();
            StringBuilder sb = new StringBuilder();
            sb.append("Exception message - ").append(throwable.getMessage())
                    .append(", Method name - ").append(method.getName());
            if (ArrayUtil.isNotEmpty(objects)) {
                sb.append(", Parameter value - ").append(Arrays.toString(objects));
            }
            throw new ServiceException(sb.toString());
        };
    }
}
