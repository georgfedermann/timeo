package org.poormanscastle.products.timeo.webfrontend.web.exceptionhandler;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * Created by georg on 20/03/2017.
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
        } catch (Throwable exception) {
            logger.error(StringUtils.join("An exception occurred within the timeo project and task management multi-app: ", exception.getMessage()), exception);
            throw exception;
        }
    }

    @Override
    public void destroy() {

    }
}
