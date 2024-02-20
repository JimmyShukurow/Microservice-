package io.smartir.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Email(message = "Email is not valid")
    @NotEmpty
    private String email;
    private String password;
    private String confirmPassword;
}
