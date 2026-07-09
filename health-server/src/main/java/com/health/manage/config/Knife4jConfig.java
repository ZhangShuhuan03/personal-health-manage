package com.health.manage.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j / OpenAPI 文档配置
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("个人健康管理系统 API")
                        .description("健康档案 CRUD + AI 健康分析")
                        .version("1.0.0")
                        .contact(new Contact().name("Health Manage").email("health@example.com")));
    }
}
