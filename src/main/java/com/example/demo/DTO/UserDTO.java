package com.example.demo.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class UserDTO {
    @NotNull
    @NotEmpty
    @Setter
    @Getter
    private String password;
    private String matchingPassword;

    // standard getters and setters
    @Getter
    @Setter
    @NotNull
    @NotEmpty
    private String email;


}