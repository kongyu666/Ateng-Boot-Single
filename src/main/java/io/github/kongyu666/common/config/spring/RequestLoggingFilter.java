package local.ateng.boot.common.config.spring;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Http接口请求日志打印
 *
 * @author 孔余
 * @email 2385569970@qq.com
 * @date 2024-08-09 20:44:37
 */
@Component
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化逻辑（如果有需要）
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 获取请求信息
        String method = httpRequest.getMethod();
        String uri = httpRequest.getRequestURL().toString();
        String clientIp = httpRequest.getRemoteAddr();

        // 执行过滤器链
        chain.doFilter(request, response);
        logger.info("访问接口 ==> method={}, uri={}, clientIp={}", method, uri, clientIp);
    }

    @Override
    public void destroy() {
        // 清理资源（如果有需要）
    }

}
