package com.zav.appointment.datasource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.tomcat.jdbc.pool.DataSource;

//TODO посмотреть, можно ли использовать пул WildFly
public class TomcatDatasource {

    private static Map<String, javax.sql.DataSource> dataSourceMap = new ConcurrentHashMap<>();

    public static javax.sql.DataSource getDataSource() {
        return dataSourceMap.computeIfAbsent("DataSource", s -> createDataSource());
    }

    //TODO брать настройки из конфига
    private static javax.sql.DataSource createDataSource() {
        DataSource ds = new DataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://127.0.0.1:3306/appointments");
        ds.setUsername("user");
        ds.setPassword("07831505");
        ds.setInitialSize(5);
        ds.setMaxActive(50);
        ds.setMaxIdle(5);
        ds.setMinIdle(2);
        return ds;
    }

}
