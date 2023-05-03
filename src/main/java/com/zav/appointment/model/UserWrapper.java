package com.zav.appointment.model;

import com.zav.appointment.domain.User;
import org.keycloak.models.SubjectCredentialManager;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;


public class UserWrapper extends AbstractUserAdapterFederatedStorage {

    private final User user;

    public UserWrapper(StorageInfo storageInfo, User user) {
        super(storageInfo.ksession(), storageInfo.realm(), storageInfo.model());
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public void setUsername(String username) {
        this.user.setUsername(username);
    }

    @Override
    public String getId() {
        return StorageId.keycloakId(storageProviderModel, user.getId().toString());
    }
}
