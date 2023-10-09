package com.tenpo.mathfusion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tenpo.mathfusion.exception.ExternalServiceException;
import com.tenpo.mathfusion.service.external.ExternalService;

@ExtendWith(MockitoExtension.class)
public class CalculationServiceTest {
    @Mock
    private ExternalService externalService;

    @Mock
    private CallHistoryService callHistoryService;

    @InjectMocks
    private CalculationService calculationService;

    @Test
    public void testCalculateWithPercentage() throws ExternalServiceException {
        double number1 = 5;
        double number2 = 5;
        double percentage = 100;

        double expected = 5 + 5 + (5 + 5) * (percentage / 100);

        when(externalService.getPercentage("HIGH")).thenReturn(percentage);
        doNothing().when(callHistoryService).logCallAsync("/api/calculate", number1, number2, percentage);

        double result = calculationService.calculateWithPercentage(number1, number2, "HIGH");

        assertEquals(expected, result, 0.001);
    }

    @Test
    public void testCalculateWithPercentage_ExternalServiceError() throws ExternalServiceException {
        double number1 = 10;
        double number2 = 20;

        when(externalService.getPercentage("ERROR"))
                .thenThrow(new ExternalServiceException("Error en el servicio externo"));

        ExternalServiceException exception = assertThrows(ExternalServiceException.class, () -> {
            calculationService.calculateWithPercentage(number1, number2, "ERROR");
        });

        assertEquals("Error en el servicio externo", exception.getMessage());
    }

    @Test
    public void testCalculateWithPercentage_ValidInput() throws ExternalServiceException {
        double number1 = 10;
        double number2 = 20;
        double percentage = 15;

        when(externalService.getPercentage("MEDIUM")).thenReturn(percentage);

        double result = calculationService.calculateWithPercentage(number1, number2, "MEDIUM");

        double expected = number1 + number2 + (number1 + number2) * (percentage / 100);
        assertEquals(expected, result, 0.001);
    }

    @Test
    public void testCalculateWithPercentage_EmptyPercentageType() throws ExternalServiceException {
        double number1 = 15;
        double number2 = 25;
        double defaultPercentage = 10;

        when(externalService.getPercentage("LOW")).thenReturn(defaultPercentage);

        double result = calculationService.calculateWithPercentage(number1, number2, "");

        double expected = number1 + number2 + (number1 + number2) * (defaultPercentage / 100);
        assertEquals(expected, result, 0.001);
    }

}
