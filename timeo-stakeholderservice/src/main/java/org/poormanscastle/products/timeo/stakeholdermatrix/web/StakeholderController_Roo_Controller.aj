// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.poormanscastle.products.timeo.stakeholdermatrix.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.poormanscastle.products.timeo.stakeholdermatrix.domain.BusinessSection;
import org.poormanscastle.products.timeo.stakeholdermatrix.domain.Organisation;
import org.poormanscastle.products.timeo.stakeholdermatrix.domain.Stakeholder;
import org.poormanscastle.products.timeo.stakeholdermatrix.web.StakeholderController;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect StakeholderController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String StakeholderController.create(@Valid Stakeholder stakeholder, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, stakeholder);
            return "stakeholders/create";
        }
        uiModel.asMap().clear();
        stakeholder.persist();
        return "redirect:/stakeholders/" + encodeUrlPathSegment(stakeholder.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String StakeholderController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Stakeholder());
        return "stakeholders/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String StakeholderController.show(@PathVariable("id") String id, Model uiModel) {
        uiModel.addAttribute("stakeholder", Stakeholder.findStakeholder(id));
        uiModel.addAttribute("itemId", id);
        return "stakeholders/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String StakeholderController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("stakeholders", Stakeholder.findStakeholderEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) Stakeholder.countStakeholders() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("stakeholders", Stakeholder.findAllStakeholders(sortFieldName, sortOrder));
        }
        return "stakeholders/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String StakeholderController.update(@Valid Stakeholder stakeholder, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, stakeholder);
            return "stakeholders/update";
        }
        uiModel.asMap().clear();
        stakeholder.merge();
        return "redirect:/stakeholders/" + encodeUrlPathSegment(stakeholder.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String StakeholderController.updateForm(@PathVariable("id") String id, Model uiModel) {
        populateEditForm(uiModel, Stakeholder.findStakeholder(id));
        return "stakeholders/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String StakeholderController.delete(@PathVariable("id") String id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Stakeholder stakeholder = Stakeholder.findStakeholder(id);
        stakeholder.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/stakeholders";
    }
    
    void StakeholderController.populateEditForm(Model uiModel, Stakeholder stakeholder) {
        uiModel.addAttribute("stakeholder", stakeholder);
        uiModel.addAttribute("businesssections", BusinessSection.findAllBusinessSections());
        uiModel.addAttribute("organisations", Organisation.findAllOrganisations());
    }
    
    String StakeholderController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
