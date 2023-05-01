package com.zav.appointment.model;

import org.keycloak.models.SubjectCredentialManager;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;


public class User extends AbstractUserAdapterFederatedStorage {

    private String id;

    private String username;

    public User(StorageInfo storageInfo) {
        super(storageInfo.ksession(), storageInfo.realm(), storageInfo.model());
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public SubjectCredentialManager credentialManager() {
        return null;
    }


    public void setId(Long id) {
        this.id = StorageId.keycloakId(storageProviderModel, id.toString());
    }

    @Override
    public String getId() {
        return id;
    }

}
