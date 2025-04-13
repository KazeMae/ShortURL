package fun.kazex.dwz.config;

import fun.kazex.dwz.interceptor.AccessLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: KazeMae
 * @Date: 2025/04/14 00:20
 * @Description:
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    AccessLimitInterceptor accessLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLimitInterceptor);
    }
}