package xyz.dingxs.subscribe.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.dingxs.subscribe.common.interceptor.AuthorizedInterceptor;

/**
 * 权限拦截器注册
 *
 * @author dingxs
 */
@Configuration
public class AuthorizedWebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthorizedInterceptor authorizedInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        InterceptorRegistration registration = registry.addInterceptor(authorizedInterceptor);
        // 所有路径都被拦截
        registration.addPathPatterns("/**");
        // 添加不拦截路径
        registration.excludePathPatterns(
                // 订阅
                "/**/subscribe/get",
                // swagger
                "/sw/**",
                "/v3/api-docs"
        );
    }
}
