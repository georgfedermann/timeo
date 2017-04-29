package org.poormanscastle.products.timeo.webfrontend.web.exceptionhandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by georg on 29/04/2017.
 */
public class LoggingHandlerExceptionResolver implements HandlerExceptionResolver, Ordered {

    private final static Logger logger = Logger.getLogger(LoggingHandlerExceptionResolver.class);

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception exception) {
        logger.error("An error occurred, suck it!", exception);
        // return null to allow other ViewResolvers to be executed, too.
        return null;
    }
}
