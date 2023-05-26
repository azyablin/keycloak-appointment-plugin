package com.zav.appointment.config;

import java.util.Optional;
import java.util.function.Predicate;


public abstract class ConfigReader {

    public int readIntValue(String name, Integer defaultValue) {
        return Optional.ofNullable(readValue(name))
                .filter(Predicate.not(String::isEmpty))
                .map(Integer::valueOf)
                .or(() -> Optional.of(defaultValue))
                .get();
    }

    public String readValue(String name, String defaultValue) {
        return Optional.ofNullable(readValue(name))
                .filter(Predicate.not(String::isEmpty))
                .or(() -> Optional.of(defaultValue))
                .get();
    }

    public abstract String readValue(String name);

}
