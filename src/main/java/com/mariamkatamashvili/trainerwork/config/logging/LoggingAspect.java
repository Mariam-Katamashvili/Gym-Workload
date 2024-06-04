package com.mariamkatamashvili.trainerwork.config.logging;

import com.mariamkatamashvili.trainerwork.dto.WorkloadDTO;
import jakarta.jms.Message;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut(value = "execution(* com.mariamkatamashvili.trainerwork.messaging..*(..)) && args(workload, message)", argNames = "workload,message")
    public void loggableMethods(WorkloadDTO workload, Message message) {
    }

    @Before(value = "loggableMethods(workload, message)", argNames = "workload,message")
    public void logBefore(WorkloadDTO workload, Message message) throws Exception {
        String transactionId = message.getStringProperty("transactionId");
        log.info("TransactionId: {}, message read by WorkloadMessageListener", transactionId);
    }

    @AfterThrowing(pointcut = "loggableMethods(workload, message)", throwing = "e", argNames = "workload,message,e")
    public void logAfterThrowing(WorkloadDTO workload, Message message, Exception e) {
        log.error("Error processing message: {}", e.getMessage());
    }
}