package cz.csob.smtp.mailer.gui;

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
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

public class ControlPanel extends JPanel {
 
    /**
     * @wbp.parser.constructor
     */
    public ControlPanel() {
        super();
        setSize(new Dimension(200, 0));
        setMinimumSize(new Dimension(200, 50));
        createAndShow();
    }

    public ControlPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        createAndShow();
    }
    /**
     * 
     */
    private static final long serialVersionUID = 6893318104120281770L;
    
    private JButton btnStart = new JButton("Start");
    private JButton btnStop = new JButton("Stop");
    
    private MonitorPanel monitorPanel;
    
    public void setMonitorPanel(MonitorPanel monitorPanel) {
        this.monitorPanel = monitorPanel;
    }
    private Object constraints;
    
    public Object getConstraints() {
        return constraints;
    }

    private void createAndShow() {
        this.setPreferredSize(new Dimension(200, 40));
        this.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Control", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        GridBagConstraints gbc_panelControl = new GridBagConstraints();
        gbc_panelControl.fill = GridBagConstraints.BOTH;
        gbc_panelControl.gridx = 0;
        gbc_panelControl.gridy = 3;
        constraints = gbc_panelControl;
        
        
        
        GridBagLayout gbl_panelControl = new GridBagLayout();
        gbl_panelControl.columnWidths = new int[]{37, 36, 0};
        gbl_panelControl.rowHeights = new int[]{38, 0};
        gbl_panelControl.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gbl_panelControl.rowWeights = new double[]{0.0, Double.MIN_VALUE};
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
                System.out.println("stop");
                monitorPanel.getLblCurrentState().setText("stopped");
                monitorPanel.getLblCurrentState().setForeground(Color.RED);
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
            }
        });
        
        
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("start");
                monitorPanel.getLblCurrentState().setText("running");
                monitorPanel.getLblCurrentState().setForeground(Color.GREEN);
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);
            }
        });
    }

}
