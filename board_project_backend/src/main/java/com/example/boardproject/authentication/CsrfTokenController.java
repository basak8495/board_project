package com.example.boardproject.authentication;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class CsrfTokenController {
	
	@GetMapping("/csrf-token")
	public ResponseEntity<Map<String, String>> getCsrfToken(HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("csrfToken", csrfToken.getToken());

        return ResponseEntity.ok(tokenMap);
    }
}
