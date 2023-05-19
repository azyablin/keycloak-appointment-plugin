package com.zav.appointment.provider;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.val;
import org.keycloak.authentication.InitiatedActionSupport;
import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.UserModel;

public class ApproveUserProvider implements RequiredActionProvider {

    private static final String FIRST_LOGIN_DATE = "first-login-date";

    @Override
    public InitiatedActionSupport initiatedActionSupport() {
        return InitiatedActionSupport.SUPPORTED;
    }

    @Override
    public void evaluateTriggers(RequiredActionContext context) {
        UserModel user = context.getUser();
        if (!user.getAttributes().containsKey(FIRST_LOGIN_DATE)) {
            if (isSubmitted(context)) {
                user.setAttribute(FIRST_LOGIN_DATE, List.of(new Date().toString()));
            } else {
                context.getUser().addRequiredAction(UserModel.RequiredAction.UPDATE_PROFILE);
            }
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

    private boolean isSubmitted(RequiredActionContext context) {
        //TODO возможно есть лучший способ определить, что нажата кнопка submit
        return context.getHttpRequest().getHttpMethod().equals("POST") &&
                Optional.ofNullable(context.getHttpRequest().getUri()
                                .getQueryParameters().get("execution"))
                        .filter(params -> params.contains(UserModel.RequiredAction.UPDATE_PROFILE.name()))
                        .isPresent();
    }
}
