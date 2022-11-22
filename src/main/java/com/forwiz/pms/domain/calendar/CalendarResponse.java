package com.forwiz.pms.domain.calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CalendarResponse {

    private Integer year;
    private Integer month;
    private Integer monthDay;
    private Integer monthEnd;

}
