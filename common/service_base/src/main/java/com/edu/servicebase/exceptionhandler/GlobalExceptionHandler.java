package com.edu.servicebase.exceptionhandler;
import com.edu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //指定出现什么异常执行这个方法
    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("发生异常/(ㄒoㄒ)/~~请等待程序猿维修.....");
    }

//    //特定异常处理
//    @ExceptionHandler(Exception.class)
//    @ResponseBody //为了返回数据
//    public R error(ArithmeticException e) {//特定异常
//        e.printStackTrace();
//        return R.error().message("发生异常/(ㄒoㄒ)/~~请等待程序猿维修.....");
//    }

    //自定义异常
    @ExceptionHandler(EduException.class)
    @ResponseBody //为了返回数据
    public R error(EduException e) {
        log.error(e.getMessage());
        e.printStackTrace();

        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
