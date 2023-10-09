package com.tenpo.mathfusion.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RateLimitFilterTest {

    @Mock
    private FilterChain filterChain;

    @Mock
    private ServletRequest servletRequest;

    @Mock
    private ServletResponse servletResponse;

    @Mock
    private HttpServletResponse httpServletResponse;

    @InjectMocks
    private RateLimitFilter rateLimitFilter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoFilterAllowed() throws Exception {
        String ipAddress = "127.0.0.1";
        AtomicInteger requestCount = new AtomicInteger(2);

        ConcurrentHashMap<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
        requestCounts.put(ipAddress, requestCount);

        rateLimitFilter.setRequestCounts(requestCounts);

        when(servletRequest.getRemoteAddr()).thenReturn(ipAddress);

        rateLimitFilter.doFilter(servletRequest, servletResponse, filterChain);

        verify(filterChain, times(1)).doFilter(servletRequest, servletResponse);
    }

    @Test
    public void testDoFilterRateLimitExceeded() throws Exception {
        String ipAddress = "127.0.0.1";
        AtomicInteger requestCount = new AtomicInteger(4);

        ConcurrentHashMap<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
        requestCounts.put(ipAddress, requestCount);

        rateLimitFilter.setRequestCounts(requestCounts);

        when(servletRequest.getRemoteAddr()).thenReturn(ipAddress);

        HttpServletResponse realHttpServletResponse = new MockHttpServletResponse();

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(servletResponse.getWriter()).thenReturn(writer);

        rateLimitFilter.doFilter(servletRequest, realHttpServletResponse, filterChain);
        rateLimitFilter.doFilter(servletRequest, realHttpServletResponse, filterChain);
        rateLimitFilter.doFilter(servletRequest, realHttpServletResponse, filterChain);
        rateLimitFilter.doFilter(servletRequest, realHttpServletResponse, filterChain);

        when(servletResponse.getContentType()).thenReturn("application/json");
        assertEquals(429, realHttpServletResponse.getStatus());

        Mockito.verify(filterChain, Mockito.never()).doFilter(servletRequest, servletResponse);
    }
}