package cz.csob.smtp.mailer.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import cz.csob.smtp.mailer.Configuration;

public class ConfigurationPanel extends JPanel {

    class ConfigTableModel extends DefaultTableModel {
        public ConfigTableModel() {
            super(new String[] {"name", "value"}, 0);
        }
        /**
         * 
         */
        private static final long serialVersionUID = -7635061872854903936L;
        boolean[] columnEditables = new boolean[] {
            false, true
        };
        public boolean isCellEditable(int row, int column) {
            return columnEditables[column];
        }
    }

    DefaultTableModel serverTableModel = new ConfigTableModel();
    DefaultTableModel sshTableModel = new ConfigTableModel();
    DefaultTableModel smtpTableModel = new ConfigTableModel();
    
    private void fillTableModel(DefaultTableModel tableModel, Hashtable<String, String> propertyMapping) {
        for (String key : Configuration.props.keySet()) {
            if (propertyMapping.containsKey(key.toString())) {
                Vector<String> row = new Vector<String>();
                row.add(propertyMapping.get(key.toString()));
                row.add(Configuration.props.getString(key));
                tableModel.addRow(row);
            }
        }
    }
    
    public ConfigurationPanel() {
        super();
        
        propertyServerToNameMapping.put("server.workers", "Thread count");
        propertyServerToNameMapping.put("server.timeout", "Connection max duration");
        propertyServerToNameMapping.put("server.port", "Listening port");
        propertyServerToNameMapping.put("server.securityClientPattern", "Reg exp pattern for clinents IPs");
        
        propertySSHToNameMapping.put("ssh.user", "User");
        propertySSHToNameMapping.put("ssh.host", "Host");
        propertySSHToNameMapping.put("ssh.program", "SSH executable");
        propertySSHToNameMapping.put("ssh.addparams", "Additional parameters");
        
        propertySMTPToNameMapping.put("ssh.smtp.host", "SMTP to connect to");
        propertySMTPToNameMapping.put("ssh.smtp.port", "SMTP listening port");
        propertySMTPToNameMapping.put("ssh.smtp.serverpretended", "Server SMTP HELO pretended");
        
        fillTableModel(serverTableModel, propertyServerToNameMapping);
        fillTableModel(sshTableModel, propertySSHToNameMapping);
        fillTableModel(smtpTableModel, propertySMTPToNameMapping);
        
        create();
    }
    
    Hashtable<String, String> propertyServerToNameMapping = new Hashtable<String, String>();
    Hashtable<String, String> propertySSHToNameMapping = new Hashtable<String, String>();
    Hashtable<String, String> propertySMTPToNameMapping = new Hashtable<String, String>();

    /**
     * 
     */
    private static final long serialVersionUID = 8856476596567368653L;
    private JTable tableServerConfig;
    private JTable tableSSHConfig;
    private JTable tableSMTPConfig;

    private void create() {
        this.setBorder(new TitledBorder(null, "Configuration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        
        GridBagLayout gbl_panelConfiguration = new GridBagLayout();
        gbl_panelConfiguration.columnWidths = new int[]{305, 0};
        gbl_panelConfiguration.rowHeights = new int[]{156, 0, 0};
        gbl_panelConfiguration.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panelConfiguration.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        this.setLayout(gbl_panelConfiguration);
        
        JTabbedPane tabbedPaneConfiguration = new JTabbedPane(JTabbedPane.TOP);
        GridBagConstraints gbc_tabbedPaneConfiguration = new GridBagConstraints();
        gbc_tabbedPaneConfiguration.fill = GridBagConstraints.BOTH;
        gbc_tabbedPaneConfiguration.insets = new Insets(0, 0, 5, 0);
        gbc_tabbedPaneConfiguration.gridx = 0;
        gbc_tabbedPaneConfiguration.gridy = 0;
        this.add(tabbedPaneConfiguration, gbc_tabbedPaneConfiguration);
        
        JScrollPane scrollPaneServerConfig = new JScrollPane();
        tabbedPaneConfiguration.addTab("Server settings", null, scrollPaneServerConfig, null);
        
        tableServerConfig = new JTable();
        scrollPaneServerConfig.setViewportView(tableServerConfig);
        
        
        tableServerConfig.setModel(serverTableModel);
        tableServerConfig.getColumnModel().getColumn(0).setPreferredWidth(120);
        tableServerConfig.getColumnModel().getColumn(1).setPreferredWidth(108);
        
        JScrollPane scrollPaneSSHConfig = new JScrollPane();
        tabbedPaneConfiguration.addTab("SSH settings", null, scrollPaneSSHConfig, null);
        
        tableSSHConfig = new JTable();
        scrollPaneSSHConfig.setViewportView(tableSSHConfig);
        tableSSHConfig.setModel(sshTableModel);
        tableSSHConfig.getColumnModel().getColumn(0).setPreferredWidth(124);
        tableSSHConfig.getColumnModel().getColumn(1).setPreferredWidth(340);
        
        JScrollPane scrollPaneSMTPConfig = new JScrollPane();
        tabbedPaneConfiguration.addTab("SMTP setting", null, scrollPaneSMTPConfig, null);
        
        tableSMTPConfig = new JTable();
        tableSMTPConfig.setModel(smtpTableModel);
        scrollPaneSMTPConfig.setViewportView(tableSMTPConfig);
        
        JButton btnSave = new JButton("Save");
        GridBagConstraints gbc_btnSave = new GridBagConstraints();
        gbc_btnSave.gridx = 0;
        gbc_btnSave.gridy = 1;
        this.add(btnSave, gbc_btnSave);
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
    }
}
