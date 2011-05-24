package cz.csob.smtp.mailer.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class ConfigurationPanel extends JPanel {


    public ConfigurationPanel() {
        super();
        create();
    }

    /**
     * 
     */
    private static final long serialVersionUID = 8856476596567368653L;
    private JTable tableConfiguration;
    private JTable table;
    private Object constraints;
    
    /**
     * @return the constraints
     */
    public Object getConstraints() {
        return constraints;
    }

    private void create() {
        this.setBorder(new TitledBorder(null, "Configuration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_panelConfiguration = new GridBagConstraints();
        gbc_panelConfiguration.insets = new Insets(0, 0, 5, 0);
        gbc_panelConfiguration.fill = GridBagConstraints.BOTH;
        gbc_panelConfiguration.gridx = 0;
        gbc_panelConfiguration.gridy = 1;
        constraints = gbc_panelConfiguration;
        
        GridBagLayout gbl_panelConfiguration = new GridBagLayout();
        gbl_panelConfiguration.columnWidths = new int[]{305, 0};
        gbl_panelConfiguration.rowHeights = new int[]{128, 0, 0};
        gbl_panelConfiguration.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panelConfiguration.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        this.setLayout(gbl_panelConfiguration);
        
        JTabbedPane tabbedPaneConfiguration = new JTabbedPane(JTabbedPane.TOP);
        GridBagConstraints gbc_tabbedPaneConfiguration = new GridBagConstraints();
        gbc_tabbedPaneConfiguration.fill = GridBagConstraints.BOTH;
        gbc_tabbedPaneConfiguration.insets = new Insets(0, 0, 5, 0);
        gbc_tabbedPaneConfiguration.gridx = 0;
        gbc_tabbedPaneConfiguration.gridy = 0;
        this.add(tabbedPaneConfiguration, gbc_tabbedPaneConfiguration);
        
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
        this.add(btnSave, gbc_btnSave);
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
    }
}
