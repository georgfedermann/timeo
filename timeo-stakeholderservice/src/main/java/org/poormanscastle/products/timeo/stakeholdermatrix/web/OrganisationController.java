package org.poormanscastle.products.timeo.stakeholdermatrix.web;
import org.poormanscastle.products.timeo.stakeholdermatrix.domain.Organisation;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/organisations")
@Controller
@RooWebScaffold(path = "organisations", formBackingObject = Organisation.class)
public class OrganisationController {
}
