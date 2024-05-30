package com.mariamkatamashvili.trainerwork.messaging;

import com.mariamkatamashvili.trainerwork.dto.WorkloadDTO;
import com.mariamkatamashvili.trainerwork.exception.WorkloadException;
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

    @JmsListener(destination = "${messaging.jms.destination.main}")
    public void receiveWorkload(WorkloadDTO workload, Message message) {
        extractAndValidateToken(workload, message);
    }

    public void extractAndValidateToken(WorkloadDTO workload, Message message) {
        try {
            String token = message.getStringProperty("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                if (jwtTokenProvider.isTokenValid(token)) {
                    workloadService.addWorkload(workload);
                } else {
                    throw new WorkloadException("Invalid JWT token", "401");
                }
            } else {
                throw new WorkloadException("JWT token missing or invalid", "401");
            }
        } catch (Exception e) {
            throw new WorkloadException("Error processing message", "500");
        }
    }
}

