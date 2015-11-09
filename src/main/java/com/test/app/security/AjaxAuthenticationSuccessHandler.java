package com.test.app.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.test.app.domain.Authority;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Spring Security success handler, specialized for Ajax requests.
 */
@Component
public class AjaxAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
    	UserDetails x =  SecurityUtils.getUserDetails();
    	/*
    	for (GrantedAuthority y : x.getAuthorities()) {
    		switch(y.getAuthority()) {
    		case AuthoritiesConstants.ADMIN:
    			response.sendRedirect("/hipster1/admin.html");
    			return;
    		case AuthoritiesConstants.DOCTOR:
    			response.sendRedirect("/hipster1/doctor.html");
    			return;
    		case AuthoritiesConstants.HOSPITAL:
    			response.sendRedirect("/hipster1/hospital.html");
    			return;
    		case AuthoritiesConstants.USER:
    			response.sendRedirect("/hipster1/user.html");
    			return;
    		}
    	}
    	*/
    	//response.sendRedirect("/hipster1/index.html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
