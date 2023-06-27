package com.atguigu.yygh.common.handler;

import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.util.ExceptionUtil;
import com.atguigu.yygh.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Date 2023/6/25
 * @Author SLF
 * @Description: 异常处理类
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R error(Exception e) {
        log.error(ExceptionUtil.getMessage(e));
        return R.error().message(e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public R error(NullPointerException e) {
        log.error(ExceptionUtil.getMessage(e));
        return R.error().message("空指针异常");
    }

    @ExceptionHandler(YyghException.class)
    public R error(YyghException e) {
        log.error(ExceptionUtil.getMessage(e));
        return R.error().code(e.getCode()).message(e.getMessage());
    }
}
