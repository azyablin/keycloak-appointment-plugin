package com.zav.appointment.datasource;

import com.zav.appointment.config.EnvConfigReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.val;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.keycloak.common.util.MultivaluedHashMap;

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

    private static MultivaluedHashMap<String, String> config;

    public static javax.sql.DataSource getDataSource() {
        return dataSourceMap.computeIfAbsent("DataSource", s -> createDataSource());
    }

    private static javax.sql.DataSource createDataSource() {
        val envConfigReader = EnvConfigReader.INSTANCE;
        DataSource ds = new DataSource();
        ds.setDriverClassName(envConfigReader.readValue(APPOINTMENT_DB_DRIVER_CLASS_NAME.name(),
                config.getFirst(APPOINTMENT_DB_DRIVER_CLASS_NAME.name())));
        ds.setUrl(envConfigReader.readValue(APPOINTMENT_DB_URL.name(),
                config.getFirst(APPOINTMENT_DB_URL.name())));
        ds.setUsername(envConfigReader.readValue(APPOINTMENT_DB_USER.name(),
                config.getFirst(APPOINTMENT_DB_USER.name())));
        ds.setPassword(envConfigReader.readValue(APPOINTMENT_DB_PASSWORD.name(),
                config.getFirst(APPOINTMENT_DB_PASSWORD.name())));
        ds.setInitialSize(envConfigReader.readIntValue(APPOINTMENT_DB_POOL_INITIAL_SIZE.name(),
                Integer.valueOf(config.getFirst(APPOINTMENT_DB_POOL_INITIAL_SIZE.name()))));
        ds.setMaxActive(envConfigReader.readIntValue(APPOINTMENT_DB_POOL_MAX_ACTIVE.name(),
                Integer.valueOf(config.getFirst(APPOINTMENT_DB_POOL_MAX_ACTIVE.name()))));
        ds.setMaxIdle(envConfigReader.readIntValue(APPOINTMENT_DB_POOL_MAX_IDLE.name(),
                Integer.valueOf(config.getFirst(APPOINTMENT_DB_POOL_MAX_IDLE.name()))));
        ds.setMinIdle(envConfigReader.readIntValue(APPOINTMENT_DB_POOL_MIN_IDLE.name(),
                Integer.valueOf(config.getFirst(APPOINTMENT_DB_POOL_MIN_IDLE.name()))));
        return ds;
    }

    public synchronized static void setConfig(MultivaluedHashMap<String, String> config) {
        TomcatDatasource.config = config;
    }

}
