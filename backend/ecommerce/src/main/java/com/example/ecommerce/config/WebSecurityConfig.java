package com.example.ecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.ecommerce.component.JwtAuthenticationEntryPoint;
import com.example.ecommerce.component.JwtRequestFilter;
import com.example.ecommerce.enumeration.UserRole;
import com.example.ecommerce.service.JwtUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

//	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load user for
		// matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
//		auth.userDetailsService(jwtUserDetailsService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this
		httpSecurity.csrf().disable()
				// authenticate this particular request
				.authorizeRequests().antMatchers(HttpMethod.POST, "/authenticate").permitAll()
				// allow access to /users without authentication
//	            .antMatchers(HttpMethod.GET, "/users").permitAll()
				.antMatchers(HttpMethod.POST, "/users/register").permitAll()
				.antMatchers(HttpMethod.POST, "/users/forgot-password").permitAll()
				.antMatchers(HttpMethod.GET, "/users/reset-password").permitAll()
				.antMatchers(HttpMethod.POST, "/users/reset-password/**").permitAll()
				.antMatchers(HttpMethod.GET, "/products").permitAll().antMatchers(HttpMethod.GET, "/products/search")
				.permitAll().antMatchers(HttpMethod.GET, "/products/{product_id}").permitAll()
				.antMatchers(HttpMethod.GET, "/products/latest").permitAll()
				.antMatchers(HttpMethod.GET, "/products/most-viewed-products").permitAll()
				.antMatchers(HttpMethod.GET, "/products/suggested-products/{product_id}").permitAll()
				.antMatchers(HttpMethod.GET, "/products/{product_id}/comments").permitAll()
				.antMatchers(HttpMethod.GET, "/products/{product_id}/ratings").permitAll()
				.antMatchers(HttpMethod.GET, "/products/test/**").permitAll()
				.antMatchers(HttpMethod.GET, "/categories/**").permitAll()
				.antMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")

				// all other requests need to be authenticated
				.anyRequest().authenticated().and()
				// make sure we use stateless session; session won't be used to store user's
				// state.
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

//	@Bean
//	public RoleHierarchy roleHierarchy() {
//		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
//		roleHierarchy.setHierarchy(UserRole.admin.name() + " > " + UserRole.user.name());
//		return roleHierarchy;
//	}
//  @Override
//  protected void configure(HttpSecurity httpSecurity) throws Exception {
//      // We don't need CSRF for this example
//      httpSecurity.csrf().disable()
//              // dont
//      // authenticate this particular request
//      .antMatchers(HttpMethod.POST, "/authenticate").permitAll().
//              // all other requests need to be authenticated
//                      anyRequest().authenticated().and().
//              // make sure we use stateless session; session won't be used to store user's state.
//              exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
//              .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//      // Add a filter to validate the tokens with every request
//      httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//  }
}
