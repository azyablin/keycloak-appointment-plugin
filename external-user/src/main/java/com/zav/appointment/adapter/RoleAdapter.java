package com.zav.appointment.adapter;

import com.zav.appointment.model.Role;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.keycloak.models.RoleContainerModel;
import org.keycloak.models.RoleModel;

@RequiredArgsConstructor
public class RoleAdapter implements RoleModel {

    @Delegate
    private final Role role;

    @Override
    public void addCompositeRole(RoleModel role) {

    }

    @Override
    public void removeCompositeRole(RoleModel role) {

    }

    @Override
    public Stream<RoleModel> getCompositesStream(String search, Integer first, Integer max) {
        return null;
    }

    @Override
    public String getContainerId() {
        return null;
    }

    @Override
    public RoleContainerModel getContainer() {
        return null;
    }

    @Override
    public boolean hasRole(RoleModel role) {
        return false;
    }

    @Override
    public void setSingleAttribute(String name, String value) {

    }

    @Override
    public void setAttribute(String name, List<String> values) {

    }

    @Override
    public void removeAttribute(String name) {

    }

    @Override
    public Stream<String> getAttributeStream(String name) {
        return null;
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        return null;
    }
}
