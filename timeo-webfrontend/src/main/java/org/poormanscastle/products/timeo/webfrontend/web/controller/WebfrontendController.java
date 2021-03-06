package org.poormanscastle.products.timeo.webfrontend.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.webfrontend.domain.TimeoWebfrontendUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by georg on 01/05/2017.
 */
@Controller
@RequestMapping("/cockpit")
public class WebfrontendController {

    private final static Logger logger = Logger.getLogger(WebfrontendController.class);
    private final static boolean debug = logger.isDebugEnabled();
    private final static boolean info = logger.isInfoEnabled();

    @RequestMapping(method = RequestMethod.GET, produces = "text/html")
    public String serveCockpit(Model modelUi) {
        logger.info(StringUtils.join("Received request for Timeo Cockpit."));
        TimeoWebfrontendUser user = (TimeoWebfrontendUser)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelUi.addAttribute("timeoUser", user);
        modelUi.addAttribute("username", user.getName());
        
        return "cockpit/cockpit";
    }

}
