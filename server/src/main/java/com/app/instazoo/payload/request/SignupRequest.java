package com.app.instazoo.payload.request;

import com.app.instazoo.annotations.PasswordMatches;
import com.app.instazoo.annotations.ValidEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@PasswordMatches
public class SignupRequest {

    @Email(message = "Username needs to be an email")
    @NotBlank(message = "User email is required")
    @ValidEmail
    private String email;
    @NotEmpty(message = "Please enter your name")
    private String firstname;
    @NotEmpty(message = "Please enter your lastname")
    private String lastname;
    @NotEmpty(message = "Please create username")
    private String username;
    @NotEmpty(message = "Password is required")
    private String password;
    private String confirmPassword;

}
