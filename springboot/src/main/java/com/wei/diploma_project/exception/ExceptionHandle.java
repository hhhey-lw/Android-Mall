package com.wei.diploma_project.exception;

import com.wei.diploma_project.util.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * User: 韦龙
 * Date: 2023/4/22
 * description:
 */
@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(NotFoundException.class)
    public Result handleException(NotFoundException e) {
        System.err.println("NotFoundException ：" + e.getMessage());
        return Result.notFound();
    }

    @ExceptionHandler(NotLoginException.class)
    public Result handleException(NotLoginException e) {
        System.err.println("NotLoginException ：" + e.getMessage());
        return Result.fail(400, "Not Login", null);
    }

    @ExceptionHandler(TokenException.class)
    public Result handleException(TokenException e) {
        System.err.println("TokenException ：" + e.getMessage());
        return Result.fail(401, "Token ERROR", null);
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        System.err.println("Exception ：" + e.getMessage());
        return Result.fail(e.getMessage());
    }

}
