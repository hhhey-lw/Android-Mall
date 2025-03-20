package com.wei.diploma_project.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: 韦龙
 * Date: 2023/3/18
 * description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private int code;
    private String msg;
    private Object data;

    public static Result ok() {
        return new Result(200, "ok", null);
    }
    public static Result ok(Object data) {
        return new Result(200, "ok", data);
    }
    public static Result fail() {
        return new Result(500, "fail", null);
    }

    public static Result fail(Object data) {
        return new Result(500, "fail", data);
    }

    public static Result fail(int code, String msg, Object data) {
        return new Result(code, msg, data);
    }

    public static Result notFound() {
        return new Result(404, "not found", null);
    }
}
