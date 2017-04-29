package org.poormanscastle.products.timeo.stakeholdermatrix.web.ajax;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.stakeholdermatrix.domain.Stakeholder;
import org.poormanscastle.products.timeo.stakeholdermatrix.services.StakeholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * serves request outside of the Tiles framework and is predestined for
 * AJAX calls.
 * http://localhost:8080/stakeholdermatrix/pages/matrix/datamatrix
 * Created by georg on 15.11.16.
 */
@RequestMapping("/matrix")
@Controller
public class PagesController {

    public final static Logger logger = Logger.getLogger(PagesController.class);

    @Autowired
    private StakeholderService stakeholderService;

    @ExceptionHandler(Exception.class)
    public void handleExceptions(Exception exception) {
        logger.error(StringUtils.join("An exception occurred: ", exception.getMessage()), exception);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "text/html", value = "/datamatrix")
    public String getDatamatrix() {
        return "businesscard/stakeholderMatrix";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stakeholder/{stakeholderKey}")
    public
    @ResponseBody
    Stakeholder getStakeholderByKey(@PathVariable String stakeholderKey) {
        logger.info(StringUtils.join("Got a REST-ful WS request for the stakeholder with master key ", stakeholderKey));
        List<Stakeholder> stakeholders = Stakeholder.findStakeholdersByMasterKey(stakeholderKey).getResultList();
        if (stakeholders.size() == 1) {
            logger.info(StringUtils.join("Found stakeholder ", stakeholders.get(0).toString()));
            return stakeholders.get(0);
        } else if (stakeholders.size() > 1) {
            logger.error(StringUtils.join("Found more than one stakeholder for masterKey ", stakeholderKey));
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stakeholders")
    public
    @ResponseBody
    List<Stakeholder> getAllStakehoolders() {
        logger.info(StringUtils.join("Got a request for all stakeholders."));
        return Stakeholder.findAllStakeholders();
    }

    /**
     * delivers a business card for the stakeholder with the given email address.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/businesscardForEmail/{email:.+}")
    public String getBusinessCardForEmail(@PathVariable String email, Model modelUi) {
        logger.info(StringUtils.join("Got a REST-ful WS request for the businessCard of stakeholder with email '", email, "'."));
        Stakeholder stakeholder = stakeholderService.findStakeholderByEmail(email);
        Stakeholder reportsTo = null;
        if (stakeholder == null) {
            logger.info(StringUtils.join("No stakeholder found for email '", email, "'."));
        } else {
            reportsTo = stakeholderService.findStakeholderByMasterKey(stakeholder.getReportsTo());
        }
        modelUi.addAttribute("stakeholder", stakeholder);
        modelUi.addAttribute("reportsTo", reportsTo);

        return "businesscard/businesscard";
    }

    /**
     * delivers a business card format for a given stakeholder.
     *
     * @param stakeholderKey
     * @param modelUi
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/businesscard/{stakeholderKey}")
    public String getBusinesscard(@PathVariable String stakeholderKey, Model modelUi) {
        // load stakeholder information from database
        List<Stakeholder> stakeholders = Stakeholder.findStakeholdersByMasterKey(stakeholderKey).getResultList();
        if (stakeholders.size() != 1) {
            String errMsg = StringUtils.join("Cannot create business card for stakeholder with masterKey ", stakeholderKey,
                    ". Number of stakeholders found for that key was not 1 but ", stakeholders.size());
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        Stakeholder stakeholder = stakeholders.get(0);
        checkArgument(stakeholder != null, StringUtils.join("The stakeholder for given stakeholderKey ", stakeholderKey, " was null!"));
        // if stakeholder has the reportsTo flag set, load reportsTo data, too
        // 0 means, the stakeholder is in top level management
        // -1 means, it is unknown who this stakeholder reports to.
        Stakeholder reportsTo = null;
        if (!stakeholder.getReportsTo().equals("-")) {
            stakeholders = Stakeholder.findStakeholdersByMasterKey(stakeholder.getReportsTo()).getResultList();
            if (stakeholders.size() == 1) {
                reportsTo = stakeholders.get(0);
            }
        }
        modelUi.addAttribute("stakeholder", stakeholder);
        modelUi.addAttribute("reportsTo", reportsTo);
        return "businesscard/businesscard";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/reportsTo/{stakeholderKey}")
    public String reportsTo(@PathVariable String stakeholderKey, Model modelUi) {
        // load the stakeholder
        List<Stakeholder> stakeholders = Stakeholder.findStakeholdersByMasterKey(stakeholderKey).getResultList();
        if (stakeholders.size() != 1) {
            String errMsg = StringUtils.join("Cannot identify stakeholder for key ", stakeholderKey,
                    ". Number of stakeholders found for that key was not 1 but ", stakeholders.size());
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        // now load the stakeholder this stakeholder reports to
        stakeholders = Stakeholder.findStakeholdersByMasterKey(stakeholders.get(0).getReportsTo()).getResultList();
        if (stakeholders.size() != 1) {
            String errMsg = StringUtils.join("Cannot identify reportsTo from reportsTo key ", stakeholders.get(0).getReportsTo(),
                    ". Number of stakeholders found for that key was not 1 but ", stakeholders.size());
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        modelUi.addAttribute("stakeholder", stakeholders.get(0));
        return "businesscard/reportsTo";
    }

}
