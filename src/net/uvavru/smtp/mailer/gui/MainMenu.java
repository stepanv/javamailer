package net.uvavru.smtp.mailer.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainMenu extends JMenuBar {
    
    /**
     * 
     */
    private static final long serialVersionUID = -3747254730113993085L;
    MonitorPanel panelMonitor;
    
    public MainMenu() {
        super();
        create();
    }

    /**
     * @param panelMPanel the panelMPanel to set
     */
    public void setPanelMonitor(MonitorPanel panelMonitor) {
        this.panelMonitor = panelMonitor;
    }

    private void create() {

        JMenu mnNewMenu = new JMenu("Menu");
        this.add(mnNewMenu);
        
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
        this.add(mnHelp);
        
        JMenuItem mntmAbout = new JMenuItem("About");
        mntmAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MonitorPanel.appendToConsole("help");
            }
        });
        mnHelp.add(mntmAbout);
    }

}
