package local.ateng.boot.common.config.gracefulResponse.exception;

import com.feiniaojin.gracefulresponse.api.ExceptionAliasFor;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 类的模板注释
 *
 * @author 孔余
 * @since 2024-01-17 12:20
 */
@ExceptionAliasFor(code = "-1", msg = "资源不存在", aliasFor = {NoResourceFoundException.class})
public class MyNoResourceFoundException extends RuntimeException {
}
