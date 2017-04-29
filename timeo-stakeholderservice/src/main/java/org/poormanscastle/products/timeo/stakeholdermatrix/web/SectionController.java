package org.poormanscastle.products.timeo.stakeholdermatrix.web;
import org.poormanscastle.products.timeo.stakeholdermatrix.domain.BusinessSection;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/businesssections")
@Controller
@RooWebScaffold(path = "businesssections", formBackingObject = BusinessSection.class)
public class SectionController {
}
