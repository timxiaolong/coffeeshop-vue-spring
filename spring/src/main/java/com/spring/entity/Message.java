package com.spring.entity;

import lombok.Data;

import java.util.Map;

@Data
public class Message {
    private String message;
    private Integer status;
    private Object param;

    public Message(String message, Integer status,Object param) {
        this.message = message;
        this.status = status;
        this.param = param;
    }

}
