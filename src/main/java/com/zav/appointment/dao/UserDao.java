package com.zav.appointment.dao;

import com.zav.appointment.domain.User;
import com.zav.appointment.model.StorageInfo;
import com.zav.appointment.model.UserWrapper;
import java.sql.ResultSet;
import java.util.List;
import java.util.Locale;
import lombok.SneakyThrows;
import lombok.val;

public class UserDao {

    public static org.keycloak.models.UserModel getByName(StorageInfo storageInfo, String username) {
        var sql = "select id, name from appointments.cd4w_user where upper(name) = '"
                .concat(username.toUpperCase(Locale.ROOT))
                .concat(";");
        val users = QueryExecutor.executeQuery(sql, resultSet -> mapUser(storageInfo, resultSet));
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    public static List<org.keycloak.models.UserModel> getAll(StorageInfo storageInfo) {
        var sql = "select id, name from appointments.cd4w_user";
        return QueryExecutor.executeQuery(sql, resultSet -> mapUser(storageInfo, resultSet));
    }

    public static int getUsersCount() {
        var sql = "select count(*) from appointments.cd4w_user";
        Long result = (Long) QueryExecutor.executeQuerySingle(sql).orElse(0L);
        return result.intValue();
    }

    @SneakyThrows
    private static UserWrapper mapUser(StorageInfo storageInfo, ResultSet resultSet) {
        User user = new User();
        user.setId(resultSet.getLong(1));
        user.setUsername(resultSet.getString(1));
        UserWrapper userModel = new UserWrapper(storageInfo, user);

        return userModel;
    }



}
