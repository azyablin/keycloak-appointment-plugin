package com.zav.appointment.adapter;

import com.zav.appointment.dao.RoleDao;
import com.zav.appointment.model.BaseUser;
import com.zav.appointment.model.User;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.experimental.Delegate;
import lombok.val;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

public class UserAdapter extends AbstractUserAdapterFederatedStorage {

    @Delegate
    private final BaseUser user;

    public UserAdapter(User user, KeycloakSession session, RealmModel realm, ComponentModel storageProviderModel) {
        super(session, realm, storageProviderModel);
        this.user = user;
    }

    @Override
    public String getId() {
        return StorageId.keycloakId(storageProviderModel, getInternalId());
    }

    public String getInternalId() {
        return ((User) user).getId();
    }

    @Override
    protected Set<RoleModel> getRoleMappingsInternal() {
        val result = getRealmRoleMappingsStream().collect(Collectors.toSet());
        return result;
    }

    @Override
    public Stream<RoleModel> getRealmRoleMappingsStream() {
        val names = RoleDao.getNamesByUserId(getInternalId());
        return names.stream()
                .map(name -> session.roles().getRealmRole(realm, name))
                .filter(rm -> rm != null);
    }

}
