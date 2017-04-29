package org.poormanscastle.products.timeo.stakeholdermatrix.web;
import org.poormanscastle.products.timeo.stakeholdermatrix.domain.Stakeholder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/stakeholders")
@Controller
@RooWebScaffold(path = "stakeholders", formBackingObject = Stakeholder.class)
public class StakeholderController {
}
