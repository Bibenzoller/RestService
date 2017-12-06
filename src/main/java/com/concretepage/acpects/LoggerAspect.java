package com.concretepage.acpects;

import com.concretepage.MyApplication;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {
    private  static Logger LOGGER = LoggerFactory.getLogger(MyApplication.class);

    @Before("execution(public * getJewelry(String)) && args(barCode)")
    public void beforeSampleCreation(String barCode) {
        LOGGER.info("Get jewelry with bar code: "+ barCode);

    }
}