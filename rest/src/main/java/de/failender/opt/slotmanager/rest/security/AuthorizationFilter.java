package de.failender.opt.slotmanager.rest.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter extends BasicAuthenticationFilter {


	private final AuthenticationService authenticationService;

	public static final String AUTHORIZATION = "Authorization";

	public AuthorizationFilter(AuthenticationManager auth, AuthenticationService authenticationService) {
		super(auth);
		this.authenticationService = authenticationService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

		authenticationService.authenticate(request.getHeader(AUTHORIZATION));

		chain.doFilter(request, response);


	}


}
