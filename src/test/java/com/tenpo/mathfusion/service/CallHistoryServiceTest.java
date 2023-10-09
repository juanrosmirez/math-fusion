package com.tenpo.mathfusion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.tenpo.mathfusion.exception.ExternalServiceException;
import com.tenpo.mathfusion.model.CallHistory;
import com.tenpo.mathfusion.repository.CallHistoryRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CallHistoryServiceTest {

    @Mock
    private CallHistoryRepository callLogRepository;

    @InjectMocks
    private CallHistoryService callHistoryService;

    @Test
    public void testLogCallAsync() throws ExternalServiceException {
        String endpoint = "/api/calculate";
        double number1 = 5.0;
        double number2 = 3.0;
        double result = 8.0;

        ArgumentCaptor<CallHistory> callHistoryCaptor = ArgumentCaptor.forClass(CallHistory.class);

        when(callLogRepository.save(callHistoryCaptor.capture())).thenReturn(null);

        callHistoryService.logCallAsync(endpoint, number1, number2, result);

        CallHistory capturedCallHistory = callHistoryCaptor.getValue();

        assertEquals(endpoint, capturedCallHistory.getEndpoint());
        assertEquals(number1, capturedCallHistory.getNumber1(), 0.001);
        assertEquals(number2, capturedCallHistory.getNumber2(), 0.001);
        assertEquals(result, capturedCallHistory.getResult(), 0.001);
    }

    @Test
    public void testLogCallAsyncWithNegativeResult() throws ExternalServiceException {
        String endpoint = "/api/calculate";
        double number1 = 5.0;
        double number2 = 3.0;
        double result = -2.0;

        ArgumentCaptor<CallHistory> callHistoryCaptor = ArgumentCaptor.forClass(CallHistory.class);

        when(callLogRepository.save(callHistoryCaptor.capture())).thenReturn(null);

        callHistoryService.logCallAsync(endpoint, number1, number2, result);

        CallHistory capturedCallHistory = callHistoryCaptor.getValue();

        assertEquals(endpoint, capturedCallHistory.getEndpoint());
        assertEquals(number1, capturedCallHistory.getNumber1(), 0.001);
        assertEquals(number2, capturedCallHistory.getNumber2(), 0.001);
        assertEquals(result, capturedCallHistory.getResult(), 0.001);
    }

    @Test
    public void testLogCallAsyncWithZeroResult() throws ExternalServiceException {
        String endpoint = "/api/calculate";
        double number1 = 5.0;
        double number2 = 0.0;
        double result = 0.0;

        ArgumentCaptor<CallHistory> callHistoryCaptor = ArgumentCaptor.forClass(CallHistory.class);

        when(callLogRepository.save(callHistoryCaptor.capture())).thenReturn(null);

        callHistoryService.logCallAsync(endpoint, number1, number2, result);

        CallHistory capturedCallHistory = callHistoryCaptor.getValue();

        assertEquals(endpoint, capturedCallHistory.getEndpoint());
        assertEquals(number1, capturedCallHistory.getNumber1(), 0.001);
        assertEquals(number2, capturedCallHistory.getNumber2(), 0.001);
        assertEquals(result, capturedCallHistory.getResult(), 0.001);
    }

    @Test
    public void testLogCallAsyncWithNullEndpoint() {
        String endpoint = null;
        double number1 = 5.0;
        double number2 = 3.0;
        double result = 8.0;

        assertThrows(ExternalServiceException.class,
                () -> callHistoryService.logCallAsync(endpoint, number1, number2, result));
    }
}