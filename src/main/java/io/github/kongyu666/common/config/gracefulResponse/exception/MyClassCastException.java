package local.ateng.boot.common.config.gracefulResponse.exception;

import com.feiniaojin.gracefulresponse.api.ExceptionAliasFor;

/**
 * 类的模板注释
 *
 * @author 孔余
 * @since 2024-01-17 12:20
 */
@ExceptionAliasFor(code = "-1", msg = "类转换异常", aliasFor = {ClassCastException.class})
public class MyClassCastException extends RuntimeException {
}
