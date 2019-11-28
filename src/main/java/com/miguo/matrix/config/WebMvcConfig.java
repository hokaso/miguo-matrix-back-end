package com.miguo.matrix.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import java.util.Collections;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2019-11-26 11:03
 */
@EnableWebMvc
@SpringBootConfiguration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter(@Autowired MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter, @Autowired ContentNegotiationManager mvcContentNegotiationManager) {
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
        requestMappingHandlerAdapter.setMessageConverters(Collections.singletonList(mappingJackson2HttpMessageConverter));
        requestMappingHandlerAdapter.setContentNegotiationManager(mvcContentNegotiationManager);
        return requestMappingHandlerAdapter;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOrigins("*")
                // 设置允许证书，不再默认开启
                .allowCredentials(true)
                // 设置允许的方法
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // 设置跨域允许时间
                .maxAge(60 * 60);
    }
}
