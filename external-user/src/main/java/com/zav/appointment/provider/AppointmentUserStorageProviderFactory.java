package com.zav.appointment.provider;

import java.util.List;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.storage.UserStorageProviderFactory;

import static com.zav.appointment.ConfigConstant.CONFIG_KEY_JDBC_DRIVER;

public class AppointmentUserStorageProviderFactory implements UserStorageProviderFactory<AppointmentUserStorageProvider> {


    protected final List<ProviderConfigProperty> configMetadata;

    public AppointmentUserStorageProviderFactory() {
        configMetadata = ProviderConfigurationBuilder.create()
                .property()
                .name(CONFIG_KEY_JDBC_DRIVER.name())
                .label("JDBC Driver Class")
                .type(ProviderConfigProperty.STRING_TYPE)
                .defaultValue("com.mysql.jdbc.Driver")
                .helpText("Fully qualified class name of the JDBC driver")
                .add()
                .build();
    }

    @Override
    public AppointmentUserStorageProvider create(KeycloakSession session, ComponentModel model) {
        return new AppointmentUserStorageProvider(session, model);
    }

    @Override
    public String getId() {
        return "appointment-user-storage-provider";
    }


}
