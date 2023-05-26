package com.zav.appointment.provider;

import com.zav.appointment.adapter.UserAdapter;
import com.zav.appointment.dao.UserDao;
import com.zav.appointment.datasource.TomcatDatasource;
import java.util.Map;
import java.util.stream.Stream;
import lombok.val;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;

public class AppointmentUserStorageProvider implements UserStorageProvider,
        UserLookupProvider,
        CredentialInputValidator,
        UserQueryProvider {

    private final KeycloakSession session;

    private final ComponentModel model;

    public AppointmentUserStorageProvider(KeycloakSession session, ComponentModel model) {
        this.session = session;
        this.model = model;
        TomcatDatasource.setConfig(model.getConfig());
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return PasswordCredentialModel.TYPE.endsWith(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        return supportsCredentialType(credentialType);
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
        //TODO реализовать
        return true;
    }

    @Override
    public void close() {

    }

    @Override
    public UserModel getUserById(RealmModel realm, String id) {
        String userId = StorageId.externalId(id);
        val user = UserDao.getById(userId);
        return new UserAdapter(user, session, realm, model);
    }

    @Override
    public UserModel getUserByUsername(RealmModel realm, String username) {
        return UserDao.getByName(username)
                .map(u -> new UserAdapter(u, session, realm, model))
                .orElse(null);
    }

    @Override
    public UserModel getUserByEmail(RealmModel realm, String email) {
        return null;
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, String search, Integer firstResult, Integer maxResults) {
        return UserDao.search(search, firstResult, maxResults).stream().map(user -> new UserAdapter(user, session, realm, model));
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, Map<String, String> params, Integer firstResult, Integer maxResults) {
        return UserDao.search(null, firstResult, maxResults).stream().map(user -> new UserAdapter(user, session, realm, model));
    }

    @Override
    public Stream<UserModel> getGroupMembersStream(RealmModel realm, GroupModel group, Integer firstResult, Integer maxResults) {
        return null;
    }

    @Override
    public Stream<UserModel> searchForUserByUserAttributeStream(RealmModel realm, String attrName, String attrValue) {
        return null;
    }

    @Override
    public int getUsersCount(RealmModel realm, boolean includeServiceAccount) {
        return UserDao.getUsersCount(null);
    }

    @Override
    public Stream<UserModel> getRoleMembersStream(RealmModel realm, RoleModel role, Integer firstResult, Integer maxResults) {
        return UserDao.findByRoleName(role.getName(), firstResult, maxResults).stream().map(user -> new UserAdapter(user, session, realm, model));
    }

    @Override
    public int getUsersCount(RealmModel realm, String search) {
        return UserDao.getUsersCount(search);
    }
}
