package guru.springframework.springmsbreweryclient.web.config;

import lombok.Setter;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@ConfigurationProperties(value = "resttemplate") // Spring uses setter injection (required setters for work)
@Setter
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {

    private Integer maxTotalConnections;
    private Integer defaultMaxPerRoute;
    private Integer connectionRequestTimeout;
    private Integer socketTimeout;

    ClientHttpRequestFactory apacheClientHttpRequestFactory() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxTotalConnections);
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);

        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(connectionRequestTimeout) // if request proceed more than 3000 then Fail
                .setSocketTimeout(socketTimeout)
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
