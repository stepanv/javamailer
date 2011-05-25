package net.uvavru.smtp.mailer.gui;

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

/*
 * HelloWorldSwing.java requires no other files. 
 */
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;

public class Window {

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame("JavaMailer Proxy");
        frame.setPreferredSize(new Dimension(500, 600));
        frame.setMaximumSize(new Dimension(2147483647, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 170, 0 };
        gridBagLayout.rowHeights = new int[] { 21, 128, 128, 48, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0,
                Double.MIN_VALUE };
        frame.getContentPane().setLayout(gridBagLayout);

        JPanel panelImage = new ImagePanel("resources/images/tunnel.png");
        GridBagConstraints gbc_panelImage = new GridBagConstraints();
        gbc_panelImage.insets = new Insets(0, 0, 5, 0);
        gbc_panelImage.gridx = 0;
        gbc_panelImage.gridy = 0;
        frame.getContentPane().add(panelImage, gbc_panelImage);

        ConfigurationPanel panelConfig = new ConfigurationPanel();
        GridBagConstraints gbc_panelConfig = new GridBagConstraints();
        gbc_panelConfig.fill = GridBagConstraints.BOTH;
        gbc_panelConfig.insets = new Insets(0, 0, 5, 0);
        gbc_panelConfig.gridx = 0;
        gbc_panelConfig.gridy = 1;
        frame.getContentPane().add(panelConfig, gbc_panelConfig);
        
        MonitorPanel panelMonitor = new MonitorPanel();
        GridBagConstraints gbc_panelMonitor = new GridBagConstraints();
        gbc_panelMonitor.fill = GridBagConstraints.BOTH;
        gbc_panelMonitor.insets = new Insets(0, 0, 5, 0);
        gbc_panelMonitor.gridx = 0;
        gbc_panelMonitor.gridy = 2;
        frame.getContentPane().add(panelMonitor, gbc_panelMonitor);

        ControlPanel panelControl = new ControlPanel();
        panelControl.setMonitorPanel(panelMonitor);
        panelControl.setConfigurationPanel(panelConfig);
        GridBagConstraints gbc_panelControl = new GridBagConstraints();
        gbc_panelControl.fill = GridBagConstraints.BOTH;
        gbc_panelControl.gridx = 0;
        gbc_panelControl.gridy = 3;
        frame.getContentPane().add(panelControl, gbc_panelControl);
        
        panelConfig.setControlPanel(panelControl);

        // Display the window.
        frame.pack();

        MainMenu menuBar = new MainMenu();
        menuBar.setPanelMonitor(panelMonitor);
        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
