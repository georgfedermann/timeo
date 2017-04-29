// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.poormanscastle.products.timeo.webfrontend.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.poormanscastle.products.timeo.webfrontend.domain.TimeoUser;
import org.poormanscastle.products.timeo.webfrontend.domain.TimeoUserRole;
import org.poormanscastle.products.timeo.webfrontend.web.TimeoUserRoleController;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect TimeoUserRoleController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String TimeoUserRoleController.create(@Valid TimeoUserRole timeoUserRole, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, timeoUserRole);
            return "timeouserroles/create";
        }
        uiModel.asMap().clear();
        timeoUserRole.persist();
        return "redirect:/timeouserroles/" + encodeUrlPathSegment(timeoUserRole.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String TimeoUserRoleController.createForm(Model uiModel) {
        populateEditForm(uiModel, new TimeoUserRole());
        return "timeouserroles/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String TimeoUserRoleController.show(@PathVariable("id") String id, Model uiModel) {
        uiModel.addAttribute("timeouserrole", TimeoUserRole.findTimeoUserRole(id));
        uiModel.addAttribute("itemId", id);
        return "timeouserroles/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String TimeoUserRoleController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("timeouserroles", TimeoUserRole.findTimeoUserRoleEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) TimeoUserRole.countTimeoUserRoles() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("timeouserroles", TimeoUserRole.findAllTimeoUserRoles(sortFieldName, sortOrder));
        }
        return "timeouserroles/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String TimeoUserRoleController.update(@Valid TimeoUserRole timeoUserRole, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, timeoUserRole);
            return "timeouserroles/update";
        }
        uiModel.asMap().clear();
        timeoUserRole.merge();
        return "redirect:/timeouserroles/" + encodeUrlPathSegment(timeoUserRole.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String TimeoUserRoleController.updateForm(@PathVariable("id") String id, Model uiModel) {
        populateEditForm(uiModel, TimeoUserRole.findTimeoUserRole(id));
        return "timeouserroles/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String TimeoUserRoleController.delete(@PathVariable("id") String id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        TimeoUserRole timeoUserRole = TimeoUserRole.findTimeoUserRole(id);
        timeoUserRole.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/timeouserroles";
    }
    
    void TimeoUserRoleController.populateEditForm(Model uiModel, TimeoUserRole timeoUserRole) {
        uiModel.addAttribute("timeoUserRole", timeoUserRole);
        uiModel.addAttribute("timeousers", TimeoUser.findAllTimeoUsers());
    }
    
    String TimeoUserRoleController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
