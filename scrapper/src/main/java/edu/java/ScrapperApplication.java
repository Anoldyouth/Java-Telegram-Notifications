package edu.java;

import edu.java.configuration.ApplicationConfig;
import edu.java.configuration.GitHubConfig;
import edu.java.configuration.StackOverflowConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({ApplicationConfig.class, GitHubConfig.class, StackOverflowConfig.class})
public class ScrapperApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScrapperApplication.class, args);
    }
}
