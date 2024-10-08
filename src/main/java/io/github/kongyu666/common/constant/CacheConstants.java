package io.github.kongyu666.common.constant;

/**
 * 缓存的key 常量
 *
 * @author 孔余
 * @since 2024-05-20 14:17:56
 */
public interface CacheConstants {

    /**
     * 在线用户 redis key
     */
    String ONLINE_TOKEN_KEY = "online_tokens:";

    /**
     * 参数管理 cache key
     */
    String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    String SYS_DICT_KEY = "sys_dict:";

}
