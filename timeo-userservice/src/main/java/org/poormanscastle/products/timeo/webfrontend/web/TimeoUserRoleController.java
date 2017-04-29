package org.poormanscastle.products.timeo.webfrontend.web;
import org.poormanscastle.products.timeo.webfrontend.domain.TimeoUserRole;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/timeouserroles")
@Controller
@RooWebScaffold(path = "timeouserroles", formBackingObject = TimeoUserRole.class)
public class TimeoUserRoleController {
}
