package com.app.instazoo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
/**
 * Когда User заходит в систему, то этот объект возвращает действующий Токен.
 */

@Data
@AllArgsConstructor
public class JWTLoginSuccessResponse {

    private boolean success;
    private String token;
    private String username;
    private List<String> roles;

}
