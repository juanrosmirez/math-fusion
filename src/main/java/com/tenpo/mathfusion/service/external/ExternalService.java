package com.tenpo.mathfusion.service.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tenpo.mathfusion.enums.PercentageType;
import com.tenpo.mathfusion.exception.ExternalServiceException;
import com.tenpo.mathfusion.service.session.SessionCache;

@Service
public class ExternalService {

    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final int EXTERNAL_SERVICE_CACHE_DURATION = 1800000;

    @Autowired
    private SessionCache sessionCache;

    @Retryable(maxAttempts = MAX_RETRY_ATTEMPTS, value = {
            ExternalServiceException.class }, backoff = @Backoff(delay = 1000))
    public double getPercentage(String percentageType) throws ExternalServiceException {

        try {
            if (sessionCache.getLastPercentageValue() != null
                    && System.currentTimeMillis() - sessionCache.getLastPercentageValueTimestamp() <= EXTERNAL_SERVICE_CACHE_DURATION) {
                return sessionCache.getLastPercentageValue();
            }

            double percentageValue = callExternalServiceAndGetPercentage(percentageType);
            sessionCache.setLastPercentageValue(percentageValue);
            sessionCache.setLastPercentageValueTimestamp(System.currentTimeMillis());

            return percentageValue;

        } catch (ExternalServiceException e) {
            if (sessionCache.getLastPercentageValue() != null) {
                return sessionCache.getLastPercentageValue();
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