package guru.springframework.springmsbreweryclient.web.config;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Setting up Non-blocking custom HttpClient
 * @author kas
 */
@Profile("non-blocking-client")
@Component
public class NIORestTemplateCustomizer implements RestTemplateCustomizer {

    ClientHttpRequestFactory apacheClientHttpRequestFactory() throws IOReactorException {
        DefaultConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(IOReactorConfig
                .custom()
                .setConnectTimeout(3000)
                .setIoThreadCount(4)
                .setSoTimeout(3000) // socket timeout
                .build());
        PoolingNHttpClientConnectionManager nonBlockingConnectionManager = new PoolingNHttpClientConnectionManager(ioReactor);
        nonBlockingConnectionManager.setDefaultMaxPerRoute(100);
        nonBlockingConnectionManager.setMaxTotal(1000);

        CloseableHttpAsyncClient client = HttpAsyncClients
                .custom()
                .setConnectionManager(nonBlockingConnectionManager)
                .build();
        return new HttpComponentsAsyncClientHttpRequestFactory(client);
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        try {
            restTemplate.setRequestFactory(apacheClientHttpRequestFactory());
        } catch (IOReactorException e) {
            e.printStackTrace();
        }

    }
}
