package org.poormanscastle.products.timeo.task.web.ajax;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * handles requests for the calendar view of tasks, activities and scheduled items.
 * Created by georg on 22/05/2017.
 */
@Controller
public class CalendarController {

    final static Logger logger = Logger.getLogger(CalendarController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/getCalendarWeekView/{calendarWeekNumber}/{teamMemberId}")
    public String getCalendarView(@PathVariable("teamMemberId") String teamMemberId,
                                  @PathVariable("calendarWeekNumber") String calendarWeekNumber, Model model) {
        
        return "ajax/calendar/calendarWeekView";
    }

}
