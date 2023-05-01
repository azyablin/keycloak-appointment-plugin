package com.zav.appointment.dao;

import com.zav.appointment.model.StorageInfo;
import com.zav.appointment.model.User;
import java.sql.ResultSet;
import java.util.List;
import java.util.Locale;
import lombok.SneakyThrows;
import lombok.val;
import org.keycloak.models.UserModel;

public class UserDao {

    public static UserModel getByName(StorageInfo storageInfo,  String username) {
        var sql = "select id, name from appointments.cd4w_user where upper(name) = '"
                .concat(username.toUpperCase(Locale.ROOT))
                .concat(";");
        val users = QueryExecutor.executeQuery(sql, resultSet -> mapUser(storageInfo, resultSet));
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    public static List<UserModel> getAll(StorageInfo storageInfo) {
        var sql = "select id, name from appointments.cd4w_user";
        return QueryExecutor.executeQuery(sql, resultSet -> mapUser(storageInfo, resultSet));
    }

    public static int getUsersCount() {
        var sql = "select count(*) from appointments.cd4w_user";
        Long result = (Long) QueryExecutor.executeQuerySingle(sql).orElse(0L);
        return result.intValue();
    }

    @SneakyThrows
    private static User mapUser(StorageInfo storageInfo, ResultSet resultSet) {
        User user = new User(storageInfo);
        user.setId(resultSet.getLong(1));
        user.setUsername(resultSet.getString(1));
        return user;
    }



}
