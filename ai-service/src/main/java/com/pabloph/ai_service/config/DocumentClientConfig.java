package com.pabloph.ai_service.config;

import com.pabloph.ai_service.client.DocumentServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class DocumentClientConfig {

    @Bean
    public DocumentServiceClient documentServiceClient(
            @Value("${services.document.url}") String documentServiceUrl
    ) {

        RestClient restClient = RestClient.builder()
                .baseUrl(documentServiceUrl)
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);

        HttpServiceProxyFactory factory =
                HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(DocumentServiceClient.class);
    }
}