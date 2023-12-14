package com.spring.entity;

import lombok.Data;

import java.util.Map;

@Data
public class Message {
    private String Message;
    private Integer status;
    private Object param;

    public Message(String message, Integer status,Object param) {
        this.Message = message;
        this.status = status;
        this.param = param;
    }

}
