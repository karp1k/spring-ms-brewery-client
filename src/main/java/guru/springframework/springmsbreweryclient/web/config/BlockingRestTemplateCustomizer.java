package guru.springframework.springmsbreweryclient.web.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Blocking HttpClient
 * @author kas
 */
@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {

    ClientHttpRequestFactory apacheClientHttpRequestFactory() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(100);
        connectionManager.setDefaultMaxPerRoute(20);

        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(3000) // if request proceed more than 3000 then Fail
                .setSocketTimeout(3000)
                .build();

        CloseableHttpClient client = HttpClients
                .custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig)
                .build();
        return new HttpComponentsClientHttpRequestFactory(client); // apache impl of ClientHttpRequestFactory
    }


    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(apacheClientHttpRequestFactory());
    }
}
