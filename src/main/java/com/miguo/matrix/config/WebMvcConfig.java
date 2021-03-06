package com.miguo.matrix.config;

import com.miguo.matrix.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private UserInterceptor userInterceptor;


    @Value("${file.path}")
    private String filePath;

    private static final String WINDOWS = "win";

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
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(userInterceptor).addPathPatterns("/staff/**");
        registry.addInterceptor(userInterceptor).addPathPatterns("/staff_miniprogram/**");
        registry.addInterceptor(userInterceptor).addPathPatterns("/admin/**");
        registry.addInterceptor(userInterceptor).addPathPatterns("/count/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

        /* 区分系统环境、自定义上传映射路径 */
        String os = System.getProperty("os.name");
        // 如果是Windows系统
        if (os.toLowerCase().startsWith(WINDOWS)) {
            registry.addResourceHandler("/file/**")
                    .addResourceLocations("file:D:/File/test/");
        } else { // linux 和mac
            registry.addResourceHandler("/file/**")
                    .addResourceLocations("file:./file/");
        }
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
