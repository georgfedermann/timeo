package org.poormanscastle.products.timeo.task.web;
import org.poormanscastle.products.timeo.task.domain.Priority;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/prioritys")
@Controller
@RooWebScaffold(path = "prioritys", formBackingObject = Priority.class)
public class PriorityController {
}
