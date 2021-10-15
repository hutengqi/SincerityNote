package cn.sincerity.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class BootApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BootApplication.class, args);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.asList(beanDefinitionNames).forEach(x -> stringBuilder.append(x).append("\n"));
        int length = context.getBeanDefinitionNames().length;
        log.trace("Spring boot启动初始化了 {} 个 Bean", length);
        log.debug("Spring boot启动初始化了 {} 个 Bean", length);
        log.info("Spring boot启动初始化了 {} 个 Bean", length);
        log.warn("Spring boot启动初始化了 {} 个 Bean", length);
        log.info("Spring boot 启动的都有{}", stringBuilder);
        log.error("Spring boot启动初始化了 {} 个 Bean", length);
    }

}
