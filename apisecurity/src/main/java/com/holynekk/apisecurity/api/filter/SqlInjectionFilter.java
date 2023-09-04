package com.holynekk.apisecurity.api.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class SqlInjectionFilter extends OncePerRequestFilter {

    private static final String[] SQL_REGEX = { "(?i)(.*)(\\b)+SELECT(\\b)+\\s.*(\\b)+FROM(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+DROP(\\b)+\\s.*(.*)" };

    private List<Pattern> sqlValidationPatterns;

    public SqlInjectionFilter() {
        sqlValidationPatterns = new ArrayList<Pattern>();

        for(String sqlStatement: SQL_REGEX) {
            Pattern pattern = Pattern.compile(sqlStatement, Pattern.CASE_INSENSITIVE);
            sqlValidationPatterns.add(pattern);
        }
    }

    private boolean isSqlInjectionSafe(String stringToValidate) {
        if (StringUtils.isBlank(stringToValidate)) {
            return true;
        }
        for (Pattern pattern : sqlValidationPatterns) {
            if (pattern.matcher(stringToValidate).find()) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String queryString = URLDecoder.decode(Optional.ofNullable(request.getQueryString()).orElse(StringUtils.EMPTY),
                StandardCharsets.UTF_8);
        String pathVariable = URLDecoder.decode(Optional.ofNullable(request.getRequestURI()).orElse(StringUtils.EMPTY),
                StandardCharsets.UTF_8);
        String requestBody = IOUtils.toString(request.getReader()).replaceAll("\\r\\n|\\r|\\n", StringUtils.EMPTY);

        if (isSqlInjectionSafe(queryString) && isSqlInjectionSafe(pathVariable) && isSqlInjectionSafe(requestBody)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType(MediaType.TEXT_PLAIN_VALUE);
            response.getWriter().print("Bad request, SQL injection detected!");
        }
    }
}
