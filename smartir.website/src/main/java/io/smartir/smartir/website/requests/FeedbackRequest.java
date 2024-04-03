package io.smartir.smartir.website.requests;

import io.smartir.smartir.website.entity.Type;
import jakarta.persistence.Column;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FeedbackRequest {
     String email;

    @Column(columnDefinition = "TEXT")
     String feedback;
}
