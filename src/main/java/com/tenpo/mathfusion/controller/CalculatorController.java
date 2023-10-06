package com.tenpo.mathfusion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenpo.mathfusion.dto.NumbersRequest;
import com.tenpo.mathfusion.exception.ExternalServiceException;
import com.tenpo.mathfusion.service.CalculationService;


@RestController
@RequestMapping("/api")
public class CalculatorController {

    @Autowired
    private CalculationService calculationService;

    @PostMapping("/calculate")
    public ResponseEntity<Double> calculateWithPercentage(@RequestBody NumbersRequest request) {
        try {
            double result = calculationService.calculateWithPercentage(request.getNumber1(), request.getNumber2(), request.getPercentageType());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ExternalServiceException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}