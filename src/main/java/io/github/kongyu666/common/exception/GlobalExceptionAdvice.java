package local.ateng.boot.common.exception;

import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.util.SaResult;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一处理异常
 *
 * @author 孔余
 * @since 2023-03-20 16:23:02
 */
@RestControllerAdvice
@Order(99)
public class GlobalExceptionAdvice {

    /**
     * 处理SaToken的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(SaTokenException.class)
    public SaResult handlerSaTokenException(SaTokenException e) {
        // https://sa-token.cc/doc.html#/fun/not-login-scene
        // https://sa-token.cc/doc.html#/fun/exception-code
        int code = e.getCode();
        String message = e.getMessage();
        // 自定义返回信息
        if (code == 11011) {
            message = "用户未登录";
        } else if (code == 11012) {
            message = "用户无效";
        } else if (code == 11013) {
            message = "用户登录过期";
        } else if (code == 11016) {
            message = "用户已被冻结";
        } else if (code == 11041 || code == 11051) {
            message = "用户无权限";
        }
        return SaResult.error(message).set("code", String.valueOf(code));
    }

}
