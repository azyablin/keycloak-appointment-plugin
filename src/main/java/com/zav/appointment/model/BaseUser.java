package com.zav.appointment.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class BaseUser {

    private String username;

    private String firstName;

    private String lastName;
    
    private String email;
}
