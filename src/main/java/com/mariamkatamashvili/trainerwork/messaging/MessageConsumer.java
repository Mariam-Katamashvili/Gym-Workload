package com.mariamkatamashvili.trainerwork.messaging;

import com.mariamkatamashvili.trainerwork.dto.WorkloadDTO;
import com.mariamkatamashvili.trainerwork.exception.WorkloadException;
import com.mariamkatamashvili.trainerwork.security.AuthValidator;
import com.mariamkatamashvili.trainerwork.security.JwtTokenProvider;
import com.mariamkatamashvili.trainerwork.service.WorkloadService;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageConsumer {
    private final WorkloadService workloadService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthValidator authValidator;

    @JmsListener(destination = "${messaging.jms.destination.main}")
    public void receiveWorkload(WorkloadDTO workload, Message message) {
        extractAndValidateToken(workload, message);
    }

    public void extractAndValidateToken(WorkloadDTO workload, Message message) {
        try {
            String token = message.getStringProperty("Authorization");
            authValidator.validate(token);
            workloadService.addWorkload(workload);
        } catch (Exception e) {
            throw new WorkloadException("Error processing message", "500");
        }
    }
}