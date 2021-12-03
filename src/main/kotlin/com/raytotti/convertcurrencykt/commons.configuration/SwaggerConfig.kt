package com.raytotti.convertcurrencykt.commons.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.raytotti.convertcurrencykt"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(ApiInfoBuilder()
            .title("Convert Currency")
            .description("API Rest that is able to perform a conversion between two currencies using updated rates from an external service.")
            .version("1.0.0")
            .contact(Contact("Ray Toti Felix de Araujo", "https://github.com/raytotti/convertcurrencykt", "raytottifa@hotmail.com"))
            .build())
}