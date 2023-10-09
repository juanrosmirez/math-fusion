package com.tenpo.mathfusion.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;

import com.tenpo.mathfusion.model.CallHistory;
import com.tenpo.mathfusion.repository.CallHistoryRepository;

@ExtendWith(MockitoExtension.class)
public class CallHistoryControllerTest {

    @Mock
    private CallHistoryRepository callHistoryRepository;

    private CallHistoryController callHistoryController;

    @BeforeEach
    public void setUp() {
        callHistoryController = new CallHistoryController(callHistoryRepository);
    }

    @Test
    public void testGetCallHistory() {
        List<CallHistory> mockCallHistoryList = Arrays.asList(
                createCallHistory(1, 2, 3),
                createCallHistory(4, 5, 9));

        Page<CallHistory> mockPage = new PageImpl<>(mockCallHistoryList);

        when(callHistoryRepository.findAllByOrderByTimestampDesc(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(mockPage);

        ResponseEntity<Page<CallHistory>> response = callHistoryController.getCallHistory(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPage, response.getBody());
    }

    private CallHistory createCallHistory(double number1, double number2, double result) {
        CallHistory callHistory = new CallHistory();
        callHistory.setTimestamp(LocalDateTime.now());
        callHistory.setEndpoint("/api/calculate");
        callHistory.setNumber1(number1);
        callHistory.setNumber2(number2);
        callHistory.setResult(result);
        return callHistory;
    }
}