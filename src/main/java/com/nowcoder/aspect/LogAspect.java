package com.nowcoder.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author ：xingxiangdong
 * @Date :2019/7/810:39
 */
@Aspect
@Component
public class LogAspect {
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.nowcoder.controller.*Controller.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        logger.info("before" + joinPoint.toLongString());//打印参数类型
        StringBuilder sb = new StringBuilder();
        for(Object s :joinPoint.getArgs()){//打印真实的参数
            sb.append(s);
        }
        logger.info("before2:"+sb.toString());
    }
    @After("execution(* com.nowcoder.controller.*Controller.*(..))")
    public void afterMethod(){
        logger.info("after");
    }
}
