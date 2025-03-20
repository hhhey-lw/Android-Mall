package com.wei.diploma_project.exception;

/**
 * User: 韦龙
 * Date: 2023/4/22
 * description:
 */
public class NotFoundException extends Exception {

    private Integer code;
    private String message;

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public NotFoundException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
