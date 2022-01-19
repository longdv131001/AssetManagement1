package com.nashtech.rookie.assetmanagement.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

import org.springframework.stereotype.Component;

@Component
public class DateUtils {
	public static String dateToString( String pattern, LocalDate date ){
        DateTimeFormatter fm = DateTimeFormatter.ofPattern(pattern);
        return date.format(fm);
    }
	
	public static boolean isWeekend(final LocalDate ld) 
    {
        DayOfWeek day = DayOfWeek.of(ld.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
    }
}
