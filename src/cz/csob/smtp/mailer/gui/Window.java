package cz.csob.smtp.mailer.gui;
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

/**
 * This example, like all Swing examples, exists in a package:
 * in this case, the "start" package.
 * If you are using an IDE, such as NetBeans, this should work 
 * seamlessly.  If you are compiling and running the examples
 * from the command-line, this may be confusing if you aren't
 * used to using named packages.  In most cases,
 * the quick and dirty solution is to delete or comment out
 * the "package" line from all the source files and the code
 * should work as expected.  For an explanation of how to
 * use the Swing examples as-is from the command line, see
 * http://download.oracle.com/javase/javatutorials/tutorial/uiswing/start/compile.html#package
 */

/*
 * HelloWorldSwing.java requires no other files. 
 */
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;        

public class Window {
    private static JButton btnStart = new JButton("Start");
    private static JButton btnStop = new JButton("Stop");
    
    private static JLabel lblCurrentState;
    private static JTable tableConfiguration;
    private static JTable table;
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("JavaMailer Proxy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{170, 170, 0};
        gridBagLayout.rowHeights = new int[]{89, 0, 0, 38, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
        frame.getContentPane().setLayout(gridBagLayout);
        
        JPanel panel_1 = new JPanel();
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.gridwidth = 2;
        gbc_panel_1.insets = new Insets(0, 0, 5, 0);
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 0;
        frame.getContentPane().add(panel_1, gbc_panel_1);
        
        JPanel panelConfiguration = new JPanel();
        GridBagConstraints gbc_panelConfiguration = new GridBagConstraints();
        gbc_panelConfiguration.gridwidth = 2;
        gbc_panelConfiguration.insets = new Insets(0, 0, 5, 5);
        gbc_panelConfiguration.fill = GridBagConstraints.BOTH;
        gbc_panelConfiguration.gridx = 0;
        gbc_panelConfiguration.gridy = 1;
        frame.getContentPane().add(panelConfiguration, gbc_panelConfiguration);
        
        JPanel panelConfigurationNamed = new JPanel();
        panelConfigurationNamed.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Properties", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelConfiguration.add(panelConfigurationNamed);
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        panelConfigurationNamed.add(tabbedPane);
        
        JScrollPane scrollPaneConfiguration = new JScrollPane();
        tabbedPane.addTab("Server settings", null, scrollPaneConfiguration, null);
        
        tableConfiguration = new JTable();
        scrollPaneConfiguration.setPreferredSize(new Dimension(300, 100));
        scrollPaneConfiguration.setViewportView(tableConfiguration);
        tableConfiguration.setModel(new DefaultTableModel(
            new Object[][] {
                {"SMTP server", null},
                {"", null},
            },
            new String[] {
                "name", "value"
            }
        ) {
            boolean[] columnEditables = new boolean[] {
                false, true
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        
        JScrollPane scrollPane_1 = new JScrollPane();
        tabbedPane.addTab("SSH settings", null, scrollPane_1, null);
        scrollPane_1.setPreferredSize(new Dimension(300, 100));
        
        table = new JTable();
        scrollPane_1.setViewportView(table);
        table.setModel(new DefaultTableModel(
            new Object[][] {
                {"executable", null},
                {null, null},
                {null, null},
            },
            new String[] {
                "name", "value"
            }
        ));
        tableConfiguration.getColumnModel().getColumn(0).setPreferredWidth(120);
        tableConfiguration.getColumnModel().getColumn(1).setPreferredWidth(108);
        
        JPanel panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.HORIZONTAL;
        gbc_panel.insets = new Insets(0, 0, 5, 5);
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 2;
        frame.getContentPane().add(panel, gbc_panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{36, 54, 72, 0};
        gbl_panel.rowHeights = new int[]{34, 0};
        gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);
        
        JLabel lblStatus = new JLabel("Status:");
        GridBagConstraints gbc_lblStatus = new GridBagConstraints();
        gbc_lblStatus.gridwidth = 2;
        gbc_lblStatus.insets = new Insets(0, 0, 0, 5);
        gbc_lblStatus.gridx = 0;
        gbc_lblStatus.gridy = 0;
        panel.add(lblStatus, gbc_lblStatus);
        
        lblCurrentState = new JLabel("stopped");
        GridBagConstraints gbc_lblCurrentState = new GridBagConstraints();
        gbc_lblCurrentState.insets = new Insets(0, 0, 5, 0);
        gbc_lblCurrentState.gridx = 1;
        gbc_lblCurrentState.gridy = 2;
        frame.getContentPane().add(lblCurrentState, gbc_lblCurrentState);
        lblCurrentState.setForeground(Color.RED);
        GridBagConstraints gbc_btnStart = new GridBagConstraints();
        gbc_btnStart.insets = new Insets(0, 0, 0, 5);
        gbc_btnStart.gridx = 0;
        gbc_btnStart.gridy = 3;
        frame.getContentPane().add(btnStart, gbc_btnStart);
        
        
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("start");
                lblCurrentState.setText("running");
                lblCurrentState.setForeground(Color.GREEN);
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);
            }
        });
        GridBagConstraints gbc_btnStop = new GridBagConstraints();
        gbc_btnStop.gridx = 1;
        gbc_btnStop.gridy = 3;
        frame.getContentPane().add(btnStop, gbc_btnStop);
        
        btnStop.setEnabled(false);
        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("stop");
                lblCurrentState.setText("stopped");
                lblCurrentState.setForeground(Color.RED);
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
            }
        });

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
