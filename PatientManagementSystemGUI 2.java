/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package patientmanagementsystemgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

public class PatientManagementSystemGUI {

    private static Queue<String> waitingList = new LinkedList<>();
    private static Queue<String> historyList = new LinkedList<>();

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Patient Management System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 400);

        JLabel imageLabel = new JLabel();
        ImageIcon originalIcon = new ImageIcon("/Users/Layan/NetBeansProjects/PatientManagementSystemGUI/hospital.jpg");
         imageLabel.setIcon(originalIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainFrame.add(imageLabel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
        
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");

        JMenuItem addPatientItem = new JMenuItem("Add Patient");
        JMenuItem prioritizePatientItem = new JMenuItem("Prioritize Patient");
        JMenuItem attendPatientItem = new JMenuItem("Attend Patient");
        JMenuItem viewWaitingListItem = new JMenuItem("View Waiting List");
        JMenuItem viewHistoryItem = new JMenuItem("View History");

        addPatientItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                openAddPatientWindow();
            }});

        prioritizePatientItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                openPrioritizePatientWindow();
            }});

        attendPatientItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                openAttendPatientWindow();
            }});

        viewWaitingListItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                openViewWaitingListWindow();
            }});

        viewHistoryItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                openViewHistoryWindow();
            }});

        menu.add(addPatientItem);
        menu.add(prioritizePatientItem);
        menu.add(attendPatientItem);
        menu.add(viewWaitingListItem);
        menu.add(viewHistoryItem);
        menuBar.add(menu);
       mainFrame.setJMenuBar(menuBar);
       mainFrame.setVisible(true);
    }

    private static void openAddPatientWindow() {
        JFrame addPatientFrame = new JFrame("Add Patient");
        addPatientFrame.setSize(300, 250);
        addPatientFrame.setLayout(new GridLayout(5, 2, 5, 5));

        JLabel idLabel = new JLabel("Patient ID:");
        JTextField idField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel reasonLabel = new JLabel("Reason for Visit:");
        JTextField reasonField = new JTextField();
        JLabel priorityLabel = new JLabel("Priority:");
        JTextField priorityField = new JTextField();

        JButton addButton = new JButton("Add");

        addButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String reason = reasonField.getText().trim();
            String priority = priorityField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || reason.isEmpty() || priority.isEmpty()) {
                throw new IllegalArgumentException("All fields must be filled !!");
            }

            int numericId = Integer.parseInt(id);  
            int numericPriority = Integer.parseInt(priority);  

            String patientInfo = "ID: " + numericId + ", Name: " + name + ", Reason: " + reason + ", Priority: " + numericPriority;

            waitingList.add(patientInfo);
            JOptionPane.showMessageDialog(addPatientFrame, "The patient has been added successfully!");

            addPatientFrame.dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(addPatientFrame, 
                "Patient ID and Priority must be valid numbers !", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(addPatientFrame, 
                ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});


        addPatientFrame.add(idLabel);
        addPatientFrame.add(idField);
        addPatientFrame.add(nameLabel);
        addPatientFrame.add(nameField);
        addPatientFrame.add(reasonLabel);
        addPatientFrame.add(reasonField);
        addPatientFrame.add(priorityLabel);
        addPatientFrame.add(priorityField);
        addPatientFrame.add(new JLabel());
        addPatientFrame.add(addButton);

        addPatientFrame.setVisible(true);
    }

private static void openPrioritizePatientWindow() {
    JFrame prioritizeFrame = new JFrame("Prioritize Patient");
    prioritizeFrame.setSize(300, 150);
    prioritizeFrame.setLayout(new GridLayout(2, 2, 5, 5));

    JLabel idLabel = new JLabel("Patient ID:");
    JTextField idField = new JTextField();
    JButton prioritizeButton = new JButton("Prioritize");

    prioritizeButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {
            String patientId = idField.getText();
            String prioritizedPatient = null;

            for (String patient : waitingList) {
                if (patient.contains("ID: " + patientId + ",")) {
                    prioritizedPatient = patient;
                    break;
                }
            }

            if (prioritizedPatient != null) {
                waitingList.remove(prioritizedPatient);
                Queue<String> tempQueue = new LinkedList<>();
                tempQueue.add(prioritizedPatient);
                tempQueue.addAll(waitingList);
                waitingList.clear();
                waitingList.addAll(tempQueue);

                JOptionPane.showMessageDialog(prioritizeFrame, 
                    "Patient prioritized: " + prioritizedPatient);
            } else {
                JOptionPane.showMessageDialog(prioritizeFrame, 
                    "Patient with ID " + patientId + " is not found in the waiting list");
            }

            prioritizeFrame.dispose();
        }
    });

    prioritizeFrame.add(idLabel);
    prioritizeFrame.add(idField);
    prioritizeFrame.add(new JLabel());
    prioritizeFrame.add(prioritizeButton);

    prioritizeFrame.setVisible(true);
}
 private static void openAttendPatientWindow() {
        JFrame attendFrame = new JFrame("Attend Patient");
        attendFrame.setSize(300, 100);

        JLabel messageLabel = new JLabel("Click the button to attend the next patient ");
        JButton attendButton = new JButton("Attend");

        attendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (!waitingList.isEmpty()) {
                    String patient = waitingList.poll();
                    historyList.add(patient);
                    JOptionPane.showMessageDialog(attendFrame, "Attending to patient: " + patient);
                } else {
                    JOptionPane.showMessageDialog(attendFrame, "No patients are in the waiting list");
                }
            }
        });

        attendFrame.setLayout(new FlowLayout());
        attendFrame.add(messageLabel);
        attendFrame.add(attendButton);

        attendFrame.setVisible(true);
    }

    private static void openViewWaitingListWindow() {
        JFrame waitingListFrame = new JFrame("Waiting List");
        waitingListFrame.setSize(300, 200);

        JTextArea waitingListArea = new JTextArea();
        waitingListArea.setEditable(false);

        if (waitingList.isEmpty()) {
            waitingListArea.setText("No patients are in the waiting list");
        } else {
            for (String patient : waitingList) {
                waitingListArea.append(patient + "\n");
            }
        }

        waitingListFrame.setLayout(new BorderLayout());
        waitingListFrame.add(new JScrollPane(waitingListArea), BorderLayout.CENTER);

        waitingListFrame.setVisible(true);
    }

    private static void openViewHistoryWindow() {
        JFrame historyFrame = new JFrame("Patient History");
        historyFrame.setSize(300, 200);

        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);

        if (historyList.isEmpty()) {
            historyArea.setText("No patients in the history ");
        } else {
            for (String patient : historyList) {
                historyArea.append(patient + "\n");
            }
        }

        historyFrame.setLayout(new BorderLayout());
        historyFrame.add(new JScrollPane(historyArea), BorderLayout.CENTER);

        historyFrame.setVisible(true);
    }
}
