package com.cyyaw;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


@Configuration
@EnableSwagger2WebMvc
@EnableKnife4j
@ConditionalOnProperty(
        name = {"htp.msb.swagger.enable"},
        havingValue = "true",
        matchIfMissing = true
)
public class SwaggerAutoConfiguration {
    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

    public SwaggerAutoConfiguration() {
    }

    @Bean
    public Docket defaultApi() {
        Docket docket = (new Docket(DocumentationType.SWAGGER_2)).apiInfo(this.apiInfo()).select().apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).build();
        return docket;
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("ICE-CLOUD", "", "");
        return (new ApiInfoBuilder()).title("接口描述").description("接口描述").contact(contact).version("1.0").build();
    }
}