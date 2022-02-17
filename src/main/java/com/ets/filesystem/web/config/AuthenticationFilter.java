package com.ets.filesystem.web.config;

import com.ets.filesystem.service.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.web.filter.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class AuthenticationFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse
            response, FilterChain filterChain) throws IOException,
            ServletException {
        Authentication authentication =
                AuthenticationService.getAuthentication((HttpServletRequest)request
                );
        SecurityContextHolder.getContext().
                setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
