package guru.springframework.springmsbreweryclient.web.client;

import guru.springframework.springmsbreweryclient.web.model.BeerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BreweryClientTest {

    @Autowired
    BreweryClient breweryClient;

    BeerDto beerDto;

    @BeforeEach
    void setUp() {
        beerDto = BeerDto.builder().beerName("Test").build();
    }

    @Test
    void getBreweryId() {
        BeerDto dto = breweryClient.getBeerById(UUID.randomUUID());
        assertNotNull(dto);
    }

    @Test
    void saveBeer() {

        URI uri = breweryClient.saveBeerDto(beerDto);
        assertNotNull(uri);
        System.out.println(uri.toString());

    }

    @Test
    void updateBeerDto() {
        breweryClient.updateBeerDto(UUID.randomUUID(), beerDto);

    }

    @Test
    void deleteBeer() {
        breweryClient.deleteBeer(UUID.randomUUID());
    }
}