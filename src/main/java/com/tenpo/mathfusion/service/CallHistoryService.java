package com.tenpo.mathfusion.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.tenpo.mathfusion.exception.ExternalServiceException;
import com.tenpo.mathfusion.model.CallHistory;
import com.tenpo.mathfusion.repository.CallHistoryRepository;

@Service
public class CallHistoryService {

    @Autowired
    private CallHistoryRepository callLogRepository;

    @Async
    public void logCallAsync(String endpoint, double number1, double number2, double result) throws ExternalServiceException {
        
        if (endpoint == null) {
            throw new ExternalServiceException("El endpoint no puede ser nulo.");
        }

        CallHistory callHistory = new CallHistory();
        callHistory.setTimestamp(LocalDateTime.now());
        callHistory.setEndpoint(endpoint);
        callHistory.setNumber1(number1);
        callHistory.setNumber2(number2);
        callHistory.setResult(result);
        callLogRepository.save(callHistory);
    }
}
