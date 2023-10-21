package com.holynekk.apisecurity.api.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//@Component
public abstract class SqlInjectionFilter extends OncePerRequestFilter {

    private static final String[] SQL_REGEX = { "(?i)(.*)(\\b)+SELECT(\\b)+\\s.*(\\b)+FROM(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+DROP(\\b)+\\s.*(.*)" };

    private List<Pattern> sqlValidationPatterns;

    public SqlInjectionFilter() {
        sqlValidationPatterns = new ArrayList<Pattern>();

        for (String sqlStatement : SQL_REGEX) {
            var pattern = Pattern.compile(sqlStatement, Pattern.CASE_INSENSITIVE);
            sqlValidationPatterns.add(pattern);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        CachedBodyHttpServletRequest cachedHttpRequest = new CachedBodyHttpServletRequest((javax.servlet.http.HttpServletRequest) request);

        String queryString = URLDecoder.decode(
                Optional.ofNullable(cachedHttpRequest.getQueryString()).orElse(StringUtils.EMPTY),
                StandardCharsets.UTF_8);
        String pathVariables = URLDecoder.decode(
                Optional.ofNullable(cachedHttpRequest.getRequestURI()).orElse(StringUtils.EMPTY),
                StandardCharsets.UTF_8);
        String requestBody = IOUtils.toString(cachedHttpRequest.getReader()).replaceAll("\\r\\n|\\r|\\n", "");

        if (isSqlInjectionSafe(queryString) && isSqlInjectionSafe(pathVariables) && isSqlInjectionSafe(requestBody)) {
            chain.doFilter((ServletRequest) cachedHttpRequest, response);
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter writer = response.getWriter();
            writer.print("{\"message\":\"SQL injection detected\"}");
        }
    }

    private boolean isSqlInjectionSafe(String stringToValidate) {
        if (StringUtils.isBlank(stringToValidate)) {
            return true;
        }

        for (var pattern : sqlValidationPatterns) {
            if (pattern.matcher(stringToValidate).find()) {
                return false;
            }
        }

        return true;
    }

}