package com.forwiz.pms.web.calendar;

import com.forwiz.pms.domain.calendar.CalendarResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Slf4j
@Controller
@RequestMapping("/pms")
public class CalendarController {

    @GetMapping("/calendars")
    public String calendar(@RequestParam(name = "years", required = false)Integer years,
                           @RequestParam(name = "months", required = false)Integer months,
                           RedirectAttributes redirectAttributes){

        if (years==null || months==null){
            years = LocalDate.now().getYear();
            months = LocalDate.now().getMonthValue();
        }
        int year = years;
        int month = months;
        LocalDate firstDate = LocalDate.of(year,month,1);

        int monthDay = firstDate.getDayOfWeek().getValue();
        int monthEnd = firstDate.lengthOfMonth();

        CalendarResponse calendarResponse = new CalendarResponse(year, month, monthDay, monthEnd);
        redirectAttributes.addFlashAttribute("calendar", calendarResponse);

        return "redirect:/pms/calendar";
    }

    @GetMapping("/calendar")
    public String calendarView(@ModelAttribute CalendarResponse calendar){
        return "calendar";
    }
}
