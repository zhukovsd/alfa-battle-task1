package com.zhukovsd.alfabattle.task1;

import com.zhukovsd.alfabattle.task1.atms.AtmController;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate getRestTemplate() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        char[] password = "password".toCharArray();
        InputStream jksInputStream = AtmController.class.getClassLoader().getResourceAsStream("alfabattle.jks");
        keyStore.load(jksInputStream, password);
        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, password)
                .build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSslcontext(sslContext)
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        return new RestTemplate(requestFactory);
    }

}
