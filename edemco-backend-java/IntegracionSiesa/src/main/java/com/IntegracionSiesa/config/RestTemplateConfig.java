package com.IntegracionSiesa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

/**
 * Configuración para definir beans relacionados con la comunicación HTTP.
 */
@Configuration
public class RestTemplateConfig {

    @Value("${api.connikey}")
    private String connikey;

    @Value("${api.conniToken}")
    private String conniToken;

    /**
     * Bean para el cliente HTTP RestTemplate.
     *
     * @return Una instancia de RestTemplate para realizar peticiones HTTP.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Bean para configurar los encabezados HTTP utilizados en las peticiones.
     *
     * @return Una instancia de HttpHeaders con los valores de "Connikey" y "ConniToken" configurados.
     */
    @Bean
    public HttpHeaders httpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Connikey", connikey);
        headers.set("ConniToken", conniToken);
        return headers;
    }
}
