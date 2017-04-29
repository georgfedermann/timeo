package org.poormanscastle.products.timeo.task.web.exceptionhandler;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

/**
 * Created by georg on 23/02/2017.
 */
public class LoggingExceptionFilter implements Filter {

    private final static Logger logger = Logger.getLogger(LoggingExceptionFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception exception) {
            logger.error("An exception occurred.", exception);
            throw exception;
        }
    }

    @Override
    public void destroy() {

    }
    
}
