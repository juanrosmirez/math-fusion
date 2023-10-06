package com.tenpo.mathfusion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tenpo.mathfusion.model.CallHistory;
import com.tenpo.mathfusion.repository.CallHistoryRepository;

@RestController
@RequestMapping("/api/call-history")
public class CallHistoryController {

    @Autowired
    private CallHistoryRepository callHistoryRepository;

    @GetMapping
    public ResponseEntity<Page<CallHistory>> getCallHistory(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        Page<CallHistory> callHistoryPage = callHistoryRepository.findAllByOrderByTimestampDesc(pageable);
        return new ResponseEntity<>(callHistoryPage, HttpStatus.OK);
    }
}