package io.smartir.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
}
