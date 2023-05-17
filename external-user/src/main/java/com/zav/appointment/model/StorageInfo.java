package com.zav.appointment.model;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

public record StorageInfo(KeycloakSession ksession, ComponentModel model, RealmModel realm) {

}
