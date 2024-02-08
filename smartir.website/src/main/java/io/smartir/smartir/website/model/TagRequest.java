package io.smartir.smartir.website.model;

import io.smartir.smartir.website.entity.Type;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TagRequest {

    String name;
    Type type;
}
