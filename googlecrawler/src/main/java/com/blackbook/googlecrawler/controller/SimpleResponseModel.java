package com.blackbook.googlecrawler.controller;

import lombok.Data;

/**
 * @author Siarhei Shauchenka at 06.09.17
 */
@Data
public class SimpleResponseModel {
    private int code;
    private String message;

    public SimpleResponseModel(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
