package com.zav.appointment.dao;

import com.zav.appointment.datasource.TomcatDatasource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.Cleanup;
import lombok.Getter;
import lombok.SneakyThrows;

public class QueryExecutor {

    @SneakyThrows
    public static <T> List<T> executeQuery(String sql, Function<ResultSet, T> mapper) {
        @Cleanup
        var rs = new CloseableResultSet().executeQuery(sql);
        List<T> result = new ArrayList<>();
        while (rs.getResultSet().next()) {
            result.add(mapper.apply(rs.getResultSet()));
        }
        return result;
    }

    @SneakyThrows
    public static Optional<Object> executeQuerySingle(String sql) {
        @Cleanup
        var rs = new CloseableResultSet().executeQuery(sql);
        while (rs.getResultSet().next()) {
            return Optional.ofNullable(rs.getResultSet().getObject(1)) ;
        }
        return Optional.empty();
    }


    public static class CloseableResultSet implements AutoCloseable {

        private Connection connection;

        private PreparedStatement statement;

        @Getter
        private ResultSet resultSet;

        @SneakyThrows
        public CloseableResultSet executeQuery(String sql) {
            close();
            connection = TomcatDatasource.getDataSource().getConnection();
            statement = connection.prepareStatement(sql);
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
