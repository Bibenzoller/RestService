package com.concretepage.acpects;

import com.concretepage.MyApplication;
import com.concretepage.jewelry.entity.Jewelry;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {
    private  static Logger LOGGER = LoggerFactory.getLogger(MyApplication.class);

    @Pointcut("within(com.concretepage.jewelry.controller.JewelryController)")
    private void inJewelryController() {}


    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getPointCutDefinition(){
    }
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postPointCutDefinition(){
    }

    //Defines a pointcut that we can use in the @Before,@After, @AfterThrowing, @AfterReturning,@Around specifications
    //The pointcut is a catch all pointcut with the scope of execution
    @Pointcut("execution(* *(..))")
    public void atExecution(){}

    @Before("getPointCutDefinition() && atExecution()&& args(str,..)")
    //JointPoint = the reference of the call to the method
    public void getWithParamsRequest(JoinPoint pointcut, String str) {
        LOGGER.debug("New request!");
        LOGGER.debug("Method : " + pointcut.getSignature().getName());
        LOGGER.debug("Parameters: " + str);
    }

    @Before("postPointCutDefinition() && atExecution() && args(obj,..)")
    //JointPoint = the reference of the call to the method
    public void postRequest(JoinPoint pointcut, Object obj) {
        LOGGER.debug("New request!");
        LOGGER.debug("method : " + pointcut.getSignature().getName());
        LOGGER.debug("Parametrs: "+ obj.toString());
    }
    @Before("getPointCutDefinition() && atExecution() && !(args(*))")
    //JointPoint = the reference of the call to the method
    public void postRequest(JoinPoint pointcut) {
        LOGGER.debug("New request!");
        LOGGER.debug("method : " + pointcut.getSignature().getName());
    }

    @AfterReturning(value = "postPointCutDefinition()|| getPointCutDefinition()"
            , returning = "returnValue")
    public void returning(JoinPoint joinPoint, Object returnValue) {

        if(returnValue !=null)
        {
           LOGGER.debug("Method return: "+returnValue.toString());
        }
        else{
            LOGGER.error("Method "+ joinPoint.getSignature().getName()+" return nothing");
        }
    }


}