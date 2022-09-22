package com.myApplication.server;

import com.myApplication.server.enumeration.Status;
import com.myApplication.server.model.Server;
import com.myApplication.server.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ServerRepository serverRepository, Environment env) {
        return args -> {
//            serverRepository.save(new Server(null,
//                    "192.168.1.160",
//                    "ubuntu Linux",
//                    16.0,
//                    "Personal PC",
//                    "http://localhost:" + env.getProperty("server.port") + "/server/image/server1.png",
//                    Status.SERVER_UP));
//
//            serverRepository.save(new Server(null, "192.168.1.58",
//                    "Fedora Linux",
//                    16.5, "Dell Tower",
//                    "http://localhost:" + env.getProperty("server.port") + "/server/image/server2.png",
//                    Status.SERVER_DOWN));
//            serverRepository.save(new Server(null, "192.168.1.21",
//                    "MS 2008",
//                    32.0,
//                    "Web Server",
//                    "http://localhost:" + env.getProperty("server.port") + "/server/image/server3.png",
//                    Status.SERVER_UP));
//            serverRepository.save(new Server(null, "192.168.1.14",
//                    "Red Hat Enterprise Linux",
//                    64.0, "Mail Server",
//                    "http://localhost:" + env.getProperty("server.port") + "/server/image/server4.png",
//                    Status.SERVER_DOWN));
        };
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Filename"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
