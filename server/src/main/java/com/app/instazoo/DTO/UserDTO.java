package com.app.instazoo.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {

    @NotEmpty
    private String name;
    @NotEmpty
    private String lastname;
    @NotEmpty
    private String username;
    private String bio;

}
