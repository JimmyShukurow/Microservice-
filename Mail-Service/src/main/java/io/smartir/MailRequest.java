package io.smartir;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MailRequest {
    private String name;
    private String surname;
    private String email;
    private String selectedCountry;
    private String phoneNumber;
    private String message;
}
