package com.zav.appointment.datasource;

import com.zav.appointment.config.EnvConfigReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.val;
import org.apache.tomcat.jdbc.pool.DataSource;

import static com.zav.appointment.ConfigConstant.APPOINTMENT_DB_DRIVER_CLASS_NAME;
import static com.zav.appointment.ConfigConstant.APPOINTMENT_DB_PASSWORD;
import static com.zav.appointment.ConfigConstant.APPOINTMENT_DB_POOL_INITIAL_SIZE;
import static com.zav.appointment.ConfigConstant.APPOINTMENT_DB_POOL_MAX_ACTIVE;
import static com.zav.appointment.ConfigConstant.APPOINTMENT_DB_POOL_MAX_IDLE;
import static com.zav.appointment.ConfigConstant.APPOINTMENT_DB_POOL_MIN_IDLE;
import static com.zav.appointment.ConfigConstant.APPOINTMENT_DB_URL;
import static com.zav.appointment.ConfigConstant.APPOINTMENT_DB_USER;

//TODO посмотреть, можно ли использовать пул WildFly
public class TomcatDatasource {

    private static Map<String, javax.sql.DataSource> dataSourceMap = new ConcurrentHashMap<>();

    public static javax.sql.DataSource getDataSource() {
        return dataSourceMap.computeIfAbsent("DataSource", s -> createDataSource());
    }

    private static javax.sql.DataSource createDataSource() {
        val envConfigReader = EnvConfigReader.INSTANCE;
        DataSource ds = new DataSource();
        ds.setDriverClassName(envConfigReader.readValue(APPOINTMENT_DB_DRIVER_CLASS_NAME.name(),
                "com.mysql.jdbc.Driver"));
        ds.setUrl(envConfigReader.readValue(APPOINTMENT_DB_URL.name(),
                "jdbc:mysql://127.0.0.1:3306/appointments?currentSchema=appointments"));
        ds.setUrl(envConfigReader.readValue(APPOINTMENT_DB_USER.name(),
                "user"));
        ds.setUrl(envConfigReader.readValue(APPOINTMENT_DB_PASSWORD.name(),
                "user"));
        ds.setInitialSize(envConfigReader.readIntValue(APPOINTMENT_DB_POOL_INITIAL_SIZE.name(), 5));
        ds.setMaxActive(envConfigReader.readIntValue(APPOINTMENT_DB_POOL_MAX_ACTIVE.name(), 50));
        ds.setMaxIdle(envConfigReader.readIntValue(APPOINTMENT_DB_POOL_MAX_IDLE.name(), 5));
        ds.setMinIdle(envConfigReader.readIntValue(APPOINTMENT_DB_POOL_MIN_IDLE.name(), 2));
        return ds;
    }

}
