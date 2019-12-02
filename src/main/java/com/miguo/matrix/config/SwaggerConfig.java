package com.miguo.matrix.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 功能描述：SwaggerUI配置类
 * @author Hocassian
 * @Date 2019/11/26 10:21
 */
@EnableSwagger2
@SpringBootConfiguration
public class SwaggerConfig {
    /**
     * 是否启动文档标识
     */
    private Boolean enableSwagger = Boolean.FALSE;
    private Environment environment;
    /**
     * 功能描述：注入环境变量
     * @author Hocassian
     * @Date 2019/11/26 10:21
     */
    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        /* 通过启动的环境变量决定是否启用Swagger-UI文档 */
        Profiles profiles = Profiles.of("dev", "main");
        enableSwagger = environment.acceptsProfiles(profiles);
    }
    @Bean("accountApi")
    public Docket accountApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("员工数据接口")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.regex("/account.*"))
                .build()
                .apiInfo(apiInfo());
    }

    @Bean("adminApi")
    public Docket adminApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("登陆接口")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.regex("/admin.*"))
                .build()
                .apiInfo(apiInfo());
    }
    /**
     * 功能描述：SwaggerUI文档的基本信息
     * @author Hocassian
     * @Date 2019/11/26 10:21
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("数据一览")
                .description("所有的接口都在这里了~")
                .termsOfServiceUrl("http://localhost:9090")
                .contact(new Contact("Hocassian", "https://Hocassian.cn", "hokaso@qq.com"))
                .version("v1.0.1")
                .build();
    }

}