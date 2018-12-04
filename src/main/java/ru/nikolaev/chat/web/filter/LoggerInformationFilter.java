package ru.nikolaev.chat.web.filter;

import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LoggerInformationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        MDC.put("sessionId", httpServletRequest.getSession().getId().substring(0, 6));
        filterChain.doFilter(servletRequest, servletResponse);
        MDC.remove("sessionId");
    }

    @Override
    public void destroy() {

    }
}
