package io.smartir.smartir.website.requests;

import lombok.*;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticleFilterRequest {
    private Collection<Integer> tagID;
    private String search;
    private int page;
    private int size;
}
