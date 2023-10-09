package com.tenpo.mathfusion.filter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.mathfusion.dto.ErrorResponse;

@Component
public class RateLimitFilter implements Filter {

    static final int MAX_REQUESTS_PER_MINUTE = 3;
    private ConcurrentHashMap<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Long> resetTimes = new ConcurrentHashMap<>();


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String ipAddress = request.getRemoteAddr();
        AtomicInteger requestCount = requestCounts.computeIfAbsent(ipAddress, k -> new AtomicInteger(0));

        if (allowRequest(ipAddress, requestCount)) {
            chain.doFilter(request, response);
        } else {
            handleRateLimitExceeded(response);
        }
    }

    private boolean allowRequest(String ipAddress, AtomicInteger requestCount) {
        long currentTime = System.currentTimeMillis();
        long resetTime = resetTimes.getOrDefault(ipAddress, currentTime);

        if (currentTime >= resetTime) {
            requestCount.set(0);
            resetTimes.put(ipAddress, currentTime + 10000);
        }

        return requestCount.incrementAndGet() <= MAX_REQUESTS_PER_MINUTE;
    }

    public void handleRateLimitExceeded(ServletResponse response) throws IOException {
        int statusCode = HttpStatus.TOO_MANY_REQUESTS.value();
        String message = "Demasiadas solicitudes. Inténtalo de nuevo más tarde.";

        ErrorResponse errorResponse = new ErrorResponse(statusCode, message);
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        
        httpResponse.setContentType("application/json");
        httpResponse.setStatus(statusCode);
        httpResponse.getWriter().write(jsonResponse);
    }

    public void setRequestCounts(ConcurrentHashMap<String, AtomicInteger> requestCounts) {
        this.requestCounts = requestCounts;
    }

    public void setResetTimes(ConcurrentHashMap<String, Long> resetTimes) {
        this.resetTimes = resetTimes;
    }

}
