package io.smartir.smartir.website.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Embeddable
public class ArticleContentsModel {

    String text;
    String subtitle;
}
