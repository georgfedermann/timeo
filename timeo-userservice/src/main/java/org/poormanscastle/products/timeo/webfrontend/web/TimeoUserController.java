package org.poormanscastle.products.timeo.webfrontend.web;

import org.poormanscastle.products.timeo.webfrontend.domain.TimeoUser;
import org.poormanscastle.products.timeo.webfrontend.domain.TimeoUserRole;
import org.poormanscastle.products.timeo.webfrontend.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/timeousers")
@Controller
@RooWebScaffold(path = "timeousers", formBackingObject = TimeoUser.class)
public class TimeoUserController {

    @Autowired
    private ResourceService resourceService;


    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") String id, Model uiModel) {
        TimeoUser timeoUser = TimeoUser.findTimeoUser(id);
        uiModel.addAttribute("timeouser", timeoUser);
        uiModel.addAttribute("itemId", id);
        uiModel.addAttribute("resources", resourceService.loadAllResources());
        return "timeousers/show";
    }

    void populateEditForm(Model uiModel, TimeoUser timeoUser) {
        uiModel.addAttribute("timeoUser", timeoUser);
        uiModel.addAttribute("timeouserroles", TimeoUserRole.findAllTimeoUserRoles());
        // manually added these attributes to make UI more convenient.
        uiModel.addAttribute("resources", resourceService.loadAllResources());
    }

}
