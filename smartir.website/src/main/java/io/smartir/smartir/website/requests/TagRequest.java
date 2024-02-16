package io.smartir.smartir.website.requests;

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
