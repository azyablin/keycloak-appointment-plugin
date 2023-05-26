package com.zav.appointment.config;

public class EnvConfigReader extends ConfigReader {

    public static final EnvConfigReader INSTANCE = new EnvConfigReader();

    @Override
    public String readValue(String name) {
        return System.getenv(name);
    }

    private EnvConfigReader() {

    }
}
