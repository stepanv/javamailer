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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class Window {
    
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
        gridBagLayout.columnWidths = new int[]{170, 0};
        gridBagLayout.rowHeights = new int[]{21, 0, 169, 48, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
        frame.getContentPane().setLayout(gridBagLayout);
        
        JPanel panelImage = new JImagePanel("resources/images/tunnel.png");
        GridBagConstraints gbc_panelImage = new GridBagConstraints();
        gbc_panelImage.insets = new Insets(0, 0, 5, 0);
        gbc_panelImage.gridx = 0;
        gbc_panelImage.gridy = 0;
        frame.getContentPane().add(panelImage, gbc_panelImage);
        
        JPanel panelConfiguration = new JPanel();
        panelConfiguration.setBorder(new TitledBorder(null, "Configuration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_panelConfiguration = new GridBagConstraints();
        gbc_panelConfiguration.insets = new Insets(0, 0, 5, 0);
        gbc_panelConfiguration.fill = GridBagConstraints.BOTH;
        gbc_panelConfiguration.gridx = 0;
        gbc_panelConfiguration.gridy = 1;
        frame.getContentPane().add(panelConfiguration, gbc_panelConfiguration);
        GridBagLayout gbl_panelConfiguration = new GridBagLayout();
        gbl_panelConfiguration.columnWidths = new int[]{305, 0};
        gbl_panelConfiguration.rowHeights = new int[]{128, 0, 0};
        gbl_panelConfiguration.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panelConfiguration.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        panelConfiguration.setLayout(gbl_panelConfiguration);
        
        JTabbedPane tabbedPaneConfiguration = new JTabbedPane(JTabbedPane.TOP);
        GridBagConstraints gbc_tabbedPaneConfiguration = new GridBagConstraints();
        gbc_tabbedPaneConfiguration.fill = GridBagConstraints.BOTH;
        gbc_tabbedPaneConfiguration.insets = new Insets(0, 0, 5, 0);
        gbc_tabbedPaneConfiguration.gridx = 0;
        gbc_tabbedPaneConfiguration.gridy = 0;
        panelConfiguration.add(tabbedPaneConfiguration, gbc_tabbedPaneConfiguration);
        
        JScrollPane scrollPaneConfiguration = new JScrollPane();
        tabbedPaneConfiguration.addTab("Server settings", null, scrollPaneConfiguration, null);
        
        tableConfiguration = new JTable();
        scrollPaneConfiguration.setPreferredSize(new Dimension(400, 100));
        scrollPaneConfiguration.setViewportView(tableConfiguration);
        tableConfiguration.setModel(new DefaultTableModel(
            new Object[][] {
                {"SMTP server", null},
                {"workers count", null},
                {"listening port", null},
                {"allowed clients pattern", null},
            },
            new String[] {
                "name", "value"
            }
        ) {
            /**
             * 
             */
            private static final long serialVersionUID = -1360372932556984063L;
            boolean[] columnEditables = new boolean[] {
                false, true
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        tableConfiguration.getColumnModel().getColumn(0).setPreferredWidth(120);
        tableConfiguration.getColumnModel().getColumn(1).setPreferredWidth(108);
        
        JScrollPane scrollPane_1 = new JScrollPane();
        tabbedPaneConfiguration.addTab("SSH settings", null, scrollPane_1, null);
        scrollPane_1.setPreferredSize(new Dimension(300, 100));
        
        table = new JTable();
        scrollPane_1.setViewportView(table);
        table.setModel(new DefaultTableModel(
            new Object[][] {
                {"executable", null},
                {"parameters", null},
                {"additional parameters", null},
                {"user", null},
                {"password", null},
                {"SMTP host", null},
                {"SMTP port", null},
                {"SMTP server pretended", null},
            },
            new String[] {
                "name", "value"
            }
        ) {
            /**
             * 
             */
            private static final long serialVersionUID = -8143789044797499788L;
            boolean[] columnEditables = new boolean[] {
                false, true
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        table.getColumnModel().getColumn(0).setPreferredWidth(124);
        table.getColumnModel().getColumn(1).setPreferredWidth(340);
        
        JButton btnSave = new JButton("Save");
        GridBagConstraints gbc_btnSave = new GridBagConstraints();
        gbc_btnSave.gridx = 0;
        gbc_btnSave.gridy = 1;
        panelConfiguration.add(btnSave, gbc_btnSave);
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        
        final MonitorPanel panelMonitor = new MonitorPanel();
       frame.getContentPane().add(panelMonitor, panelMonitor.getConstraints());
        
        ControlPanel panelControl = new ControlPanel();
        panelControl.setMonitorPanel(panelMonitor);
        frame.getContentPane().add(panelControl, panelControl.getConstraints());
        
        //Display the window.
        frame.pack();
        
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        JMenu mnNewMenu = new JMenu("Menu");
        menuBar.add(mnNewMenu);
        
        JMenuItem mntmNewMenuItem = new JMenuItem("Exit");
        mntmNewMenuItem.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                //tabbedPaneMonitor.appendToConsole("exiting...");
                System.exit(0);
                
            }
        });
        mnNewMenu.add(mntmNewMenuItem);
        
        JMenu mnHelp = new JMenu("Help");
        menuBar.add(mnHelp);
        
        JMenuItem mntmAbout = new JMenuItem("About");
        mntmAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //tabbedPaneMonitor.appendToConsole("help");
            }
        });
        mnHelp.add(mntmAbout);
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
class JImagePanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 7269311915479728109L;
    private Image img;

    public JImagePanel(String img) {
      this(new ImageIcon(img).getImage());
    }

    public JImagePanel(Image img) {
      this.img = img;
      Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
      setPreferredSize(size);
      setMinimumSize(size);
      setMaximumSize(size);
      setSize(size);
      setLayout(null);
    }

    public void paintComponent(Graphics g) {
      g.drawImage(img, 0, 0, null);
    }

  }
