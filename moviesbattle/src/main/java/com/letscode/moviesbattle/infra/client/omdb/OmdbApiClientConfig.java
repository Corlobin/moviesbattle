package com.letscode.moviesbattle.infra.client.omdb;

import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.concurrent.TimeUnit;

@Configuration
public class OmdbApiClientConfig {

    public static final String URL = "http://www.omdbapi.com/";

    @Bean
    @Lazy
    public OmdbApiClient omdbApiClient() {
        return Feign.builder()
            .client(new ApacheHttpClient())
            .encoder(new JacksonEncoder())
            .decoder(new JacksonDecoder())
            .options(new Request.Options(2, TimeUnit.SECONDS, 2, TimeUnit.SECONDS, true))
            .errorDecoder(new ErrorDecoder.Default())
            .logLevel(Logger.Level.FULL)
            .retryer(Retryer.NEVER_RETRY)
            .target(OmdbApiClient.class, URL);
    }
}
