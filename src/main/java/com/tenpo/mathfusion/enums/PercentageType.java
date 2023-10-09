package com.tenpo.mathfusion.enums;

import com.tenpo.mathfusion.exception.ExternalServiceException;

public enum PercentageType {
    HIGH(100.0),
    MEDIUM(50.0),
    LOW(10.0);

    private final double value;

    PercentageType(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public static PercentageType fromString(String input) throws ExternalServiceException {
        switch (input.toUpperCase()) {
            case "HIGH":
                return HIGH;
            case "MEDIUM":
                return MEDIUM;
            case "LOW":
                return LOW;
            default:
                throw new ExternalServiceException("La entrada debe ser 'HIGH', 'MEDIUM' o 'LOW'");
        }
    }
}
