package com.gestionqcm.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@WebFilter("/*")
public class AuthFilter implements Filter {
    private static final Set<String> ALLOWED = Set.of(
            "/login", "/logout", "/assets/", "/api/", "/css/", "/js/", "/images/", "/favicon.ico"
    );

    @Override
    public void init(FilterConfig filterConfig) { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI().substring(request.getContextPath().length());

        boolean allow = ALLOWED.stream().anyMatch(path::startsWith);
        if (!allow) {
            HttpSession session = request.getSession(false);
            boolean loggedIn = session != null && session.getAttribute("user") != null;
            if (!loggedIn) {
                response.sendRedirect(request.getContextPath() + "/login?redirect=" + request.getRequestURI());
                return;
            }
        }
        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() { }
}
