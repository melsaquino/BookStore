package com.example.demo.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class UserDTO {

    // standard getters and setters
    @Getter
    @Setter
    @NotNull
    @NotEmpty
    private String email;

    private int id;

    public UserDTO(int id, String email){
        this.id = id;
        this.email =email;
    }


}