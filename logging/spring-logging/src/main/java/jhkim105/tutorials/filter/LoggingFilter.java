package jhkim105.tutorials.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {
    private final ServerProperties serverProperties;
    private final List<String> ignoredPaths = List.of("/api-docs", "/swagger", "/version.txt");

    public LoggingFilter(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return ignoredPaths.stream().anyMatch(path -> request.getRequestURI().startsWith(serverProperties.getServlet().getContextPath() + path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper cachingResponseWrapper = new ContentCachingResponseWrapper(response);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper);
        } finally {
            stopWatch.stop();
            log.info(printLog(cachingRequestWrapper, cachingResponseWrapper, stopWatch.getTotalTimeMillis()));
        }

        cachingResponseWrapper.copyBodyToResponse();
    }

    private String printLog(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, long elapsedTime) {
        return String.format("""
                |
                |%s %s (%s) (%dms)
                |>> request parameter: %s
                |>> request body: %s
                |>> response body: %s
                """.trim(),
                request.getMethod(), request.getRequestURI(), HttpStatus.valueOf(response.getStatus()), elapsedTime,
                parameters(request), requestBody(request), responseBody(response));
    }

    private String parameters(ContentCachingRequestWrapper request) {
        return request.getParameterMap().entrySet().stream()
            .map(entry -> entry.getKey() + "=" + String.join(",", entry.getValue()))
            .collect(Collectors.joining("&"));
    }

    private String requestBody(ContentCachingRequestWrapper request) {
        return request.getContentAsString();
    }

    private String responseBody(ContentCachingResponseWrapper response) {
        return new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);
    }

}