package com.luckydashboard.dashboard.task.manager;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000","https://lucky-task-manager-awfk71tmw-davidaholliday.vercel.app","https://lucky-task-manager.vercel.app/") // Update with your frontend origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}