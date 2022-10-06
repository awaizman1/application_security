package client_credentials;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.security.oauth2.client.*;
//import org.springframework.security.oauth2.client.registration.*;
//import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

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
                    .uri("http://localhost:8080/hello")
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(string -> "We retrieved the following resource using Client Credentials Grant Type: " + string)
                    .subscribe(LOG::info);

            Thread.sleep(5000);
        }
    }

//    @Bean
//    ClientRegistration oktaClientRegistration(
//            @Value("${spring.security.oauth2.client.provider.kc.issuer-uri}") String issuer_uri,
//            @Value("${spring.security.oauth2.client.provider.kc.token-uri}") String token_uri,
//            @Value("${spring.security.oauth2.client.registration.kc.client-id}") String client_id,
//            @Value("${spring.security.oauth2.client.registration.kc.client-secret}") String client_secret,
//            @Value("${spring.security.oauth2.client.registration.kc.scope}") List<String> scope,
//            @Value("${spring.security.oauth2.client.registration.kc.authorization-grant-type}") String authorizationGrantType
//    ) {
//        return ClientRegistration
//                .withRegistrationId("kc")
//                .issuerUri(issuer_uri)
//                .tokenUri(token_uri)
//                .clientId(client_id)
//                .clientSecret(client_secret)
//                .scope(scope)
//                .authorizationGrantType(new AuthorizationGrantType(authorizationGrantType))
//                .build();
//    }
//
//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository(ClientRegistration oktaClientRegistration) {
//        return new InMemoryClientRegistrationRepository(oktaClientRegistration);
//    }
//
//    @Bean ReactiveClientRegistrationRepository reactiveClientRegistrationRepository(ClientRegistration oktaClientRegistration) {
//        return new InMemoryReactiveClientRegistrationRepository(oktaClientRegistration);
//    }
//
//    // Create the authorized client service
//    @Bean
//    public OAuth2AuthorizedClientService auth2AuthorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
//        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
//    }
//
//    // Create the authorized client manager and service manager using the
//    // beans created and configured above
//    @Bean
//    public AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientServiceAndManager(
//            ClientRegistrationRepository clientRegistrationRepository,
//            OAuth2AuthorizedClientService authorizedClientService) {
//
//        OAuth2AuthorizedClientProvider authorizedClientProvider =
//                OAuth2AuthorizedClientProviderBuilder.builder()
//                        .clientCredentials()
//                        .build();
//
//        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
//                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
//                        clientRegistrationRepository, authorizedClientService);
//        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
//
//        return authorizedClientManager;
//    }
//
//    //    @Bean
////    WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations) {
////
////        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
////                new ServerOAuth2AuthorizedClientExchangeFilterFunction(
////                        clientRegistrations,
////                        new UnAuthenticatedServerOAuth2AuthorizedClientRepository());
////        oauth.setDefaultClientRegistrationId("bael");
////        return WebClient.builder()
////                .filter(oauth)
////                .build();
////    }
//    @Bean
//    WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations) {
//        InMemoryReactiveOAuth2AuthorizedClientService clientService = new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrations);
//        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations, clientService);
//        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
//        oauth.setDefaultClientRegistrationId("kc");
//        return WebClient.builder()
//                .filter(oauth)
//                .build();
//    }
}
