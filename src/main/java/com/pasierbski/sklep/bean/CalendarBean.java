package com.pasierbski.sklep.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

@Named("calendarBean")
@SessionScoped
public class CalendarBean implements Serializable {
    private LocalDate currentDate;

    public CalendarBean() {
        this.currentDate = LocalDate.now(); // Ustawiamy na dzisiejszy dzień
    }

    public String getMonthName() {
        int month = currentDate.getMonthValue();
        String[] monthNames = { "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień",
                "Wrzesień", "Październik", "Listopad", "Grudzień" };
        return monthNames[month - 1];

    }

    public int getYear() {
        return currentDate.getYear();
    }

    public int getDaysInMonth() {
        return YearMonth.from(currentDate).lengthOfMonth();
    }

    public int getFirstDayOfWeek() {
        // Zwraca dzień tygodnia (1 = Poniedziałek, 7 = Niedziela)
        int dayOfWeek = currentDate.withDayOfMonth(1).getDayOfWeek().getValue();
        return (dayOfWeek == 7 ? 0 : dayOfWeek) - 1; // Niedziela na końcu
    }

    public int getToday() {
        currentDate = LocalDate.now();
        return currentDate.getDayOfMonth();
    }
}
