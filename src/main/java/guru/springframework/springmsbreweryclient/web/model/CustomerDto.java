package guru.springframework.springmsbreweryclient.web.model;

import lombok.*;

import java.util.UUID;

/**
 * @author kas
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    private UUID id;
    private String name;
}
