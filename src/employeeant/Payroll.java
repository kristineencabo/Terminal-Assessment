package employeeant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Payroll extends JFrame{
    String employeeDetailsPath = "csv/Employee Data.xlsx - Employee Details.csv";
    String employeeAttendancePath = "csv/Employee Data.xlsx - Attendance Record.csv";
    private JFrame frame;
    private JTextField employeeIdField;
    private JTextField employeeMonthField;
    private JTextField employeenohrField;
    private JTextField salarymonthly;
    private JTextField salarymonthly1;
    private JTextField salarymonthly2;
    private JTextField salarymonthly3;
    private JTextField salarymonthly4;
    
    String csvFile = "Employee Data.xlsx - Attendance Record.csv";
    String targetEmployeeNumber = "";
    String targetEmployeeMonth = "";
    String targetMonth = "";
    String lastField = "";
    float weeklyGrossSalary = 0;
    float weeklyGrossSalary1 = 0;
    float weeklyGrossSalary2 = 0;
    float weeklyGrossSalary3 = 0;
    float weeklyGrossSalary4 = 0;
    float weeklyGrossSalary5 = 0;
    float weeklyGrossSalary6 = 0;
    int numweek = 0;
    int monthlygross = 0;
    double ph = 0;
    double sss = 0;
    double tax = 0;
    double pagibig = 0;
    
    public Payroll() {
        try {
            frame = new JFrame("Payroll System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 900);
            frame.setLayout(new BorderLayout());
            frame.getContentPane().setBackground(new Color(240, 240, 240));
            
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(20, 1, 40, 10));
            inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            inputPanel.setBackground(Color.white);
            
            JLabel employeeIdLabel = new JLabel("Employee ID:");
            employeeIdField = new JTextField(15);
            employeeIdField.setPreferredSize(new Dimension(150, employeeIdField.getPreferredSize().height));
            
            JLabel employeeMonthLabel = new JLabel("Month:");
            employeeMonthField = new JTextField(15);
            employeeMonthField.setPreferredSize(new Dimension(150, employeeMonthField.getPreferredSize().height));
            
            JLabel employeenohrLabel = new JLabel("Monthly hours work:");
            // employeenohrField = new JTextField(15);
            // employeenohrField.setPreferredSize(new Dimension(150, employeeMonthField.getPreferredSize().height));
            
            JLabel salarymonthly = new JLabel("Monthly gross salary: ");
            JLabel week1salary = new JLabel("    Week 1: ");
            JLabel week2salary = new JLabel("    Week 2: ");
            JLabel week3salary = new JLabel("    Week 3: ");
            JLabel week4salary = new JLabel("    Week 4: ");
            JLabel week5salary = new JLabel("    Week 5: ");
            JLabel week6salary = new JLabel("    Week 6: ");
            JLabel salarymonthlynet = new JLabel("Monthly net salary: ");
            
            JLabel statutories = new JLabel("Statutories: ");
            JLabel statutories1 = new JLabel("  PAG-IBIG: ");
            JLabel statutories2 = new JLabel("  PhilHealth: ");
            JLabel statutories3 = new JLabel("  SSS: ");
            JLabel statutories4 = new JLabel("  Withholding Tax: ");
            
            inputPanel.add(employeeIdLabel);
            inputPanel.add(employeeIdField);
            inputPanel.add(employeeMonthLabel);
            inputPanel.add(employeeMonthField);
            inputPanel.add(employeenohrLabel);
            // inputPanel.add(employeenohrField);
            inputPanel.add(salarymonthly);
            inputPanel.add(week1salary);
            inputPanel.add(week2salary);
            inputPanel.add(week3salary);
            inputPanel.add(week4salary);
            inputPanel.add(week5salary);
            inputPanel.add(week6salary);
            inputPanel.add(salarymonthlynet);
            inputPanel.add(statutories);
            inputPanel.add(statutories1);
            inputPanel.add(statutories2);
            inputPanel.add(statutories3);
            inputPanel.add(statutories4);
            
            frame.add(inputPanel, BorderLayout.CENTER);
            frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));            
            JButton calculateButton = new JButton("Calculate");
            calculateButton.setPreferredSize(new Dimension(120, 30));
            calculateButton.setBackground(new Color(0, 153, 0));
            calculateButton.setForeground(Color.white);
            calculateButton.setFocusPainted(false);
            calculateButton.setFont(new Font("Arial", Font.BOLD, 14));
            JButton leaveAppButton = new JButton("Leave Application Form");
            leaveAppButton.setPreferredSize(new Dimension(120, 30));
            leaveAppButton.setBackground(new Color(0, 153, 0));
            leaveAppButton.setForeground(Color.white);
            leaveAppButton.setFocusPainted(false);
            leaveAppButton.setFont(new Font("Arial", Font.BOLD, 14));
            
            frame.add(calculateButton, BorderLayout.SOUTH);
            frame.add(leaveAppButton, BorderLayout.SOUTH);
            
            ArrayList<Employee> employees = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(employeeDetailsPath));
            boolean isFirstLine = true;
            String row = br.readLine();
            while (row != null) {
                try {
                    if (isFirstLine) {
                        isFirstLine = false;
                        row = br.readLine();
                        continue; // skip header
                    }
                    String[] rowData = row.split(",");
                    ArrayList<String> processedRow = new ArrayList<String>();
                    String concatenator = "";
                    boolean concatOpen = false;
                    for (int index = 0; index < rowData.length; index++) {
                        if (!rowData[index].contains("\"") && !concatOpen) {
                            processedRow.add(rowData[index]);
                        } else {
                            if (rowData[index].charAt(0) == '"' && !concatOpen) {
                                concatOpen = true;
                            } else if (rowData[index].charAt(rowData[index].length() - 1) == '"' && concatOpen) {
                                concatOpen = false;
                            }
                            
                            concatenator += rowData[index].replace("\"", "");
                            
                            if (!concatOpen) {
                                processedRow.add(concatenator);
                                concatenator = "";
                            }
                        }
                    }
                    
                    Employee newEmployee = new Employee(processedRow.get(0), processedRow.get(2), processedRow.get(1), Float.parseFloat(processedRow.get(18)), Float.parseFloat(processedRow.get(13)));
                    employees.add(newEmployee);
                    
                    row = br.readLine();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
            
            leaveAppButton.addActionListener((ActionEvent e) -> {
                dispose();
                LeaveApplication leaveForm = new LeaveApplication();
                leaveForm.setVisible(true);
            });
            
            
            calculateButton.addActionListener((ActionEvent e) -> {
                Employee selectedEmployee = null;
                
                for (Employee employee : employees) {
                    // employeeIdField.setText("10001"); // added temporary
                    if (employee.getId().equals(employeeIdField.getText())) {
                        selectedEmployee = employee;
                        break;
                    }
                }
                
                if (selectedEmployee != null) {
                    try {
                        System.out.println(selectedEmployee.getFullName() + " with hourly rate of " + selectedEmployee.getHourlyRate());
                        System.out.println(selectedEmployee.getFullName() + " with basic salary of " + selectedEmployee.getBasicSalary());
                        
                        BufferedReader br2 = new BufferedReader(new FileReader(employeeAttendancePath));
                        
                        String row2 = br2.readLine();
                        boolean pastFirstLine2 = false;
                        
                        Date timeIn = null;
                        Date timeOut = null;
                        while (row2 != null) {
                            if (!pastFirstLine2) {
                                pastFirstLine2 = true;
                                row2 = br2.readLine();
                                continue;
                            }
                            
                            String[] rowData = row2.split(",");
                            
                            if (!rowData[0].equals(employeeIdField.getText())) {
                                row2 = br2.readLine();
                                continue;
                            }
                            
                            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm");
                            String timeInString = rowData[3] + " " + rowData[4];
                            String timeOutString = rowData[3] + " " + rowData[5];
                            
                            timeIn = formatter.parse(timeInString);
                            timeOut = formatter.parse(timeOutString);
                            
                            Attendance attendanceRecord = new Attendance(timeIn, timeOut);
                            selectedEmployee.addTimeRecord(attendanceRecord);
                            
                            row2 = br2.readLine();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Payroll.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        Logger.getLogger(Payroll.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                float philhealthContribution = 0;
                float pagibigContribution = 0;
                float totalContributions = 0;
                float sssContribution = 0;
                targetEmployeeNumber = employeeIdField.getText();
                // employeeMonthField.setText("1"); // added temporary
                int monthNumber = Integer.parseInt(employeeMonthField.getText());                
                int monthlyTotalHours = selectedEmployee.getMonthlyTotalHours(monthNumber);
                float monthlyGrossSalary = selectedEmployee.getMonthlyGrossSalary(monthNumber);
                // Found the matching employee number, no need to continue reading the file
                float monthlyNetSalary = selectedEmployee.getMonthlyNetSalary(monthNumber);
                monthlygross = (int) monthlyGrossSalary;
                float netSalary = 0;
                philhealthContribution = Deductions.computePhilHealth((monthlyGrossSalary));
                pagibigContribution = Deductions.computePagIBIG(monthlyGrossSalary);
                sssContribution = Deductions.computeSSS(monthlyGrossSalary);
                totalContributions = philhealthContribution + pagibigContribution + sssContribution;
                float taxableIncome = 0;
                taxableIncome = monthlyGrossSalary - totalContributions;
                float withholdingTax = 0;
                withholdingTax = Deductions.computeWithholdingTax(taxableIncome);
                netSalary = taxableIncome - withholdingTax;
                ph = philhealthContribution;
                sss = sssContribution;
                pagibig = pagibigContribution;
                tax = withholdingTax;
                salarymonthly.setText("");
                week1salary.setText("");
                week2salary.setText("");
                week3salary.setText("");
                week4salary.setText("");
                week5salary.setText("");
                week6salary.setText("");
                employeenohrLabel.setText("");
                employeenohrLabel.setText("Monthly hours work: " + monthlyTotalHours);
                salarymonthly.setText("Monthly gross salary: Php. " + monthlygross);
                
                int[] weeklyTotalHours = new int[6]; for (int i=0; i<6; ++i) weeklyTotalHours[i] = 0;
                weeklyTotalHours = selectedEmployee.getWeeklyTotalHours(monthNumber);
                weeklyGrossSalary1 = selectedEmployee.getWeeklyGrossSalary(weeklyTotalHours[0]);
                weeklyGrossSalary2 = selectedEmployee.getWeeklyGrossSalary(weeklyTotalHours[1]);
                weeklyGrossSalary3 = selectedEmployee.getWeeklyGrossSalary(weeklyTotalHours[2]);
                weeklyGrossSalary4 = selectedEmployee.getWeeklyGrossSalary(weeklyTotalHours[3]);
                weeklyGrossSalary5 = selectedEmployee.getWeeklyGrossSalary(weeklyTotalHours[4]);
                weeklyGrossSalary6 = selectedEmployee.getWeeklyGrossSalary(weeklyTotalHours[5]);                
                week1salary.setText("    Week 1: Total hours: " + weeklyTotalHours[0] + ", Weekly Gross: Php. " + (weeklyGrossSalary1));
                week2salary.setText("    Week 2: Total hours: " + weeklyTotalHours[1] + ", Weekly Gross: Php. " + (weeklyGrossSalary2));
                week3salary.setText("    Week 3: Total hours: " + weeklyTotalHours[2] + ", Weekly Gross: Php. " + (weeklyGrossSalary3));
                week4salary.setText("    Week 4: Total hours: " + weeklyTotalHours[3] + ", Weekly Gross: Php. " + (weeklyGrossSalary4));
                week5salary.setText("    Week 5: Total hours: " + weeklyTotalHours[4] + ", Weekly Gross: Php. " + (weeklyGrossSalary5));
                week6salary.setText("    Week 6: Total hours: " + weeklyTotalHours[5] + ", Weekly Gross: Php. " + (weeklyGrossSalary6));
                double monthlysalary = 0;
                monthlysalary = monthlygross - (ph + sss + pagibig + tax);
                totalContributions = philhealthContribution + pagibigContribution + sssContribution;
                salarymonthlynet.setText("Monthly net salary: Php. "+ monthlyNetSalary);
                statutories.setText("Total Contributions: Php. " + totalContributions);
                statutories1.setText("  PAG-IBIG: Php. " + pagibig);
                statutories2.setText("  PhilHealth: Php. " + ph);
                statutories3.setText("  SSS: Php. " + sss);
                statutories4.setText("  Withholding Tax: Php. " + tax);
                frame.revalidate();
                frame.repaint();
                frame.setVisible(true);
                
            });
            
            frame.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(Payroll.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Payroll::new);
    }
    
}
