package xyz.dingxs.subscribe.common.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.dingxs.subscribe.common.config.properties.SubscribeConfigProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截器
 *
 * @author dingxs
 */
@Component
public class AuthorizedInterceptor implements HandlerInterceptor {

    @Autowired
    private SubscribeConfigProperties subscribeConfigProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader("token");

        if (subscribeConfigProperties.getAuthorized().getToken().equals(token)) {
            return true;
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

    }
}
