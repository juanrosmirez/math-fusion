package com.tenpo.mathfusion.dto;

public class NumbersRequest {
    private double number1;
    private double number2;
    private String percentageType;

    // Constructor vacío (necesario para Spring Boot)
    public NumbersRequest() {
    }

    // Constructor con parámetros
    public NumbersRequest(double number1, double number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    // Constructor con parámetros
    public NumbersRequest(double number1, double number2, String percentageType) {
        this.number1 = number1;
        this.number2 = number2;
        this.percentageType = percentageType;
    }

    // Getters y setters
    public double getNumber1() {
        return number1;
    }

    public void setNumber1(double number1) {
        this.number1 = number1;
    }

    public double getNumber2() {
        return number2;
    }

    public void setNumber2(double number2) {
        this.number2 = number2;
    }

    public void setPercentageType(String percentageType) {
        this.percentageType = percentageType;
    }

    public String getPercentageType() {
        return percentageType;
    }
}