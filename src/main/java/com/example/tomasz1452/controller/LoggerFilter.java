package com.example.tomasz1452.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoggerFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoggerFilter.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request instanceof HttpServletRequest){
            var httpRequest = (HttpServletRequest) request;
            logger.info("[doFilter]"+httpRequest.getMethod() + httpRequest.getRequestURI());
            /**
             * Ten logger wyświetla nazwę metody oraz adres uri jeśli zostanie wywolane
             * jakieś rządanie do serwera.
             */
        }
        chain.doFilter(request, response);
//        logger.info("[doFilter] 2");
    }
}
