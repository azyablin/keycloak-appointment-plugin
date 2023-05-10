package com.zav.appointment.dao;

import com.zav.appointment.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import lombok.val;

@Log
public class UserDao {

    private static final String BASE_SQL = """
                select id, msisdn, first_name, last_name, email from cd4w_patient p 
            """;

    private static final String SEARCH_CONDITION = """
                 upper(concat(id, msisdn, first_name, last_name, coalesce(email,''))) like ? 
            """;
    private static final String BY_ROLE_SQL = BASE_SQL + """
            where p.id in 
            (select rp.patient_id from cd4w_role_patients rp
            inner join cd4w_role r on r.id = rp.role_id where r.name = ?) LIMIT ?, ?
            """;

    public static Optional<User> getByName(String username) {
        var sql = BASE_SQL + " where upper(msisdn) = ?";
        val users = QueryExecutor.executeQuery(sql, UserDao::mapUser, List.of(username));
        return users.stream().findFirst();
    }


    public static User getById(String id) {
        var sql = BASE_SQL + " where id = ?";
        val users = QueryExecutor.executeQuery(sql, UserDao::mapUser, List.of(id));
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    public static List<User> search(String search, Integer firstResult, Integer maxResults) {
        StringJoiner sqlJoiner = new StringJoiner(" ");
        List params = new ArrayList<>();
        sqlJoiner.add(BASE_SQL);
        sqlJoiner.add("where 1=1");
        fillSearchCriteria(search, sqlJoiner, params);
        if (Stream.of(firstResult, maxResults).noneMatch(s -> s == null)) {
            sqlJoiner.add("LIMIT ?, ?");
            params.add(firstResult);
            params.add(maxResults);
        }
        return QueryExecutor.executeQuery(sqlJoiner.toString(), UserDao::mapUser, params);
    }

    public static int getUsersCount(String search) {
        StringJoiner sqlJoiner = new StringJoiner(" ");
        List params = new ArrayList<>();
        sqlJoiner.add("select count(*) from cd4w_patient where 1=1 ");
        fillSearchCriteria(search, sqlJoiner, params);
        Long result = (Long) QueryExecutor.executeQuerySingle(sqlJoiner.toString(), params).orElse(0L);
        return result.intValue();
    }

    public static List<User> findByRoleName(String roleName, Integer firstResult, Integer maxResults) {
        return QueryExecutor.executeQuery(BY_ROLE_SQL, UserDao::mapUser, List.of(roleName, firstResult, maxResults));
    }

    private static void fillSearchCriteria(String search, StringJoiner sqlJoiner, List params) {
        if (Stream.of("", "*", null).noneMatch(s -> Objects.equals(s, search))) {
            sqlJoiner.add("and");
            sqlJoiner.add(SEARCH_CONDITION);
            params.add(QueryExecutor.createLikeString(search));
        }
    }

    @SneakyThrows
    private static User mapUser(ResultSet resultSet) {
        return User.builder()
                .id(resultSet.getString(1))
                .username(resultSet.getString(2))
                .firstName(resultSet.getString(3))
                .lastName(resultSet.getString(4))
                .email(resultSet.getString(5))
                .build();
    }


    @SneakyThrows
    private static void mapParams(PreparedStatement ps, String param) {
        ps.setString(1, param);
    }


}
