package com.zav.appointment.provider;

import org.keycloak.Config;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

public class AppointmentUserStorageProviderFactory implements UserStorageProviderFactory<AppointmentUserStorageProvider> {
    
    @Override
    public AppointmentUserStorageProvider create(KeycloakSession session, ComponentModel model) {
        return new AppointmentUserStorageProvider(session, model);
    }

    @Override
    public String getId() {
        return "appointment-user-storage-provider";
    }

    @Override
    public void init(Config.Scope config) {
        UserStorageProviderFactory.super.init(config);
    }
}
