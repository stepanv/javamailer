package cz.csob.smtp.mailer.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

import cz.csob.smtp.mailer.Configuration;

public class ConfigurationPanel extends JPanel {

    class ConfigTableModel extends DefaultTableModel {
        public ConfigTableModel() {
            super(new String[] { "name", "value" }, 0);
        }

        /**
         * 
         */
        private static final long serialVersionUID = -7635061872854903936L;
        boolean[] columnEditables = new boolean[] { false, true };

        public boolean isCellEditable(int row, int column) {
            return columnEditables[column];
        }

        @SuppressWarnings("rawtypes")
        Class[] columnTypes = new Class[] { String.class, String.class };

        public Class<?> getColumnClass(int columnIndex) {
            return columnTypes[columnIndex];
        }
    }

    DefaultTableModel serverTableModel = new ConfigTableModel();
    DefaultTableModel sshTableModel = new ConfigTableModel();
    DefaultTableModel smtpTableModel = new ConfigTableModel();

    private void writeConfiguration(DefaultTableModel tableModel,
            BidiMap propertyMapping) {
        @SuppressWarnings("unchecked")
        Vector<Vector<String>> rows = tableModel.getDataVector();
        
        for (Vector<String> row : rows) {
            String name = row.elementAt(0);
            String property = (String) propertyMapping.getKey(name);
            
            System.setProperty(property, row.elementAt(1));
        }
    }

    private void fillTableModel(DefaultTableModel tableModel,
            BidiMap propertyMapping) {
        for (String key : Configuration.props.keySet()) {
            if (propertyMapping.containsKey(key.toString())) {
                Vector<String> row = new Vector<String>();
                row.add((String)propertyMapping.get(key.toString()));
                row.add(Configuration.props.getString(key));
                tableModel.addRow(row);
            }
        }
    }

    public ConfigurationPanel() {
        super();

        propertyServerToNameMapping.put("server.workers", "Thread count");
        propertyServerToNameMapping.put("server.timeout",
                "Connection max duration");
        propertyServerToNameMapping.put("server.port", "Listening port");
        propertyServerToNameMapping.put("server.securityClientPattern",
                "Reg exp pattern for clinents IPs");

        propertySSHToNameMapping.put("ssh.user", "User");
        propertySSHToNameMapping.put("ssh.host", "Host");
        propertySSHToNameMapping.put("ssh.program", "SSH executable");
        propertySSHToNameMapping.put("ssh.addparams", "Additional parameters");

        propertySMTPToNameMapping.put("ssh.smtp.host", "SMTP to connect to");
        propertySMTPToNameMapping.put("ssh.smtp.port", "SMTP listening port");
        propertySMTPToNameMapping.put("ssh.smtp.serverpretended",
                "Server SMTP HELO pretended");

        fillTableModel(serverTableModel, propertyServerToNameMapping);
        fillTableModel(sshTableModel, propertySSHToNameMapping);
        fillTableModel(smtpTableModel, propertySMTPToNameMapping);

        create();
    }

    BidiMap propertyServerToNameMapping = new DualHashBidiMap();
    BidiMap propertySSHToNameMapping = new DualHashBidiMap();
    BidiMap propertySMTPToNameMapping = new DualHashBidiMap();

    /**
     * 
     */
    private static final long serialVersionUID = 8856476596567368653L;
    private JTable tableServerConfig;
    private JTable tableSSHConfig;
    private JTable tableSMTPConfig;
    
    private ControlPanel controlPanel;

    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    private void create() {
        this.setBorder(new TitledBorder(null, "Configuration",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));

        GridBagLayout gbl_panelConfiguration = new GridBagLayout();
        gbl_panelConfiguration.columnWidths = new int[] { 305, 0 };
        gbl_panelConfiguration.rowHeights = new int[] { 156, 0, 0 };
        gbl_panelConfiguration.columnWeights = new double[] { 1.0,
                Double.MIN_VALUE };
        gbl_panelConfiguration.rowWeights = new double[] { 0.0, 0.0,
                Double.MIN_VALUE };
        this.setLayout(gbl_panelConfiguration);

        JTabbedPane tabbedPaneConfiguration = new JTabbedPane(JTabbedPane.TOP);
        GridBagConstraints gbc_tabbedPaneConfiguration = new GridBagConstraints();
        gbc_tabbedPaneConfiguration.fill = GridBagConstraints.BOTH;
        gbc_tabbedPaneConfiguration.insets = new Insets(0, 0, 5, 0);
        gbc_tabbedPaneConfiguration.gridx = 0;
        gbc_tabbedPaneConfiguration.gridy = 0;
        this.add(tabbedPaneConfiguration, gbc_tabbedPaneConfiguration);

        JScrollPane scrollPaneServerConfig = new JScrollPane();
        tabbedPaneConfiguration.addTab("Server settings", null,
                scrollPaneServerConfig, null);

        tableServerConfig = new JTable();
        scrollPaneServerConfig.setViewportView(tableServerConfig);
        tableServerConfig.setModel(serverTableModel);

        JScrollPane scrollPaneSSHConfig = new JScrollPane();
        tabbedPaneConfiguration.addTab("SSH settings", null,
                scrollPaneSSHConfig, null);

        tableSSHConfig = new JTable();
        scrollPaneSSHConfig.setViewportView(tableSSHConfig);
        tableSSHConfig.setModel(sshTableModel);
        tableSSHConfig.getColumnModel().getColumn(0).setPreferredWidth(124);
        tableSSHConfig.getColumnModel().getColumn(1).setPreferredWidth(340);

        JScrollPane scrollPaneSMTPConfig = new JScrollPane();
        tabbedPaneConfiguration.addTab("SMTP setting", null,
                scrollPaneSMTPConfig, null);

        tableSMTPConfig = new JTable();
        tableSMTPConfig.setModel(smtpTableModel);
        scrollPaneSMTPConfig.setViewportView(tableSMTPConfig);

        final JButton btnSave = new JButton("Save");
        GridBagConstraints gbc_btnSave = new GridBagConstraints();
        gbc_btnSave.gridx = 0;
        gbc_btnSave.gridy = 1;
        this.add(btnSave, gbc_btnSave);
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlPanel.scheduleConfigurationReload();
                btnSave.setText("Save");
            }
        });
    }

    public void flushProperties() {
        writeConfiguration(serverTableModel, propertyServerToNameMapping);
        writeConfiguration(sshTableModel, propertySSHToNameMapping);
        writeConfiguration(smtpTableModel, propertySMTPToNameMapping);
        
    }
}
