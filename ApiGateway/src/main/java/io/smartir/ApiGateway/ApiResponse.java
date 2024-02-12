package io.smartir.ApiGateway;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ApiResponse {
    private long id;
    private String name;
    private String email;
    private List<String> roles;

}
