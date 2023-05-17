package com.zav.appointment.dao;

import com.zav.appointment.datasource.TomcatDatasource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.Cleanup;
import lombok.Getter;
import lombok.SneakyThrows;

public class QueryExecutor {

    private static final String LIKE_PATTERN = "%";

    @SneakyThrows
    public static <T> List<T> executeQuery(String sql, Function<ResultSet, T> resultMapper,
            List params) {
        @Cleanup
        var rs = new CloseableResultSet().executeQuery(sql, params);
        List<T> result = new ArrayList<>();
        while (rs.getResultSet().next()) {
            result.add(resultMapper.apply(rs.getResultSet()));
        }
        return result;
    }

    @SneakyThrows
    public static Optional<Object> executeQuerySingle(String sql, List params) {
        @Cleanup
        var rs = new CloseableResultSet().executeQuery(sql, params);
        while (rs.getResultSet().next()) {
            return Optional.ofNullable(rs.getResultSet().getObject(1));
        }
        return Optional.empty();
    }

    public static String createLikeString(String search) {
        return LIKE_PATTERN.concat(search.replace(" ", LIKE_PATTERN)).concat(LIKE_PATTERN);
    }


    public static class CloseableResultSet implements AutoCloseable {

        private Connection connection;

        private PreparedStatement statement;

        @Getter
        private ResultSet resultSet;

        @SneakyThrows
        public CloseableResultSet executeQuery(String sql, List params) {
            close();
            connection = TomcatDatasource.getDataSource().getConnection();
            statement = connection.prepareStatement(sql);
            Optional.ofNullable(params).ifPresent(list -> {
                for (int i = 1; i <= params.size(); ++i) {
                    try {
                        statement.setObject(i, params.get(i - 1));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            resultSet = statement.executeQuery();
            return this;
        }

        @Override
        @SneakyThrows
        public void close() {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }


    }

}
