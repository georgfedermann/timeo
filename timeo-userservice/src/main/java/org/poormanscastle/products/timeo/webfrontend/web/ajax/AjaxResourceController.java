package org.poormanscastle.products.timeo.webfrontend.web.ajax;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.webfrontend.domain.Resource;
import org.poormanscastle.products.timeo.webfrontend.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by georg on 01/05/2017.
 */
@RequestMapping("/resource")
@Controller
public class AjaxResourceController {

    final static Logger logger = Logger.getLogger(AjaxResourceController.class);

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(method = RequestMethod.GET, value = "/byLoginId/{loginId:.+}")
    public @ResponseBody Resource getResourceByLoginId(@PathVariable String loginId) {
        logger.info(StringUtils.join("Got a RESTful WS request for the resource with loginId ", loginId, "."));
        return resourceService.loadResourceByLoginId(loginId);
    }

}
