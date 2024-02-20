package io.smartir;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailChimpRequest {
    @Email(message = "Email is not valid")
    @NotEmpty
    private String email;
}
