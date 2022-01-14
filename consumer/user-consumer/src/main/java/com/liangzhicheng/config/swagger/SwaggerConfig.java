package com.liangzhicheng.config.swagger;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.liangzhicheng.config.swagger.properties.SwaggerProperties;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import javax.annotation.Resource;

/**
 * swagger接口文档配置
 * @author liangzhicheng
 */
@EnableSwagger2
@EnableSwaggerBootstrapUI
@Configuration
public class SwaggerConfig {

    @Resource
    private Environment env;
    @Resource
    private SwaggerProperties properties;

    @Bean
    public Docket docket() {
        //设置Api接口文档在环境中显示
        Profiles profiles = Profiles.of("dev", "beta");
        //判断当前是否处于该环境
        boolean isEnable = env.acceptsProfiles(profiles);
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(isEnable)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(properties.getApplicationName())
                .description(properties.getApplicationDescription())
                .contact(new Contact("liangzhicheng", "https://gitee.com/yichengc3", "yichengc3@163.com"))
                .version(properties.getApplicationVersion())
                .build();
    }

}
