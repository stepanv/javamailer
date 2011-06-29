package net.uvavru.smtp.mailer.gui;

/*
 * Copyright (c) 2011, Stepan Vavra, MFF UK. All rights reserved.
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

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

/**
 * Main Java Mailer GUI Window class.<br>
 * Fires creating of JFrame instance and all related GUI Swing components
 * used in Mailer app.
 * 
 * @author stepan
 *
 */
public class Window {

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        // Create and set up the window.
        try {
            UIManager.setLookAndFeel(com.sun.java.swing.plaf.windows.WindowsLookAndFeel.class.getName());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JFrame.setDefaultLookAndFeelDecorated(false);
        JFrame frame = new JFrame("JavaMailer Proxy");
        frame.setPreferredSize(new Dimension(500, 600));
        frame.setMaximumSize(new Dimension(2147483647, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 549, 0 };
        gridBagLayout.rowHeights = new int[] { 21, 128, 128, 48, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0,
                Double.MIN_VALUE };
        frame.getContentPane().setLayout(gridBagLayout);
        
        JPanel panelImageWrapper = new JPanel();
        panelImageWrapper.setBackground(Color.BLACK);
        GridBagConstraints gbc_panelImageWrapper = new GridBagConstraints();
        gbc_panelImageWrapper.fill = GridBagConstraints.BOTH;
        gbc_panelImageWrapper.insets = new Insets(0, 0, 5, 0);
        gbc_panelImageWrapper.gridx = 0;
        gbc_panelImageWrapper.gridy = 0;
        frame.getContentPane().add(panelImageWrapper, gbc_panelImageWrapper);
        GridBagLayout gbl_panelImageWrapper = new GridBagLayout();
        gbl_panelImageWrapper.columnWidths = new int[]{0, 431, 0};
        gbl_panelImageWrapper.rowHeights = new int[]{21, 0};
        gbl_panelImageWrapper.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gbl_panelImageWrapper.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panelImageWrapper.setLayout(gbl_panelImageWrapper);

        JPanel panelImage = new ImagePanel("resources/images/tunnel.png");
        GridBagConstraints gbc_panelImage = new GridBagConstraints();
        gbc_panelImage.fill = GridBagConstraints.HORIZONTAL;
        gbc_panelImage.gridx = 1;
        gbc_panelImage.gridy = 0;
        panelImageWrapper.add(panelImage, gbc_panelImage);

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
        menuBar.setPanelConfiguration(panelConfig);
        menuBar.setPanelControl(panelControl);
        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
        Logger.getRootLogger().addAppender(new net.uvavru.smtp.mailer.gui.AppenderLog4j(panelMonitor));
        
        
        TrayIcon trayIcon = null;
        if (SystemTray.isSupported()) {
          SystemTray tray = SystemTray.getSystemTray();
          Image image = new ImageIcon("resources/icons/tunnel_icon.png").getImage(); 
          ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              System.out.println("action");
            }
          };
          final JPopupMenu popupMenu = new JPopupMenu();
          addPopup(panelControl, popupMenu);
          
          JRadioButtonMenuItem rdbtnmntmBar = new JRadioButtonMenuItem("bar");
          popupMenu.add(rdbtnmntmBar);
          
          JMenuItem mntmFoo = new JMenuItem("foo");
          popupMenu.add(mntmFoo);
          
          trayIcon = new TrayIcon(image, "Tray Demo", null);
          trayIcon.addActionListener(listener);
          
          
          trayIcon.addMouseListener(new MouseAdapter() {
              public void mouseReleased(MouseEvent e) {
                  if (e.isPopupTrigger()) {
                      popupMenu.setLocation(e.getX(), e.getY());
                      popupMenu.setInvoker(popupMenu);
                      popupMenu.setVisible(true);
                  }
              }
          });
          
          try {
            tray.add(trayIcon);
        } catch (AWTException e1) {
            e1.printStackTrace();
        }
        } 
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
    private static void addPopup(Component component, final JPopupMenu popup) {
        component.addMouseListener(new MouseAdapter() {
        	public void mousePressed(MouseEvent e) {
        		if (e.isPopupTrigger()) {
        			showMenu(e);
        		}
        	}
        	public void mouseReleased(MouseEvent e) {
        		if (e.isPopupTrigger()) {
        			showMenu(e);
        		}
        	}
        	private void showMenu(MouseEvent e) {
        		popup.show(e.getComponent(), e.getX(), e.getY());
        	}
        });
    }
}
