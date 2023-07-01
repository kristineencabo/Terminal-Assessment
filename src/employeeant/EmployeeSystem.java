package employeeant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This is a payroll class that extends JFrame. It provides functionality
 * related to payroll management.
 */
public class EmployeeSystem extends JFrame {

    JTable table = new JTable(new DefaultTableModel());
    JLabel empInfoLabel = new JLabel();
    JTextField empNumberField = new JTextField(15);

    public EmployeeSystem() {
        setTitle("Employee System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 600);        

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        JLabel empNumberLabel = new JLabel("Employee Number:");
        JButton retrieveButton = new JButton("Retrieve");
        JButton payrollButton = new JButton("Payroll");
        topPanel.add(empNumberLabel);
        topPanel.add(empNumberField);
        topPanel.add(retrieveButton);
        topPanel.add(payrollButton);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        table = new JTable(); // Create the table instance
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(empInfoLabel);
        add(bottomPanel, BorderLayout.SOUTH);                

        loadCSVData("csv/Employee Data.xlsx - Employee Details.csv");        
        
        retrieveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                retrieveEmployee();
            }
        });        

        payrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                Payroll payroll = new Payroll();
                payroll.setVisible(true);
                dispose(); setVisible(false);                
            }
        });                        
        
        setVisible(true);
    }

    private void loadCSVData(String csvFilePath) {
        DefaultTableModel model = new DefaultTableModel(); // Create a new DefaultTableModel instance
        model.setColumnIdentifiers(new String[]{"Employee Number", "Last Name", "First Name", "Birthday", "Address", "Phone Number", "SSS #", "Philhealth #", "TIN #", "Pag-ibig #", "Status", "Position", "Immediate Supervisor", "Basic Salary", "Rice Subsidy", "Phone Allowance", "Clothing Allowance", "Gross Semi-monthly Rate", "Hourly Rate"});

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line; boolean isHeaderLine = true;
            while ((line = reader.readLine()) != null) {
                if (isHeaderLine) {
                    isHeaderLine = false;
                    continue; // skip header
                }
                String[] rowData = line.split(",");
                model.addRow(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        table.setModel(model); // Set the model to the table
    }

    private void retrieveEmployee() {
        String employeeNumber = empNumberField.getText();
        String monthStr = empNumberField.getText();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing table data

        try (BufferedReader reader = new BufferedReader(new FileReader("Employee Data.xlsx - Attendance Record.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                if (rowData.length > 0 && rowData[0].equals(employeeNumber) && rowData[3].equals(monthStr)) {
                    model.addRow(rowData);
                }
            }
        } catch (IOException e) {
        }
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(EmployeeSystem::new);
    }
}
