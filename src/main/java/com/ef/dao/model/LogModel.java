package com.ef.dao.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogModel {

    private Long id;
    private LocalDateTime date;
    private String ip;
    private String request;
    private int status;
    private String userAgent;

}
