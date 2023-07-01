package employeeant;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

public class Attendance {

    private LocalDateTime time_in;
    private LocalDateTime time_out;
    private int weeklyTotalHours;
    private int weekNumber;
    private int inMonth, outMonth;
    private int inDate, outDate;
    private int inYear, outYear;

    public Attendance(Date timeIn, Date timeOut) {
        this.time_in = timeIn.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.time_out = timeOut.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        // Calculate and set the week number based on timeIn
        LocalDateTime timeInDateTime = getTimeIn();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        this.weekNumber = timeInDateTime.get(weekFields.weekOfWeekBasedYear());

        inMonth = time_in.getMonthValue();
        inDate = time_in.getDayOfMonth();
        inYear = time_in.getYear();

        outDate = time_out.getDayOfMonth();
        outMonth = time_out.getMonthValue();
        outYear = time_out.getYear();
    }

    public LocalDateTime getTimeIn() {
        return this.time_in;
    }

    public int getMonth() {
        return this.time_in.getMonthValue();
    }

    public int getHours() {
        return this.time_out.getHour() - this.time_in.getHour();
    }

    public int getWeekNumber() {
        return this.weekNumber;
    }

    public void setWeek(int week) {
        this.weekNumber = week;
    }

    public int getInMonth() {
        return inMonth;
    }

    public int getOutMonth() {
        return outMonth;
    }

    public int getInDate() {
        return inDate;
    }

    public int getOutDate() {
        return outDate;
    }

    public int getInYear() {
        return inYear;
    }

    public int getOutYear() {
        return outYear;
    }

}
