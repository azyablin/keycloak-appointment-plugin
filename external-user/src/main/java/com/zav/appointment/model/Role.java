package com.zav.appointment.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Role {

    private String name;

    private String description;

    private String id;

    private boolean composite;

    private boolean clientRole;

}
