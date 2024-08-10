package io.github.kongyu666.common.config.snailjob;

import com.aizuda.snailjob.client.starter.EnableSnailJob;
import org.springframework.context.annotation.Configuration;

/**
 * 启动SnailJob服务
 *
 * @author 孔余
 * @since 2024-05-27 11:55
 */
@Configuration
@EnableSnailJob(group = "ateng_boot")
public class SnailJobConfig {
}
