package io.smartir;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailChimpRequestDTO {
    private String email_address;
    private String status;
    private List<String> tags;
}
