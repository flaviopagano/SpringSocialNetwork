package co.develope.SpringSocialNetwork.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfo(
                        "Social Network project - Team 4",
                        "A complete social network",
                        "1.0.0",
                        "http://en.wikipedia.org/wiki/MIT_License",
                        new Contact("Gaia Zanchi","https://github.com/gaiazanchi" ,"gaia.zanchi2001@gmail.com"),
                        //new Contact("Alma Caciula Negrea","https://github.com/AlmaCN", "almacaciulanegrea@gmail.com"),
                        //new Contact("Aldo Mancuso","https://github.com/Aldomancuso97","mancusoaldo8@gmail.com"),
                        //new Contact("Flavio Pagano", "https://github.com/flaviopagano", "pagano.flavio@outlook.com"),
                        //new Contact("Alessandro Gobetti", "https://github.com/alegbt", "alessandrogbt94@gmail.com"),
                        "MIT",
                        "http://en.wikipedia.org/wiki/MIT_License",
                        Collections.emptyList()
                )).tags(
                        new Tag("default-controller", "The default controller"),
                        new Tag("user-controller","The user controller"),
                        new Tag("post-controller","The post controller"),
                        new Tag("comment-controller","The comment controller"),
                        new Tag("reaction-controller","The reaction controller")
                );
    }

}
