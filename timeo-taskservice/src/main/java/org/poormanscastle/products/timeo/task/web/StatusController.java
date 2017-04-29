package org.poormanscastle.products.timeo.task.web;
import org.poormanscastle.products.timeo.task.domain.Status;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/statuses")
@Controller
@RooWebScaffold(path = "statuses", formBackingObject = Status.class)
public class StatusController {
}
