package org.poormanscastle.products.timeo.stakeholdermatrix.web;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.stakeholdermatrix.services.StakeholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by georg on 03.11.16.
 */
@RequestMapping("/api")
@Controller
public class StakeholderMatrixController {

    private final static Logger logger = Logger.getLogger(StakeholderMatrixController.class);

    @Autowired
    private StakeholderService stakeholderService;

    @RequestMapping(method = RequestMethod.GET, produces = "text/html", value = "/mergeMatrixIntoDataStorage")
    public String importStakeholders() {
        try {
            logger.debug("Executing importStakeholders controller.");
            stakeholderService.mergeData(new URL("http://localhost/Stakeholders.tsv").openStream(), "\\t");
        } catch (IOException e) {
            logger.error(e);
        }
        return "redirect:/stakeholders/";
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = "text/html", value="/uuid")
    public String getUuid(Model modelUi){
        modelUi.addAttribute("uuid", UUID.randomUUID().toString());
        return "api/uuid";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/createStakeholderMatrix")
    public String createStakeholderMatrix(Model model) {
        logger.debug("Executing createStakeholderMatrix controller");
        String matrix = stakeholderService.produceDataMatrix();
        logger.debug(matrix);
        model.addAttribute("content", matrix);

        return "api/stakeholderMatrix";
    }

}
