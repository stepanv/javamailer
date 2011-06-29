package net.uvavru.smtp.mailer.gui;

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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import net.uvavru.smtp.mailer.Configuration;
import net.uvavru.smtp.mailer.Mailer;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;


/**
 * The Swing {@code ConfigurationPanel} class provides a GUI
 * for configuring {@link Mailer} via its {@link Configuration} class.
 * 
 * @author stepan
 *
 */
public class ConfigurationPanel extends JPanel {

	/**
	 * Default model for all tables used in this class.
	 * @author stepan
	 *
	 */
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
        for (Object key : propertyMapping.keySet()) {
                Vector<String> row = new Vector<String>();
                row.add((String)propertyMapping.get(key.toString()));
                row.add(Configuration.getProperty(key.toString()));
                tableModel.addRow(row);
        }
    }

    /**
     * Creates instance of {@link ConfigurationPanel} Swing component.
     */
    public ConfigurationPanel() {
        super();

        propertyServerToNameMapping.put(Configuration.SERVER_THREADS, "Thread count");
        propertyServerToNameMapping.put(Configuration.SERVER_TIMEOUT,
                "Connection timeout duration");
        propertyServerToNameMapping.put(Configuration.SERVER_PORT, "Listening port");
        propertyServerToNameMapping.put(Configuration.SERVER_SECURITYCLIENTPATTERN,
                "Reg exp pattern for clinents IPs");

        propertySSHToNameMapping.put(Configuration.SSH_USER, "User");
        propertySSHToNameMapping.put(Configuration.SSH_HOST, "Host");
        propertySSHToNameMapping.put(Configuration.SSH_EXECUTABLE, "SSH executable");
        propertySSHToNameMapping.put(Configuration.SSH_ADDPARAMS, "Additional parameters");

        propertySMTPToNameMapping.put(Configuration.SSH_SMTP_HOST, "SMTP to connect to");
        propertySMTPToNameMapping.put(Configuration.SSH_SMTP_PORT, "SMTP listening port");
        propertySMTPToNameMapping.put(Configuration.SSH_SMTP_SERVERPRETENDED,
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

    /**
     * It's required to set {@link ControlPanel} for flawless 
     * {@code ConfigurationPanel} functionality.
     * 
     * @param controlPanel A {@code ControlPanel} instance
     */
    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    private void create() {
        this.setBorder(new TitledBorder(null, "Configuration",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));

        GridBagLayout gbl_panelConfiguration = new GridBagLayout();
        gbl_panelConfiguration.columnWidths = new int[] { 305, 0 };
        gbl_panelConfiguration.rowHeights = new int[] { 156, 0, 0, 0 };
        gbl_panelConfiguration.columnWeights = new double[] { 1.0,
                Double.MIN_VALUE };
        gbl_panelConfiguration.rowWeights = new double[] { 0.0, 0.0, 1.0,
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

        JScrollPane scrollPaneSMTPConfig = new JScrollPane();
        tabbedPaneConfiguration.addTab("SMTP settings", null,
                scrollPaneSMTPConfig, null);

        tableSMTPConfig = new JTable();
        tableSMTPConfig.setModel(smtpTableModel);
        scrollPaneSMTPConfig.setViewportView(tableSMTPConfig);

        final JButton btnSave = new JButton(BUTTON_SAVE);
        GridBagConstraints gbc_btnSave = new GridBagConstraints();
        gbc_btnSave.insets = new Insets(0, 0, 5, 0);
        gbc_btnSave.gridx = 0;
        gbc_btnSave.gridy = 1;
        
        this.add(btnSave, gbc_btnSave);
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlPanel.scheduleConfigurationReload();
                btnSave.setText(BUTTON_SAVE);
            }
        });
        
        /**
         * Table model listener implementation used in all tables here.
         * 
         * @author stepan
         *
         */
        class ConfigTableModelListener implements TableModelListener {
            @Override
            public void tableChanged(TableModelEvent e) {
                btnSave.setText(BUTTON_SAVE_PENDINGCHANGE);
            }
        }
        
        smtpTableModel.addTableModelListener(new ConfigTableModelListener());
        sshTableModel.addTableModelListener(new ConfigTableModelListener());
        serverTableModel.addTableModelListener(new ConfigTableModelListener());
    }
    
    public static final String BUTTON_SAVE = "Save";
    public static final String BUTTON_SAVE_PENDINGCHANGE = "Save *";

    /**
     * Flushes all changed properties to make them permanent for {@link Mailer}
     * instance.
     */
    public void flushProperties() {
        writeConfiguration(serverTableModel, propertyServerToNameMapping);
        writeConfiguration(sshTableModel, propertySSHToNameMapping);
        writeConfiguration(smtpTableModel, propertySMTPToNameMapping);
        
    }
}
