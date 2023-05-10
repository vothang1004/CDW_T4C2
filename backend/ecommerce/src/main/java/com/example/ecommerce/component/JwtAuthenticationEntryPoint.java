package com.example.ecommerce.component;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -7858869558953243875L;


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException authException)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
//		System.out.println("autj excep:" +authException.getMessage());
//		authException.printStackTrace();
//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		JSONObject errorJson = new JSONObject();
		errorJson.put("message", authException.getMessage());
		response.getWriter().write(errorJson.toString());
	}
}
