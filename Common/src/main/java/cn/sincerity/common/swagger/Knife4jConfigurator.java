package cn.sincerity.common.swagger;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Swagger 配置父类
 * Created by Ht7_Sincerity on 2021/10/21.
 */
public class Knife4jConfigurator {

    @Autowired
    private Environment environment;

    /**
     * 根据版本号创建不同的 Docket 对象
     *
     * @param version 版本号
     * @return Docket
     */
    protected Docket versionDocket(String version) {
        return this.createDocket(version, input -> {
            Optional<ApiVersion> apiVersion = input.findAnnotation(ApiVersion.class);
            Optional<ApiOperation> apiOperation = input.findAnnotation(ApiOperation.class);
            if (!apiVersion.isPresent() || !apiOperation.isPresent()) {
                return false;
            }
            List<String> versions = Arrays.asList(apiVersion.get().group());
            return versions.contains(version) || versions.contains(ApiVersionConstant.ALL);
        });
    }

    /**
     * 根据 ApiVersionConstant 和 自定义 RequestHandler 生成 Docket 对象
     *
     * @param groupName 版本号组名
     * @param selector  自定义的 Predicate<RequestHandler>
     * @return Docket 对象
     */
    private Docket createDocket(String groupName, Predicate<RequestHandler> selector) {
        List<Parameter> parameters = new ArrayList<>();
        addParameters(parameters);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName(groupName)
                .select()
                .apis(selector)
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters)
                .pathMapping(pathPrefix());
                // enable(isProdEnvironment) 出错，原因应该是 environment 注入失败。
    }

    /**
     * 返回当前模块的路径前缀
     *
     * @return 前缀
     */
    protected String pathPrefix() {
        return "/";
    }

    /**
     * Header 中的 Ticket 的非必填参数
     *
     * @param parameters List<Parameter>
     */
    protected void addParameters(List<Parameter> parameters) {
        // 子类自定义参数规则
    }

    /**
     * 默认的 Api 信息
     *
     * @return ApiInfo
     */
    protected ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SincerityNote-Boot")
                .description("SincerityNote-Boot 的接口文档")
                .version("1.0")
                .build();
    }

    /**
     * 检查当前环境是否是正式环境
     *
     * @return boolean
     */
    private boolean isProdEnvironment() {
        String[] activeProfiles = environment.getActiveProfiles();
        for (String activeProfile : activeProfiles) {
            return activeProfile.contains("prod");
        }
        return false;
    }
}
