package com.tenpo.mathfusion.controller;

import com.tenpo.mathfusion.dto.NumbersRequest;
import com.tenpo.mathfusion.exception.ExternalServiceException;
import com.tenpo.mathfusion.service.CalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CalculatorControllerTest {

    @Mock
    private CalculationService calculationService;

    @InjectMocks
    private CalculatorController calculatorController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateWithPercentage() throws ExternalServiceException {
        NumbersRequest request = new NumbersRequest(10.0, 5.0, "percentage");

        when(calculationService.calculateWithPercentage(10.0, 5.0, "percentage")).thenReturn(7.0);

        ResponseEntity<Double> responseEntity = calculatorController.calculateWithPercentage(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(7.0, responseEntity.getBody());
        verify(calculationService, times(1)).calculateWithPercentage(10.0, 5.0, "percentage");
    }

    @Test
    public void testCalculateWithPercentageExternalServiceError() throws ExternalServiceException {
        NumbersRequest request = new NumbersRequest(10.0, 5.0, "percentage");

        when(calculationService.calculateWithPercentage(10.0, 5.0, "percentage"))
                .thenThrow(new ExternalServiceException("Error en el servicio externo OPA"));

        ResponseEntity<Double> responseEntity = calculatorController.calculateWithPercentage(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        verify(calculationService, times(1)).calculateWithPercentage(10.0, 5.0, "percentage");
    }
}