package com.ef.enumeration;

import com.ef.exception.GeneralRuntimeException;

import java.time.LocalDateTime;
import java.util.Arrays;

public enum DurationEnum {

    HOURLY,
    DAILY;

    public static DurationEnum of(String value) {
        for (DurationEnum durationEnum : values()) {
            if (durationEnum.name().equalsIgnoreCase(value)) {
                return durationEnum;
            }
        }
        throw new GeneralRuntimeException("Missing line argument `duration`, could be: " + Arrays.toString(values()));
    }

    public LocalDateTime calculate(LocalDateTime time) {
        if (this == HOURLY) {
            return time.plusHours(1);
        }
        return time.plusDays(1);
    }

    public String warningMessage() {
        if (this == HOURLY) {
            return "in 1 hour";
        }
        return "";
    }
}
