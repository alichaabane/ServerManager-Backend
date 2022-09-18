package com.myApplication.server;

import com.myApplication.server.enumeration.Status;
import com.myApplication.server.model.Server;
import com.myApplication.server.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ServerRepository serverRepository, Environment env) {
        return args -> {
            serverRepository.save(new Server(null,
                    "192.168.1.160",
                    "ubuntu Linux",
                    "16 GB",
                    "Personal PC",
                    "http://localhost:" + env.getProperty("server.port") + "/server/image/server1.png",
                    Status.SERVER_UP));

            serverRepository.save(new Server(null, "192.168.1.58",
                    "Fedora Linux",
                    "16 GB", "Dell Tower",
                    "http://localhost:" + env.getProperty("server.port") + "/server/image/server2.png",
                    Status.SERVER_DOWN));
            serverRepository.save(new Server(null, "192.168.1.21",
                    "MS 2008",
                    "32 GB",
                    "Web Server",
                    "http://localhost:" + env.getProperty("server.port") + "/server/image/server3.png",
                    Status.SERVER_UP));
            serverRepository.save(new Server(null, "192.168.1.14",
                    "Red Hat Enterprise Linux",
                    "64 GB", "Mail Server",
                    "http://localhost:" + env.getProperty("server.port") + "/server/image/server4.png",
                    Status.SERVER_DOWN));
        };
    }
}
