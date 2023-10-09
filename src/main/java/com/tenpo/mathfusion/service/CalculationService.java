package com.tenpo.mathfusion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tenpo.mathfusion.exception.ExternalServiceException;
import com.tenpo.mathfusion.service.external.ExternalService;

@Service
public class CalculationService {

    @Autowired
    private ExternalService externalService;

    @Autowired
    private CallHistoryService callHistoryService;

    public double calculateWithPercentage(double number1, double number2, String percentageType) throws ExternalServiceException {
        
        if (number1 < 0 || number2 < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los valores de entrada no son válidos.");
        }

        if(percentageType.isEmpty()) {
            percentageType = "LOW";
        }
        
        double percentageValue = externalService.getPercentage(percentageType);

        callHistoryService.logCallAsync("/api/calculate", number1, number2, percentageValue);

        return performCalculation(number1, number2, percentageValue);

    }

    private double performCalculation(double number1, double number2, double externalServiceValue) {
        return number1 + number2 + (number1 + number2) * (externalServiceValue / 100.0);
    }
}
