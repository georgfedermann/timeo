package org.poormanscastle.products.timeo.task.web;
import org.poormanscastle.products.timeo.task.domain.Activity;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/activitys")
@Controller
@RooWebScaffold(path = "activitys", formBackingObject = Activity.class)
public class ActivityController {
}
