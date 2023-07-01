package employeeant;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LeaveApplication extends JFrame {

    static final int NUMBER_OF_SICK_LEAVE = 5;
    static final int NUMBER_OF_VACATION_LEAVE = 10;
    static final int NUMBER_OF_EMERGENCY_LEAVE = 5;
    static final int LET_CONSTRUCTOR_DECIDE = -1;
    static JTextField remainingLeaveField;

    public LeaveApplication() {
        JFrame frame = new JFrame("Leave Application Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setSize(600, 700);
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(11, 2, 10, 10));
        inputPanel.setBackground(Color.WHITE);

        JLabel leaveTypeLabel = new JLabel("Leave Type:");
        JComboBox<String> leaveTypeComboBox = new JComboBox<>(new String[]{"Sick", "Emergency", "Vacation"});
        
        JLabel idNumberLabel = new JLabel("ID Number:");
        JLabel startDateLabel = new JLabel("Start Date: ");
        JLabel endDateLabel = new JLabel("End Date: ");
        JLabel remainingLeaveLabel = new JLabel("Remaining Leave:");
        
        JTextField idNumberField = new JTextField();
        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();
        remainingLeaveField = new JTextField();        
        remainingLeaveField.setEditable(false);

        inputPanel.add(idNumberLabel); inputPanel.add(idNumberField);
        inputPanel.add(leaveTypeLabel); inputPanel.add(leaveTypeComboBox);
        inputPanel.add(startDateLabel); inputPanel.add(startDateField);
        inputPanel.add(endDateLabel); inputPanel.add(endDateField);
        inputPanel.add(remainingLeaveLabel); inputPanel.add(remainingLeaveField);

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(0, 153, 0));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String idNumber = idNumberField.getText();
                String leaveType = leaveTypeComboBox.getSelectedItem().toString();
                String dateStart = startDateField.getText();
                String dateEnd = endDateField.getText();
                
                submitOperation(idNumber, leaveType, dateStart, dateEnd);
            }            
        });
        
        JButton clearButton = new JButton("Clear");
        clearButton.setBackground(new Color(0, 153, 0));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                idNumberField.setText("");
                startDateField.setText("");
                endDateField.setText("");
            }            
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(inputPanel, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);        
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
        frame.pack();
    } // end of constructor
    
    private void submitOperation(String idNumber, String leaveType, String dateStart, String dateEnd) {
        String filePath = "csv/leaveapp.csv";        
        
        // Add new leave application to CSV file
        EmployeeLeave newEmployeeLeave = new EmployeeLeave(idNumber, leaveType, dateStart, dateEnd, LET_CONSTRUCTOR_DECIDE);        
        addNewCSV(filePath, newEmployeeLeave);
        
        // Read employee data from CSV file
        List<EmployeeLeave> readEmployees = readCSV(filePath);
        for (EmployeeLeave employee : readEmployees) System.out.println(employee);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LeaveApplication();
            }
        });
    }

    
    public static void main_test(String[] args) {
        String filePath = "appleave.csv";

         // Read employee data from CSV file
        List<EmployeeLeave> readEmployees = readCSV(filePath);
        for (EmployeeLeave employee : readEmployees) {
            System.out.println(employee);
        }
        
        /*
        if (readEmployees.size() < 3) {
            // Create sample employee data
            List<EmployeeLeave> employees = new ArrayList<>();        
            employees.add(new EmployeeLeave("XXXXX", "Sick",      "2022-01-01", "2022-01-06", NUMBER_OF_SICK_LEAVE));
            employees.add(new EmployeeLeave("XXXXX", "Emergency", "2022-01-01", "2022-01-06", NUMBER_OF_EMERGENCY_LEAVE));
            employees.add(new EmployeeLeave("XXXXX", "Vacation",  "2022-02-01", "2022-02-11", NUMBER_OF_VACATION_LEAVE));

            // Write employee data to CSV file
            writeCSV(filePath, employees);
        }
        */

        // Update employee data in CSV file
        // EmployeeLeave updatedEmployee = new EmployeeLeave("E001", "Emergency", "2022-01-01", "2022-01-06");
        // updateCSV(filePath, updatedEmployee);
        
        // Add new leave application to CSV file
        EmployeeLeave newEmployeeLeave = new EmployeeLeave("10001", "Vacation", "2022-01-01", "2022-01-10", LET_CONSTRUCTOR_DECIDE);        
        addNewCSV(filePath, newEmployeeLeave);
    }
    
    public static void addNewCSV(String filePath, EmployeeLeave newEmployeeLeave) {
        List<EmployeeLeave> employees = readCSV(filePath);

        // verify id entry from CSV
        EmployeeLeave foundEntry = null;
        for (int i = employees.size()-1; i >= 0; i--) {
            EmployeeLeave employee = employees.get(i);
            if (employee.getEmployeeID().equals(newEmployeeLeave.getEmployeeID()) 
                && employee.getType().equalsIgnoreCase(newEmployeeLeave.getType())) {
                foundEntry = employees.get(i); // employees.set(i, newEmployeeLeave);
                break;
            }
        }
        
        if (foundEntry == null) {            
            // validate if days requested is OK
            if (newEmployeeLeave.isValid()) {
                int newDaysAllowed = newEmployeeLeave.getRemainingDays();
                newEmployeeLeave.setDaysAllowed(newDaysAllowed);
                employees.add(newEmployeeLeave);
                writeCSV(filePath, employees);                
                // System.out.println("Leave Application added successfully.");
                JOptionPane.showMessageDialog(null, "Leave Application added successfully.");
            } else {
                String errorMsg = String.format("Please adjust your date entry. Leave Credits: %d", 
                        newEmployeeLeave.getDaysAllowed());
                JOptionPane.showMessageDialog(null, errorMsg);
            }            
        } 
        // if existing...
        else { 
            // retrieve days remaining // validate against days requested
            if (foundEntry.getDaysAllowed() >= newEmployeeLeave.getDaysDuration()) {
                // add addt'l entry
                int newDaysAllowed = foundEntry.getDaysAllowed() - newEmployeeLeave.getDaysDuration();
                newEmployeeLeave.setDaysAllowed(newDaysAllowed);
                employees.add(newEmployeeLeave);
                writeCSV(filePath, employees);                
                // System.out.println("Leave Application added successfully.");
                JOptionPane.showMessageDialog(null, "Leave Application added successfully.");
            } else {
                String errorMsg = String.format("Something went wrong. Leave Credits: %d", 
                        foundEntry.getDaysAllowed());
                JOptionPane.showMessageDialog(null, errorMsg);
            }                       
        }       
        remainingLeaveField.setText("" + newEmployeeLeave.getDaysAllowed());
    }
    
    public static void writeCSV(String filePath, List<EmployeeLeave> employees) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write header line
            writer.write("EmpID,LeaveType,DateStart,DateEnd,LeaveCredits");
            writer.newLine();

            // Write employee data
            for (EmployeeLeave employee : employees) {
                writer.write(employee.toCSVString());
                writer.newLine();
            }

            // System.out.println("CSV file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<EmployeeLeave> readCSV(String filePath) {
        List<EmployeeLeave> employees = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header line
                }

                String[] fields = line.split(",");
                int numberOfColumns = 5;
                if (fields.length == numberOfColumns) {
                    String employeeID = fields[0].trim();
                    String leaveType = fields[1].trim();
                    String dateStart = fields[2].trim();
                    String dateEnd = fields[3].trim();
                    int daysAllowed = Integer.parseInt(fields[4].trim());

                    EmployeeLeave employee = new EmployeeLeave(employeeID, leaveType, dateStart, dateEnd, daysAllowed);
                    employees.add(employee);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public static void updateCSV(String filePath, EmployeeLeave updatedEmployee) {
        List<EmployeeLeave> employees = readCSV(filePath);

        for (int i = 0; i < employees.size(); i++) {
            EmployeeLeave employee = employees.get(i);
            if (employee.getEmployeeID().equals(updatedEmployee.getEmployeeID())) {
                employees.set(i, updatedEmployee);
                break;
            }
        }

        writeCSV(filePath, employees);
        System.out.println("CSV file updated successfully.");
    }
} // end of LeaveApplication

class EmployeeLeave {

    private String employeeID;
    private String leaveType;
    private String dateStart;
    private String dateEnd;
    private int daysAllowed;

    public EmployeeLeave(String employeeID, String leaveType, String dateStart, String dateEnd, int daysAllowed) {
        this.employeeID = employeeID;
        this.leaveType = leaveType;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;       
        this.daysAllowed = daysAllowed;
    }

    public String getEmployeeID() {
        return employeeID;
    }    
    
    public int getDaysDuration() {

        LocalDate startDate = LocalDate.parse(dateStart, DateTimeFormatter.ISO_DATE);
        LocalDate endDate = LocalDate.parse(dateEnd, DateTimeFormatter.ISO_DATE);

        // Subtract dates and get the number of days between them
        long daysDuration = ChronoUnit.DAYS.between(startDate, endDate);
        
        return (int) daysDuration;
    }
    
    public int getRemainingDays() {      
        int remainingDays = 0;
        switch (leaveType) {            
            case "Sick"      : remainingDays = LeaveApplication.NUMBER_OF_SICK_LEAVE - getDaysDuration(); break;
            case "Vacation"  : remainingDays = LeaveApplication.NUMBER_OF_VACATION_LEAVE - getDaysDuration(); break;
            case "Emergency" : remainingDays = LeaveApplication.NUMBER_OF_EMERGENCY_LEAVE - getDaysDuration(); break;
        }
        return remainingDays;
    }
    
    public boolean isValid() {
        return (getRemainingDays() >= 0);
    }
    
    public int getDaysAllowed() {
        return daysAllowed;
    }
    
    public void setDaysAllowed(int newDaysAllowed) {
        daysAllowed = newDaysAllowed;
    }

    public String getType() {
        return leaveType;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String toCSVString() {
        return employeeID + "," + leaveType + "," + dateStart + "," + dateEnd + "," + daysAllowed;
    }

    @Override
    public String toString() {
        return String.format("%5s %-9s From %10s To %10s - Leave Credits: %d", 
                employeeID, leaveType, dateStart, dateEnd, getDaysAllowed());
    }
}
