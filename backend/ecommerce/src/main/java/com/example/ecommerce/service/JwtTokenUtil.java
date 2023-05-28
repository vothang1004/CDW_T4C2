package com.example.ecommerce.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.model.JwtUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtTokenUtil {

	private static final long serialVersionUID = -2550185165626007488L;
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	@Value("${jwt.secret}")
	private String secret;
	@Autowired
	private UserService userService;

	// retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException
				| IllegalArgumentException e) {
			return false;
		}
	}

	public long getIdUserByRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		long userId = this.getUserIdFromBearToken(token);
		return userId;
	}

	public User getUserByRequest(HttpServletRequest request) {
		long id = getIdUserByRequest(request);
		return userService.getUserById(id);
	}

	public String preExecuteToken(String token) {
//    	System.out.println("run here? 1");
//	    String token = request.getHeader("Authorization");
//	    System.out.println("run here? 2");
		if (token == null || !token.startsWith("Bearer ")) {
//	        throw new UnauthorizedException("Missing or invalid authorization header");
		}
//		System.out.println("run here? 3");
		token = token.substring(7);
		return token;
	}

	public long getUserIdFromBearToken(String token) {
		return getUserIdFromToken(preExecuteToken(token));
	}

	public long getUserIdFromToken(String token) {
//		System.out.println("getUserIdFromToken: ");
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return claims.get("userId", Long.class);
	}

//    public String getUsernameFromToken(String token) {
//        String username = Jwts.parser()
//                .setSigningKey(secret)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//        return username;
//    }
	// @FunctionalInterface
	public void threadr() {
		Thread t = new Thread(() -> {
			doSomething();
		});
	}
	public void doSomething() {
		System.out.println("do a");
		System.out.println("do b");
	}
	/**
	 * --paramater(int a,int b) -> implementation{ a + b} and return. --lamda is not
	 * create new Object implementation, not annonymous class. TypeInterface in = ()
	 * -> {}; if TypeInterface{void do();} (int a,int b) -> a+b public int plus (int
	 * a,int b) { return a+ b; }
	 * 
	 * @param token
	 * @return
	 */
	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		//Claims::getExpiration  - method reference  - Class::method
		//-- claims -> claims.getExpiration() - ( Claims c) -> c.getExpiration()
		return getClaimFromToken(token,Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);// get claim return T
	}

	// for retrieving any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	// generate token for user
	public String generateToken(UserDetails userDetails) {
		JwtUserDetails jwtUserDetails = (JwtUserDetails) userDetails;
		Map<String, Object> claims = new HashMap<>();
//        claims.put("sub", jwtUserDetails.getEmail());
		claims.put("userId", jwtUserDetails.getId());
		claims.put("username", jwtUserDetails.getUsername());
		claims.put("email", jwtUserDetails.getEmail());

		return doGenerateToken(claims, jwtUserDetails.getEmail());
	}

	// while creating the token -
	// 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
	// 2. Sign the JWT using the HS512 algorithm and secret key.
	// 3. According to JWS Compact Serialization, which is a URL-safe means of
	// representing
	// signed content in a compact format, produce the final JWT token string
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	// validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
