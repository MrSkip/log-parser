package com.ef.dao.model;

import lombok.Data;

@Data
public class BlockedIpModel {

    private Long id;
    private String ip;
    private String message;

}
