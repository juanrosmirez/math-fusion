package com.tenpo.mathfusion.service.external;

import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tenpo.mathfusion.enums.PercentageType;
import com.tenpo.mathfusion.exception.ExternalServiceException;

@Service
public class ExternalService {

    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final int EXTERNAL_SERVICE_CACHE_DURATION = 1800000;
    private Double lastPercentageValue;
    private long lastPercentageValueTimestamp;

    @Retryable(maxAttempts = MAX_RETRY_ATTEMPTS, value = {
            ExternalServiceException.class }, backoff = @Backoff(delay = 1000)) // Retardo de 1 segundo entre reintentos
    public double getPercentage(String percentageType) throws ExternalServiceException {

        try {

            if (lastPercentageValue != null
                    && System.currentTimeMillis() - lastPercentageValueTimestamp <= EXTERNAL_SERVICE_CACHE_DURATION) {
                return lastPercentageValue;
            }

            double percentageValue = callExternalServiceAndGetPercentage(percentageType);
            lastPercentageValue = percentageValue;
            lastPercentageValueTimestamp = System.currentTimeMillis();

            return percentageValue;

        } catch (ExternalServiceException e) {
            if (lastPercentageValue != null) {
                return lastPercentageValue;
            } else {
                throw e;
            }
        }
    }

    @Recover
    public double handleMaxRetries(ExternalServiceException e) {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Demasiados intentos al servicio externo");
    }

    public double callExternalServiceAndGetPercentage(String input) throws ExternalServiceException {
        PercentageType percentageType = PercentageType.fromString(input);
        return percentageType.getValue();
    }
}