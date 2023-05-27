package com.example.ecommerce.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.JwtTokenUtil;

@Component
public class AuthInterceptor implements HandlerInterceptor {
	@Autowired
//    private TokenService tokenService;
	private JwtTokenUtil tokenService;
	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("Authorization");

		if (token == null || !token.startsWith("Bearer ")) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Missing or invalid token");
			return false;
		}

		// Verify token and check user permissions here...
		// Validate token
		token = token.substring(7);
		if (!tokenService.validateToken(token)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Invalid token");
			return false;
		}

		// Check user permissions
		String username = tokenService.getUsernameFromToken(token);
		String path = request.getRequestURI();
		String method = request.getMethod();
		if (!checkPermissions(username, path, method)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.getWriter().write("You don't have permission to access this resource");
			return false;
		}
		System.out.println("run here?");
		return true;
//        return true;
	}

	private boolean checkPermissions(String username, String path, String method) {
		// Implement your logic here to check user permissions based on their username,
		// path and method
		// You can use a database or some other source to store the permissions and
		// roles of the users
//		System.out.println("username "+username+", path: "+path+", method: "+method);
		User user = userRepository.findByEmail(username);
//		System.out.println("user "+user);

		if (user == null) {
			return false; // User not found
		}

//    	    // Check if user has required permissions for the path and method
//    	    List<Role> roles = user.getRoles();
//    	    for (Role role : roles) {
//    	        List<Permission> permissions = role.getPermissions();
//    	        for (Permission permission : permissions) {
//    	            if (permission.getPath().equals(path) && permission.getMethod().equals(method)) {
//    	                return true; // User has required permissions
//    	            }
//    	        }
//    	    }

//		return false; // User doesn't have required permissions
    	return true; // or false if the user doesn't have the required permissions
	}
}
