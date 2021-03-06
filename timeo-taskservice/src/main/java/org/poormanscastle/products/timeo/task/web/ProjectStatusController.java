package org.poormanscastle.products.timeo.task.web;
import org.poormanscastle.products.timeo.task.domain.ProjectStatus;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/projectstatuses")
@Controller
@RooWebScaffold(path = "projectstatuses", formBackingObject = ProjectStatus.class)
public class ProjectStatusController {
}
