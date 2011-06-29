package net.uvavru.smtp.mailer.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import net.uvavru.smtp.mailer.Mailer;
import net.uvavru.smtp.mailer.MailerImpl;

/**
 * The {@code ControlPanel} class associates whole mailer GUI with the
 * Java Mailer core (e.g. {@link Mailer} instance).
 * 
 * @author stepan
 *
 */
public class ControlPanel extends JPanel {

    /**
     * @wbp.parser.constructor
     */
    public ControlPanel() {
        super();
        setSize(new Dimension(200, 0));
        setMinimumSize(new Dimension(200, 50));
        create();
    }

    public ControlPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        create();
    }

    /**
     * 
     */
    private static final long serialVersionUID = 6893318104120281770L;

    private JButton btnStart = new JButton("Start");
    private JButton btnStop = new JButton("Stop");

    private MonitorPanel monitorPanel;
    private ConfigurationPanel configurationPanel;

    public void setMonitorPanel(MonitorPanel monitorPanel) {
        this.monitorPanel = monitorPanel;

        monitorUpdateTimer = new Timer(1000, new MailerChangeStateDistributor(
                monitorPanel, this));

        monitorUpdateTimer.start();
    }

    public void setConfigurationPanel(ConfigurationPanel configurationPanel) {
        this.configurationPanel = configurationPanel;
    }

    private void create() {
        this.setPreferredSize(new Dimension(200, 40));
        this.setBorder(new TitledBorder(UIManager
                .getBorder("TitledBorder.border"), "Control",
                TitledBorder.LEADING, TitledBorder.TOP, null,
                new Color(0, 0, 0)));

        GridBagLayout gbl_panelControl = new GridBagLayout();
        gbl_panelControl.columnWidths = new int[] { 37, 36, 0 };
        gbl_panelControl.rowHeights = new int[] { 38, 0 };
        gbl_panelControl.columnWeights = new double[] { 1.0, 1.0,
                Double.MIN_VALUE };
        gbl_panelControl.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
        this.setLayout(gbl_panelControl);
        GridBagConstraints gbc_btnStart = new GridBagConstraints();
        gbc_btnStart.anchor = GridBagConstraints.EAST;
        gbc_btnStart.insets = new Insets(0, 0, 0, 5);
        gbc_btnStart.gridx = 0;
        gbc_btnStart.gridy = 0;
        this.add(btnStart, gbc_btnStart);
        GridBagConstraints gbc_btnStop = new GridBagConstraints();
        gbc_btnStop.anchor = GridBagConstraints.WEST;
        gbc_btnStop.gridx = 1;
        gbc_btnStop.gridy = 0;
        btnStop.setMaximumSize(new Dimension(100, 23));
        this.add(btnStop, gbc_btnStop);

        btnStop.setEnabled(false);
        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mailer.stopAsync();
                mailerStopped();
            }
        });

        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (reloadConfiguration) {
                    configurationPanel.flushProperties();
                    reloadConfiguration = false;
                }
                mailer.startAsync();
                mailerStarted();
            }
        });
    }

    private void mailerStopped() {
        monitorPanel.getLblCurrentState().setText("stopped");
        monitorPanel.getLblCurrentState().setForeground(Color.RED);
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
    }

    private void mailerStarted() {
        monitorPanel.getLblCurrentState().setText("running");
        monitorPanel.getLblCurrentState().setForeground(Color.GREEN);
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
    }

    private boolean reloadConfiguration = false;

    /**
     * Schedules {@code ControlPanel} to reload {@link Mailer} configuration
     * from set properties (e.g. via GUI) to next {@code Mailer} startup
     */
    public void scheduleConfigurationReload() {
        reloadConfiguration = true;
    }

    Mailer mailer = new MailerImpl();

    Timer monitorUpdateTimer;

    /**
     * Fires {@code ControlPanel} update of monitored events. 
     */
    public void update() {
        if (mailer.isRunning()) {
            mailerStarted();
        } else {
            mailerStopped();
        }
    }
}

/**
 * Class designed to update all needed components according to mailer
 * current state.
 * 
 * @author stepan
 * 
 */
class MailerChangeStateDistributor implements ActionListener {
    private MonitorPanel panelMonitor;
    private ControlPanel panelControl;

    public MailerChangeStateDistributor(MonitorPanel panelMonitor, ControlPanel panelControl) {
        this.panelMonitor = panelMonitor;
        this.panelControl = panelControl;
    }

    public void actionPerformed(ActionEvent e) {
        panelMonitor.update(panelControl);
        panelControl.update();
    }
}
