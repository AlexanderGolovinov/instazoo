package com.app.instazoo.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostDTO {

    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String caption;
    private String location;
    private String username;

}
