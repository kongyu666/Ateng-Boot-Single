package local.ateng.boot.common.config.gracefulResponse.exception;

import com.feiniaojin.gracefulresponse.api.ExceptionAliasFor;
import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 * 类的模板注释
 *
 * @author 孔余
 * @since 2024-01-17 12:20
 */
@ExceptionAliasFor(code = "-1", msg = "请求方式错误", aliasFor = {HttpRequestMethodNotSupportedException.class})
public class MyHttpRequestMethodNotSupportedException extends RuntimeException {
}
