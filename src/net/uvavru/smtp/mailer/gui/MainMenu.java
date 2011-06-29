package net.uvavru.smtp.mailer.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import net.uvavru.smtp.mailer.Configuration;
import net.uvavru.smtp.mailer.gui.dialog.AboutDialog;
import net.uvavru.smtp.mailer.gui.dialog.ErrorDialog;
import net.uvavru.smtp.mailer.gui.dialog.HelpDialog;

/**
 * Simple simple simple menu of this application.
 * 
 * @author stepan
 * 
 */
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

    // Create a file chooser
    final JFileChooser loadDialog = new JFileChooser();
    final JFileChooser saveDialog = new JFileChooser();
    private ConfigurationPanel panelConfig;
    private ControlPanel panelControl;

    /**
     * @param panelMPanel
     *            the panelMPanel to set
     */
    public void setPanelMonitor(MonitorPanel panelMonitor) {
        this.panelMonitor = panelMonitor;
    }

    public void setPanelConfiguration(ConfigurationPanel panelConfig) {
        this.panelConfig = panelConfig;
    }

    public void setPanelControl(ControlPanel panelControl) {
        this.panelControl = panelControl;
    }

    ErrorDialog errorDialog = new ErrorDialog(new JFrame());
    
    JDialog dialogAbout;
    JDialog dialogHelp;

    private void create() {

        final JMenu mnNewMenu = new JMenu("Menu");
        this.add(mnNewMenu);

        JMenuItem mntmNewMenuItem = new JMenuItem("Exit");
        mntmNewMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // tabbedPaneMonitor.appendToConsole("exiting...");
                System.exit(0);

            }
        });

        JMenuItem mntmLoad = new JMenuItem("Load");
        mntmLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveDialog.showOpenDialog(MainMenu.this);
            }
        });
        mnNewMenu.add(mntmLoad);

        final JMenuItem mntmSave = new JMenuItem("Save");
        mntmSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == mntmSave) {
                    if (panelControl.mailer != null && panelControl.mailer.isRunning()) {
                        errorDialog.setText("Mailer must be stopped before saving");
                        errorDialog.setVisible(true);
                    } else {
                        int returnVal = saveDialog
                                .showOpenDialog(MainMenu.this);

                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            File file = saveDialog.getSelectedFile();
                            // This is where a real application would open the
                            // file.
                            try {
                                if (!panelControl.mailer.isRunning()) {
                                    panelConfig.flushProperties();
                                    Configuration.write(file);
                                } else {
                                    errorDialog.setText("Mailer must be stopped before saving");
                                    errorDialog.setVisible(true);
                                }

                            } catch (IOException e1) {
                                errorDialog.setText( e1.getMessage());
                                errorDialog.setVisible(true);
                            }
                        }
                    }
                }
            }
        });
        mnNewMenu.add(mntmSave);
        mnNewMenu.add(mntmNewMenuItem);

        JMenu mnHelp = new JMenu("Help");
        this.add(mnHelp);

        dialogHelp = new HelpDialog(new JFrame());

        JMenuItem mntmHelp = new JMenuItem("Help");
        mntmHelp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialogHelp.setVisible(true);
            }
        });
        mnHelp.add(mntmHelp);

        dialogAbout = new AboutDialog(new JFrame());

        JMenuItem mntmAbout = new JMenuItem("About");
        mntmAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialogAbout.setVisible(true);
            }
        });
        mnHelp.add(mntmAbout);
    }

}
