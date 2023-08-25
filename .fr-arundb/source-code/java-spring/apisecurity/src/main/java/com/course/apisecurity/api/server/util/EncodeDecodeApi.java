package com.course.apisecurity.api.server.util;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.apisecurity.api.request.util.OriginalStringRequest;
import com.course.apisecurity.util.EncodeDecodeUtil;

@RestController
@RequestMapping("/api")
public class EncodeDecodeApi {

	@GetMapping(value = "/encode/base64", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String encodeBase64(@RequestBody(required = true) OriginalStringRequest original) {
		return EncodeDecodeUtil.encodeBase64(original.getText());
	}

	@GetMapping(value = "/decode/base64", produces = MediaType.TEXT_PLAIN_VALUE)
	public String decodeBase64(@RequestParam(name = "encoded", required = true) String encoded) {
		return EncodeDecodeUtil.decodeBase64(encoded);
	}

	@GetMapping(value = "/encode/url", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String encodeUrl(@RequestBody(required = true) OriginalStringRequest original) {
		return EncodeDecodeUtil.encodeUrl(original.getText());
	}

	@GetMapping(value = "/decode/url", produces = MediaType.TEXT_PLAIN_VALUE)
	public String decodeUrl(@RequestParam(name = "encoded", required = true) String encoded) {
		return EncodeDecodeUtil.decodeUrl(encoded);
	}

}
