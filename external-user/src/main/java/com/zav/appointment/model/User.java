package com.zav.appointment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class User extends BaseUser {

    private String id;


}
