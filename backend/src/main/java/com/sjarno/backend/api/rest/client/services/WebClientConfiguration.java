package com.sjarno.backend.api.rest.client.services;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.sjarno.backend.api.rest.client.configurations.EndpointConfiguration;
import com.sjarno.backend.api.rest.client.exceptions.HeroServiceException;
import com.sjarno.backend.models.responses.HeroDto;
import com.sjarno.backend.models.responses.HeroServiceResponse;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Log4j2
@Configuration
public class WebClientConfiguration {

    private final WebClient webClient;
    // private static ObjectMapper mapper;

    public WebClientConfiguration(
            WebClient.Builder webClientBuilder,
            EndpointConfiguration endpointConfiguration) {

        String baseUrl = endpointConfiguration.getHeroServiceBaseUrl();

        this.webClient = this.initWebclient(baseUrl, webClientBuilder);
    }

    /**
     * Webclient can be configured here, for ex. default headers, cookies etc.
     * 
     * @param baseUrl
     * @param webClientBuilder
     * @return WebClient
     */
    private WebClient initWebclient(String baseUrl,
            WebClient.Builder webClientBuilder) {
        // TODO: create a properties file
        HttpClient httpClient = HttpClient.create()
                // set connection timeout
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                // configure response timeout
                .responseTimeout(Duration.ofMillis(5000))
                // set read and write timeouts
                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

        return webClientBuilder
                .filter(customErrorHandler())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(baseUrl).build();
    }

    /* Get response from hero service */
    public <T> Mono<HeroServiceResponse<T>> getHeroesResponse(String endpoint) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(endpoint)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> handleResponse(response));
    }

    public <T> Mono<HeroServiceResponse<T>> updateHero(String endpoint, HeroDto heroDto) {
        return this.webClient.put()
                .uri(endpoint)
                // .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(heroDto), HeroDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> handleResponse(response));
    }

    public <T> Mono<HeroServiceResponse<T>> createHero(String endpoint, HeroDto heroDto) {
        return this.webClient.post()
                .uri(endpoint)
                .body(Mono.just(heroDto), HeroDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> handleResponse(response));
    }

    public <T> Mono<HeroServiceResponse<T>> deleteHero(String endpoint) {
        return this.webClient.delete()
                .uri(endpoint)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> handleResponse(response));
    }

    public <T> Mono<HeroServiceResponse<T>> findHeroesByName(String endpoint, String name) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(endpoint)
                        .queryParam("name", name)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> handleResponse(response));
    }

    private <T> Mono<HeroServiceResponse<T>> handleResponse(ClientResponse response) {
        if (response.statusCode().is2xxSuccessful()) {
            // used for list
            // return response.bodyToMono(new
            // ParameterizedTypeReference<HeroServiceResponse<?>>() {});
            return response.bodyToMono(HeroServiceResponse.class);
        }
        return response.createException().flatMap(errorBody -> Mono.error(new Exception(errorBody)));
    }

    public static ExchangeFilterFunction customErrorHandler() {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            // TODO: create custom error handling
            if (response.statusCode().is4xxClientError()) {
                log.error("Client side error occured, statuscode == " + response.statusCode());
                return response.createException().flatMap(errorBody -> {
                    return Mono.error(new HeroServiceException("Error eccured during call to hero service") {
                        {
                            setWebClientResponseException(errorBody);
                        }
                    });
                });
            }
            if (response.statusCode().is5xxServerError()) {
                log.error("Internal server error occured, statuscode == " + response.statusCode());
                return response.createException().flatMap(errorBody -> {
                    return Mono.error(new Exception("Status code 500 here") {
                        {
                            // setWebClientResponseException(errorBody);
                        }
                    });
                });
            }
            return Mono.just(response);
        });
    }

}
