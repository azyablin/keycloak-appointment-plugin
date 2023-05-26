package com.zav.appointment.provider;

import java.util.List;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.storage.UserStorageProviderFactory;

import static com.zav.appointment.ConfigConstant.APPOINTMENT_DB_DRIVER_CLASS_NAME;
import static com.zav.appointment.ConfigConstant.APPOINTMENT_DB_PASSWORD;
import static com.zav.appointment.ConfigConstant.APPOINTMENT_DB_POOL_INITIAL_SIZE;
import static com.zav.appointment.ConfigConstant.APPOINTMENT_DB_POOL_MAX_ACTIVE;
import static com.zav.appointment.ConfigConstant.APPOINTMENT_DB_POOL_MAX_IDLE;
import static com.zav.appointment.ConfigConstant.APPOINTMENT_DB_POOL_MIN_IDLE;
import static com.zav.appointment.ConfigConstant.APPOINTMENT_DB_URL;
import static com.zav.appointment.ConfigConstant.APPOINTMENT_DB_USER;

public class AppointmentUserStorageProviderFactory implements UserStorageProviderFactory<AppointmentUserStorageProvider> {


    protected final List<ProviderConfigProperty> configMetadata;

    public AppointmentUserStorageProviderFactory() {
        configMetadata = ProviderConfigurationBuilder.create()
                .property()
                .name(APPOINTMENT_DB_DRIVER_CLASS_NAME.name())
                .label("JDBC Driver Class")
                .type(ProviderConfigProperty.STRING_TYPE)
                .defaultValue("com.mysql.jdbc.Driver")
                .helpText("Fully qualified class name of the JDBC driver")
                .add()
                .property()
                .name(APPOINTMENT_DB_URL.name())
                .label("JDBC URL")
                .type(ProviderConfigProperty.STRING_TYPE)
                .defaultValue("jdbc:mysql://127.0.0.1:3306/appointments?currentSchema=appointments")
                .helpText("JDBC URL for database")
                .add()
                .property()
                .name(APPOINTMENT_DB_USER.name())
                .label("User")
                .type(ProviderConfigProperty.STRING_TYPE)
                .defaultValue("user")
                .helpText("User name for database")
                .add()
                .property()
                .name(APPOINTMENT_DB_PASSWORD.name())
                .label("Password")
                .type(ProviderConfigProperty.PASSWORD)
                .defaultValue("07831505")
                .helpText("Password for current user")
                .add()
                .property()
                .name(APPOINTMENT_DB_POOL_INITIAL_SIZE.name())
                .label("Initial size")
                .type(ProviderConfigProperty.STRING_TYPE)
                .defaultValue("5")
                .helpText("The number of connections that will be established when the connection pool is started. ")
                .add()
                .property()
                .name(APPOINTMENT_DB_POOL_MAX_ACTIVE.name())
                .label("Max active")
                .type(ProviderConfigProperty.STRING_TYPE)
                .defaultValue("50")
                .helpText("The maximum number of active connections that can be allocated from this pool at the same time.")
                .add()
                .property()
                .name(APPOINTMENT_DB_POOL_MAX_IDLE.name())
                .label("Max idle")
                .type(ProviderConfigProperty.STRING_TYPE)
                .defaultValue("5")
                .helpText("The maximum number of connections that should be kept in the idle pool.")
                .add()
                .property()
                .name(APPOINTMENT_DB_POOL_MIN_IDLE.name())
                .label("Min idle")
                .type(ProviderConfigProperty.STRING_TYPE)
                .defaultValue("2")
                .helpText("The minimum number of established connections that should be kept in the pool at all times. ")
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

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configMetadata;
    }


}
