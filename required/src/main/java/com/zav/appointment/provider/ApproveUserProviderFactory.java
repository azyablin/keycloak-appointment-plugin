package com.zav.appointment.provider;

import org.keycloak.Config;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class ApproveUserProviderFactory implements RequiredActionFactory {

    private static final ApproveUserProvider APPROVE_USER_PROVIDER = new ApproveUserProvider();

    @Override
    public String getDisplayText() {
        return "User must be approved";
    }

    @Override
    public RequiredActionProvider create(KeycloakSession session) {
        return APPROVE_USER_PROVIDER;
    }

    @Override
    public void init(Config.Scope config) {

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "APPROVE_USER_PROVIDER";
    }
}
