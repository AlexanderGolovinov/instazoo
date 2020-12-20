package com.app.instazoo.payload.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Объект используется для логина User в систему
 */

@Data
public class LoginRequest {

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    private String password;
}
