package employeeant;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;

public class Employee {
    private String id;
    private String first_name;
    private String last_name;
    private float hourly_rate;
    private float basic_salary;
    private ArrayList<Attendance> time_records = new ArrayList<>();

    public Employee(String id, String firstName, String lastName, float hourlyRate, float basicSalary) {
        this.id = id;
        this.first_name = firstName;
        this.last_name = lastName;
        this.hourly_rate = hourlyRate;
        this.basic_salary = basicSalary;
    }

    public float getBasicSalary() {
        return this.basic_salary;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return this.first_name + " " + this.last_name;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public float getHourlyRate() {
        return hourly_rate;
    }

    public void addTimeRecord(Attendance timeRecord) {
        this.time_records.add(timeRecord);
    }

    public int getTimeRecordsCount() {
        return this.time_records.size();
    }

    public ArrayList<Attendance> getTimeRecords() {
        return this.time_records;
    }

    public void setTimeRecords(ArrayList<Attendance> timeRecords) {
        this.time_records = timeRecords;
    }        

    public int getWeeklyTotalHours_old(int weekNumber) {
        ArrayList<Attendance> filteredTimeRecords = new ArrayList<>();
        int weeklyTotalHours = 0;

        for (int index = 0; index < this.time_records.size(); index++) {
            Attendance timeRecord = this.time_records.get(index);

            if (timeRecord.getWeekNumber() == weekNumber) {
                weeklyTotalHours += timeRecord.getHours();
            }
        }

        return weeklyTotalHours;
    }    
    //////////////////////////////
    
    public int[] getWeeklyTotalHours(int month) {
        
        int[] weeklyHours = new int[6];
        for (int i = 0; i < 6; i++) weeklyHours[i] = 0;

        for (int index = 0; index < this.time_records.size(); index++) {
            Attendance timeRecord = this.time_records.get(index);
            // filter for a particular selected month.
            if (timeRecord.getMonth() == month) {
                // for (int day = 1; day <= timeRecord.getOutDate(); day++ ) {                    
                if (timeRecord.getHours() > 0) {
                    int weekIndex = getWeeklyIndex(timeRecord.getMonth(), timeRecord.getOutDate(), timeRecord.getOutYear());                
                    weeklyHours[weekIndex-1] += timeRecord.getHours();
                }            
                // }                                    
            }
        }

        return weeklyHours;
    }    
    
    public int getWeeklyIndex(int month, int day, int year) {

        // Create a LocalDate object for the given date
        LocalDate date = LocalDate.of(year, month, day);

        // Determine the week number within the month
        WeekFields weekFields = WeekFields.ISO;
        TemporalField weekOfMonthField = weekFields.weekOfMonth();
        int weekNumberInMonth = date.get(weekOfMonthField);

        /* System.out.printf("Week number %d for day %d in the month: %d\n", 
                weekNumberInMonth, day, month); */
        return weekNumberInMonth;
    }
    
    public float getWeeklyGrossSalary(int weeklyHours) {        
        return this.hourly_rate * weeklyHours;
    }
    //////////////////////////////

    public float getWeeklyGrossSalary_old(int weekNumber) {
        int weeklyHours = this.getWeeklyTotalHours_old(weekNumber);
        return this.hourly_rate * weeklyHours;
    }

    public int getMonthlyTotalHours(int monthNumber) {
        ArrayList<Attendance> filteredTimeRecords = new ArrayList<>();
        int monthlyTotalHours = 0;

        for (int index = 0; index < this.time_records.size(); index++) {
            Attendance timeRecord = this.time_records.get(index);

            if (timeRecord.getMonth() == monthNumber) {
                monthlyTotalHours += timeRecord.getHours();
            }
        }

        return monthlyTotalHours;
    }

    public float getMonthlyGrossSalary(int monthNumber) {
        int monthlyHours = this.getMonthlyTotalHours(monthNumber);
        return this.hourly_rate * monthlyHours;
    }

    public float getMonthlyNetSalary(int monthNumber) {
        float grossSalary = this.getMonthlyGrossSalary(monthNumber);
        float netSalary = 0;

        float philhealthContribution = Deductions.computePhilHealth((grossSalary));

        System.out.println("Philhealth is " + philhealthContribution);

        float pagibigContribution = Deductions.computePagIBIG(grossSalary);

        System.out.println("PAGIBIG is " + pagibigContribution);

        float sssContribution = Deductions.computeSSS(grossSalary);

        System.out.println("SSS is " + sssContribution);

        float totalContributions = philhealthContribution + pagibigContribution + sssContribution;

        System.out.println("Total contributions is " + totalContributions);

        float taxableIncome = grossSalary - totalContributions;

        System.out.println("Taxable income " + taxableIncome);

        float withholdingTax = Deductions.computeWithholdingTax(taxableIncome);

        System.out.println("Withholding tax is " + withholdingTax);

        netSalary = taxableIncome - withholdingTax;

        return netSalary;
    }
}
