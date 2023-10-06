package com.tenpo.mathfusion.filter;

// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.HttpStatus;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.FilterConfig;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import java.util.concurrent.ConcurrentHashMap;
// import java.util.concurrent.atomic.AtomicInteger;

// import static org.mockito.Mockito.when;

// @SpringBootTest
// public class RateLimitFilterTest {

//     @InjectMocks
//     private RateLimitFilter rateLimitFilter;

//     @Mock
//     private FilterChain filterChain;

//     @Mock
//     private HttpServletRequest request;

//     @Mock
//     private HttpServletResponse response;

//     @Test
//     public void testDoFilter_AllowRequest() throws Exception {
//         // Configurar el filtro y las solicitudes
//         rateLimitFilter.init(Mockito.mock(FilterConfig.class));
//         rateLimitFilter.setRequestCounts(new ConcurrentHashMap<>());
//         rateLimitFilter.setResetTimes(new ConcurrentHashMap<>());

//         when(request.getRemoteAddr()).thenReturn("127.0.0.1");
//         when(filterChain).thenReturn(Mockito.mock(FilterChain.class));

//         // Ejecutar el filtro
//         rateLimitFilter.doFilter(request, response, filterChain);

//         // Verificar que el filtro permita la solicitud
//         Mockito.verify(filterChain).doFilter(request, response);
//     }

//     @Test
//     public void testDoFilter_RateLimitExceeded() throws Exception {
//         // Configurar el filtro y las solicitudes
//         rateLimitFilter.init(Mockito.mock(FilterConfig.class));
//         ConcurrentHashMap<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
//         requestCounts.put("127.0.0.1", new AtomicInteger(RateLimitFilter.MAX_REQUESTS_PER_MINUTE));
//         rateLimitFilter.setRequestCounts(requestCounts);
//         rateLimitFilter.setResetTimes(new ConcurrentHashMap<>());

//         when(request.getRemoteAddr()).thenReturn("127.0.0.1");
//         when(response.getWriter()).thenReturn(Mockito.mock(java.io.PrintWriter.class));

//         // Ejecutar el filtro
//         rateLimitFilter.doFilter(request, response, filterChain);

//         // Verificar que el filtro responda con un estado de demasiadas solicitudes
//         Mockito.verify(response).setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
//     }
// }