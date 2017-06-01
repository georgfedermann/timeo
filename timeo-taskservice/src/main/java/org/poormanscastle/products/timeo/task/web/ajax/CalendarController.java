package org.poormanscastle.products.timeo.task.web.ajax;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.poormanscastle.products.timeo.task.service.ActivityService;
import org.poormanscastle.products.timeo.task.service.TaskServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ActivityService activityService;

    @RequestMapping(method = RequestMethod.GET, value = "/getCalendarWeekViewByYearAndCalendarWeek/{year}/{calendarWeekNumber}/{masterKey}")
    public String getCalendarView(@PathVariable("masterKey") String masterKey,
                                  @PathVariable("year") int year,
                                  @PathVariable("calendarWeekNumber") int calendarWeekNumber, Model model) {
        logger.info(StringUtils.join("Got WS request for calendar view for user ", masterKey,
                " for the year ", year, " and the calendar week ", calendarWeekNumber, "."));
        model.addAttribute("weekDays", TaskServiceUtils.getCalendarWeek(year, calendarWeekNumber));
        model.addAttribute("year", year);
        model.addAttribute("calendarWeek", calendarWeekNumber);
        model.addAttribute("activities", activityService.getActivitiesForStakeholderAndCalendarWeek(masterKey,
                year, calendarWeekNumber));
        return "ajax/calendar/calendarWeekView";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getCalendarWeekViewForTeamMember/{masterKey}")
    public String getCalenderViewForTeamMember(@PathVariable("masterKey") String masterKey, Model model) {
        logger.info(StringUtils.join("Got WS request for calendar view for user ", masterKey,
                " for the current week of the current year."));
        DateTime today = new DateTime();
        model.addAttribute("weekDays", TaskServiceUtils.getCalendarWeek(today.getYear(), today.getWeekOfWeekyear()));
        model.addAttribute("year", today.getYear());
        model.addAttribute("calendarWeek", today.getWeekOfWeekyear());

        model.addAttribute("activities", activityService.getActivitiesForStakeholderAndCalendarWeek(masterKey,
                today.getYear(), today.getWeekOfWeekyear()));

        return "ajax/calendar/calendarWeekView";
    }

}
