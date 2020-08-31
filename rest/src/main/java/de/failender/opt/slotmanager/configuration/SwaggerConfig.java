package de.failender.opt.slotmanager.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.configuration.ObjectMapperConfigured;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final ObjectMapper objectMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public SwaggerConfig(ObjectMapper objectMapper, ApplicationEventPublisher applicationEventPublisher) {
        this.objectMapper = objectMapper;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Bean
    public Docket api() {
        applicationEventPublisher.publishEvent(new ObjectMapperConfigured(this, objectMapper));
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "REST API for OPTs Slotmanager",
                "Documentation for the REST API of the OPT Slotmanager",
                "1",
                "",
                "pahilgenstock@hotmail.de",
                "",
                ""
        );
        return apiInfo;
    }
}
