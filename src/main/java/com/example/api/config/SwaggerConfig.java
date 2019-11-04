package com.example.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket detalheApi() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        docket
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.api"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.informacoesApi().build());

        return docket;
    }

    private ApiInfoBuilder informacoesApi() {

        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();

        apiInfoBuilder.title("Tokyo Test");
        apiInfoBuilder.description("Api Tokyo.");
        apiInfoBuilder.version("1.0");
        apiInfoBuilder.termsOfServiceUrl("Termo de uso: TESTE.");
        apiInfoBuilder.license("Licença - Open Source");
        apiInfoBuilder.contact(this.contato());

        return apiInfoBuilder;

    }

    private Contact contato() {

        return new Contact(
                "Fernando Santos ",
                null,
                "fernandosantos.jr@gmail.com");
    }
}