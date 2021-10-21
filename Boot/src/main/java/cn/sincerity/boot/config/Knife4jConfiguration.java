package cn.sincerity.boot.config;

import cn.sincerity.common.swagger.ApiVersionConstant;
import cn.sincerity.common.swagger.ApiVersion;
import cn.sincerity.common.swagger.Knife4jConfigurator;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Arrays;

/**
 * Description：Knife4j 配置类
 * Create by Ht7_Sincerity on 2021/10/19
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration extends Knife4jConfigurator {

    @Bean
    public Docket apiV1000 (){
        return super.versionDocket(ApiVersionConstant.V1000);
    }
}
