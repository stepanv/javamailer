package net.uvavru.smtp.mailer.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import net.uvavru.smtp.mailer.Mailer;

public class MonitorPanel extends JPanel {

    public MonitorPanel() {
        super();
        create();
        textPaneInstance = textPane;
    }

    /**
     * 
     */
    private static final long serialVersionUID = -2702024507197138938L;
    private JLabel lblCurrentState;

    public JLabel getLblCurrentState() {
        return lblCurrentState;
    }

    public void setLblCurrentState(JLabel lblCurrentState) {
        this.lblCurrentState = lblCurrentState;
    }

    public static void appendToConsole(String text) {
        textPaneInstance.setText(textPaneInstance.getText() + text + "\n");
    }

    private static JTextPane textPaneInstance;

    JTextPane textPane = new JTextPane();
    JTabbedPane tabbedPaneMonitor = new JTabbedPane(JTabbedPane.TOP);
    JScrollPane scrollPane = new JScrollPane();

    private void create() {

        GridBagLayout gbl_panelMonitor = new GridBagLayout();
        gbl_panelMonitor.columnWidths = new int[] { 170, 0 };
        gbl_panelMonitor.rowHeights = new int[] { 169, 0 };
        gbl_panelMonitor.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gbl_panelMonitor.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
        setLayout(gbl_panelMonitor);

        tabbedPaneMonitor = new JTabbedPane(JTabbedPane.TOP);
        GridBagConstraints gbc_tabbedPaneMonitor = new GridBagConstraints();
        gbc_tabbedPaneMonitor.fill = GridBagConstraints.BOTH;
        gbc_tabbedPaneMonitor.gridx = 0;
        gbc_tabbedPaneMonitor.gridy = 0;
        this.add(tabbedPaneMonitor, gbc_tabbedPaneMonitor);
        tabbedPaneMonitor.setBorder(new TitledBorder(null, "Monitor",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));

        JPanel panelInfo = new JPanel();
        panelInfo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        tabbedPaneMonitor.addTab("Info", null, panelInfo, null);
        GridBagLayout gbl_panelInfo = new GridBagLayout();
        gbl_panelInfo.columnWidths = new int[] { 200, 200, 0 };
        gbl_panelInfo.rowHeights = new int[] { 14, 14, 0, 0, 0 };
        gbl_panelInfo.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
        gbl_panelInfo.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0,
                Double.MIN_VALUE };
        panelInfo.setLayout(gbl_panelInfo);

        JLabel lblState = new JLabel("State:");
        GridBagConstraints gbc_lblState = new GridBagConstraints();
        gbc_lblState.fill = GridBagConstraints.BOTH;
        gbc_lblState.insets = new Insets(0, 0, 5, 5);
        gbc_lblState.gridx = 0;
        gbc_lblState.gridy = 0;
        panelInfo.add(lblState, gbc_lblState);

        lblCurrentState = new JLabel("stopped");
        GridBagConstraints gbc_lblCurrentState = new GridBagConstraints();
        gbc_lblCurrentState.insets = new Insets(0, 0, 5, 0);
        gbc_lblCurrentState.fill = GridBagConstraints.BOTH;
        gbc_lblCurrentState.gridx = 1;
        gbc_lblCurrentState.gridy = 0;
        panelInfo.add(lblCurrentState, gbc_lblCurrentState);
        lblCurrentState.setForeground(Color.RED);

        JLabel lblIdleThreads = new JLabel("Idle threads:");
        GridBagConstraints gbc_lblIdleThreads = new GridBagConstraints();
        gbc_lblIdleThreads.anchor = GridBagConstraints.WEST;
        gbc_lblIdleThreads.insets = new Insets(0, 0, 5, 5);
        gbc_lblIdleThreads.gridx = 0;
        gbc_lblIdleThreads.gridy = 1;
        panelInfo.add(lblIdleThreads, gbc_lblIdleThreads);

        GridBagConstraints gbc_lblCurrentIdleThreads = new GridBagConstraints();
        gbc_lblCurrentIdleThreads.insets = new Insets(0, 0, 5, 0);
        gbc_lblCurrentIdleThreads.anchor = GridBagConstraints.WEST;
        gbc_lblCurrentIdleThreads.gridx = 1;
        gbc_lblCurrentIdleThreads.gridy = 1;
        panelInfo.add(lblCurrentIdleThreads, gbc_lblCurrentIdleThreads);

        JLabel lblRunningThreads = new JLabel("Running threads:");
        GridBagConstraints gbc_lblRunningThreads = new GridBagConstraints();
        gbc_lblRunningThreads.anchor = GridBagConstraints.WEST;
        gbc_lblRunningThreads.insets = new Insets(0, 0, 5, 5);
        gbc_lblRunningThreads.gridx = 0;
        gbc_lblRunningThreads.gridy = 2;
        panelInfo.add(lblRunningThreads, gbc_lblRunningThreads);

        GridBagConstraints gbc_labelCurrentRunningThreads = new GridBagConstraints();
        gbc_labelCurrentRunningThreads.anchor = GridBagConstraints.WEST;
        gbc_labelCurrentRunningThreads.insets = new Insets(0, 0, 5, 0);
        gbc_labelCurrentRunningThreads.gridx = 1;
        gbc_labelCurrentRunningThreads.gridy = 2;
        panelInfo.add(labelCurrentRunningThreads,
                gbc_labelCurrentRunningThreads);

        JPanel panelConsole = new JPanel();
        tabbedPaneMonitor.addTab("Console", null, panelConsole, null);
        GridBagLayout gbl_panelConsole = new GridBagLayout();
        gbl_panelConsole.columnWidths = new int[] { 364, 0 };
        gbl_panelConsole.rowHeights = new int[] { 50, 0 };
        gbl_panelConsole.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gbl_panelConsole.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
        panelConsole.setLayout(gbl_panelConsole);

        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 0;
        panelConsole.add(scrollPane, gbc_scrollPane);
        scrollPane.setViewportView(textPane);

        textPane.setAlignmentX(Component.RIGHT_ALIGNMENT);

    }

    private JLabel labelCurrentRunningThreads = new JLabel("0");

    private JLabel lblCurrentIdleThreads = new JLabel("0");

    public void updateMonitor(ControlPanel panelControl) {
        labelCurrentRunningThreads.setText(String.valueOf(Mailer
                .getSenderRunningCount()));
        lblCurrentIdleThreads.setText(String.valueOf(Mailer
                .getSenderIdlePoolCount()));
        
        if (Mailer.isRunning()) {
            panelControl.mailerStarted();
        } else {
            panelControl.mailerStopped();
        }

    }

    static class MonitorUpdater implements ActionListener {
        private MonitorPanel panelMonitor;
        private ControlPanel panelControl;

        public MonitorUpdater(MonitorPanel panelMonitor, ControlPanel panelControl) {
            this.panelMonitor = panelMonitor;
            this.panelControl = panelControl;
        }

        public void actionPerformed(ActionEvent e) {
            panelMonitor.updateMonitor(panelControl);
        }
    }
}