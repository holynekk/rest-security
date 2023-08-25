package com.course.apisecurity.api.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.course.apisecurity.entity.AuditLogEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Component
public class AuditLogFilter extends OncePerRequestFilter {

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final Logger LOG = LoggerFactory.getLogger(AuditLogFilter.class);

	private static ExecutorService executor = Executors.newSingleThreadExecutor();

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		var cachedHttpRequest = new CachedBodyHttpServletRequest(request);
		var requestBody = IOUtils.toString(cachedHttpRequest.getReader());
		var httpResponse = response;

		chain.doFilter(cachedHttpRequest, response);

		var now = LocalDateTime.now();

		executor.execute(() -> {
			var auditLogEntry = new AuditLogEntry();
			auditLogEntry.setTimestamp(now.toString());
			auditLogEntry.setHeaders(request.getHeader("Authorization"));
			auditLogEntry.setPath(request.getRequestURI());
			auditLogEntry.setQuery(request.getQueryString());
			auditLogEntry.setMethod(request.getMethod());
			auditLogEntry.setRequestBody(requestBody);
			auditLogEntry.setResponseStatusCode(httpResponse.getStatus());

			try {
				var logString = objectMapper.writeValueAsString(auditLogEntry);
				LOG.info(logString);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		});
	}

}
