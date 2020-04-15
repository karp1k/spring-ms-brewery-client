package guru.springframework.springmsbreweryclient.web.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author kas
 */
@Getter
@Setter
@Builder
public class CustomerDto {

    private UUID id;
    private String name;
}
