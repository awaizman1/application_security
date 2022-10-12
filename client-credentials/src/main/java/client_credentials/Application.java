package client_credentials;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private static Logger LOG = LoggerFactory
            .getLogger(Application.class);

    @Autowired
    private WebClient webClient;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        while (true) {
            webClient.get()
                    .uri("http://localhost:8080/pets")
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(string -> "Got the following pets using client credentials Grant Type: " + string)
                    .subscribe(LOG::info);

            Thread.sleep(5000);
        }
    }
}
