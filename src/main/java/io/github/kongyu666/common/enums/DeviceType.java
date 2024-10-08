package io.github.kongyu666.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 设备类型
 * 针对一套 用户体系
 *
 * @author Lion Li
 */
@Getter
@AllArgsConstructor
public enum DeviceType {

    /**
     * pc端
     */
    PC("PC"),

    /**
     * app端
     */
    APP("APP"),

    /**
     * 微信小程序
     */
    WXXCX("WeChat Mini Program"),

    /**
     * 支付宝小程序
     */
    ZFBXCX("Alipay Mini Program");

    private final String device;
}
