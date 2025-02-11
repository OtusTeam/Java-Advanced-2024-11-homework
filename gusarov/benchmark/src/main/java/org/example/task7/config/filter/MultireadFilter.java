package org.example.task7.config.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class MultireadFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        MultiReadHttpServletRequest requestWrapper = new MultiReadHttpServletRequest((HttpServletRequest) servletRequest);
        filterChain.doFilter(requestWrapper, servletResponse);
    }
}

