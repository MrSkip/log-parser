package com.ef.mapper;

import com.ef.dao.model.LogModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogMapper {
    private static final DateTimeFormatter FORMATTER;

    static {
        FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    }

    public static LogModel map(String[] arr) {
        return new LogModel()
                .setDate(LocalDateTime.parse(arr[0], FORMATTER))
                .setIp(arr[1])
                .setRequest(arr[2])
                .setStatus(Integer.parseInt(arr[3]))
                .setUserAgent(arr[4]);
    }

}
