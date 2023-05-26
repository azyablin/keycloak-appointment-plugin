package com.zav.appointment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConfigConstant {

    APPOINTMENT_DB_DRIVER_CLASS_NAME,
    APPOINTMENT_DB_URL,
    APPOINTMENT_DB_USER,
    APPOINTMENT_DB_PASSWORD,
    APPOINTMENT_DB_POOL_INITIAL_SIZE,
    APPOINTMENT_DB_POOL_MAX_ACTIVE,
    APPOINTMENT_DB_POOL_MAX_IDLE,
    APPOINTMENT_DB_POOL_MIN_IDLE,
    
}
