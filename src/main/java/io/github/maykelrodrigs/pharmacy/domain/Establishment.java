package io.github.maykelrodrigs.pharmacy.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Document(collection = "establishment")
@Data
@Builder
public class Establishment {

    @Id
    private String id;

    @NotBlank
    private String name;

}
