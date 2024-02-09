package io.smartir.smartir.website.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Embeddable
public class ArticleContentsModel {

    @Column(columnDefinition = "TEXT")
    String text;
    @Column(columnDefinition = "TEXT")
    String subtitle;
}
