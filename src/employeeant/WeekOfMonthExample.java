package employeeant;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;

public class WeekOfMonthExample {
    public static void main(String[] args) {
        for (int day = 1; day <= 31; day++ ) {
            getWeekNumber(1, day, 2022);
        }
    }
    
    static void getWeekNumber(int month, int dayOfMonth, int year) {
        // int year = 2023;
        // int month = 6; // June (months are 1-based)
        // int dayOfMonth = 15; // Example day of the month

        // Create a LocalDate object for the given date
        LocalDate date = LocalDate.of(year, month, dayOfMonth);

        // Determine the week number within the month
        WeekFields weekFields = WeekFields.ISO;
        TemporalField weekOfMonthField = weekFields.weekOfMonth();
        int weekNumberInMonth = date.get(weekOfMonthField);        

        System.out.printf("for the Month of %s Day %2d (%-9s) belongs to Week %d\n", 
                date.getMonth(), dayOfMonth, date.getDayOfWeek(), weekNumberInMonth);
    }
}
