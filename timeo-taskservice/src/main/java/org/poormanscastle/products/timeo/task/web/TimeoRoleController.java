package org.poormanscastle.products.timeo.task.web;
import org.poormanscastle.products.timeo.task.domain.TimeoRole;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/timeoroles")
@Controller
@RooWebScaffold(path = "timeoroles", formBackingObject = TimeoRole.class)
public class TimeoRoleController {
}
