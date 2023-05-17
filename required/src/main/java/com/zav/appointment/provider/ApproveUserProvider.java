package com.zav.appointment.provider;

import java.util.Date;
import java.util.List;
import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.UserModel;

public class ApproveUserProvider implements RequiredActionProvider {

    private static final String FIRST_LOGIN_DATE = "first-login-date";

    @Override

    public void evaluateTriggers(RequiredActionContext context) {
        UserModel user = context.getUser();
        if (!user.getAttributes().containsKey(FIRST_LOGIN_DATE)) {
            user.setAttribute(FIRST_LOGIN_DATE, List.of(new Date().toString()));
            context.getUser().addRequiredAction(UserModel.RequiredAction.UPDATE_PROFILE);
        }
    }

    @Override
    public void requiredActionChallenge(RequiredActionContext context) {

    }

    @Override
    public void processAction(RequiredActionContext context) {
    
    }

    @Override
    public void close() {

    }
}
