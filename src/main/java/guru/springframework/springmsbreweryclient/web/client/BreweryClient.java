package guru.springframework.springmsbreweryclient.web.client;

import guru.springframework.springmsbreweryclient.web.model.BeerDto;
import guru.springframework.springmsbreweryclient.web.model.CustomerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

/**
 * @author kas
 */
@Component
// ignoreUnknownFields - false Вызовет ошибку если свойство apiHost не будет задано
@ConfigurationProperties(value = "brewery", ignoreUnknownFields = false)
public class BreweryClient {

    public final String BEER_PATH_API_V1 = "/api/v1/beer";
    public final String CUSTOMER_PATH_API_V1 = "/api/v1/customer";
    private String apiHost;
    private final RestTemplate restTemplate;

    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public BeerDto getBeerById(UUID id) {
        return restTemplate.getForObject(apiHost + BEER_PATH_API_V1 + "/" + id.toString(), BeerDto.class);
    }

    public URI saveBeerDto(BeerDto dto) {
        return restTemplate.postForLocation(apiHost + BEER_PATH_API_V1, dto);
    }

    public void updateBeerDto(UUID id, BeerDto dto) {
        restTemplate.put(apiHost + BEER_PATH_API_V1 + "/" + id, dto);
    }

    public void deleteBeer(UUID id) {
        restTemplate.delete(apiHost + BEER_PATH_API_V1 + "/" + id.toString());
    }

    public CustomerDto getCustomerDto(UUID id) {
        return restTemplate.getForObject(apiHost + CUSTOMER_PATH_API_V1 + "/" + id, CustomerDto.class);
    }

    public URI saveCustomerDto(CustomerDto dto) {
        return restTemplate.postForLocation(apiHost + CUSTOMER_PATH_API_V1, dto);
    }

    public void updateCustomerDto(UUID id, CustomerDto dto) {
        restTemplate.put(apiHost + CUSTOMER_PATH_API_V1 + "/" + id, dto);
    }

    public void deleteCustomer(UUID id) {
        restTemplate.delete(apiHost + CUSTOMER_PATH_API_V1 + "/" + id);
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }
}
