package com.zav.appointment.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import lombok.SneakyThrows;

public class RoleDao {

    private static final String NAMES_BY_USER_ID_SQL = """
            select r.name from appointments.cd4w_role r 
            where r.id in  (select rp.role_id from cd4w_role_patients rp where rp.patient_id = ?)
            """;

    public static List<String> getNamesByUserId(String userId) {
        return QueryExecutor.executeQuery(NAMES_BY_USER_ID_SQL, RoleDao::mapName, List.of(userId));
    }

    @SneakyThrows
    private static String mapName(ResultSet resultSet) {
        return resultSet.getString(1);
    }


    @SneakyThrows
    private static void mapParams(PreparedStatement ps, String userId) {
        ps.setString(1, userId);
    }

}
