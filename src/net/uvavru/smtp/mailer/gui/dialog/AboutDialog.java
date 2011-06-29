package net.uvavru.smtp.mailer.gui.dialog;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;

import net.uvavru.smtp.mailer.gui.ImagePanel;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;

/**
 * Swing {@link JDialog} component About dialog.
 * 
 * @author stepan
 *
 */
public class AboutDialog extends JDialog {

    /**
         * 
         */
    private static final long serialVersionUID = -3249076672761982035L;

    public AboutDialog(JFrame parent) {
        super(parent, "About Dialog", true);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 197, 0};
        gridBagLayout.rowHeights = new int[]{90, 33, 0, 34, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);
                
                JPanel panel = new ImagePanel("resources/images/tunnel_small.png");
                GridBagConstraints gbc_panel = new GridBagConstraints();
                gbc_panel.gridheight = 2;
                gbc_panel.insets = new Insets(0, 0, 5, 5);
                gbc_panel.fill = GridBagConstraints.HORIZONTAL;
                gbc_panel.gridx = 0;
                gbc_panel.gridy = 0;
                getContentPane().add(panel, gbc_panel);
                
                JPanel panel_1 = new JPanel();
                panel_1.setMinimumSize(new Dimension(1, 10));
                panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
                panel_1.setSize(new Dimension(1, 0));
                GridBagConstraints gbc_panel_1 = new GridBagConstraints();
                gbc_panel_1.gridheight = 3;
                gbc_panel_1.insets = new Insets(0, 0, 5, 5);
                gbc_panel_1.fill = GridBagConstraints.BOTH;
                gbc_panel_1.gridx = 1;
                gbc_panel_1.gridy = 0;
                getContentPane().add(panel_1, gbc_panel_1);
        
                Box b = Box.createVerticalBox();
                b.add(Box.createGlue());
                b.add(new JLabel("Java Mailer"));
                
                Component verticalGlue = Box.createVerticalGlue();
                b.add(verticalGlue);
                
                Component horizontalGlue_1 = Box.createHorizontalGlue();
                b.add(horizontalGlue_1);
                b.add(new JLabel("Build 1.0, 052011"));
                
                Component horizontalGlue = Box.createHorizontalGlue();
                b.add(horizontalGlue);
                b.add(Box.createGlue());
                GridBagConstraints gbc_b = new GridBagConstraints();
                gbc_b.fill = GridBagConstraints.BOTH;
                gbc_b.insets = new Insets(0, 0, 5, 0);
                gbc_b.gridx = 2;
                gbc_b.gridy = 0;
                getContentPane().add(b, gbc_b);
                
                JTextPane txtpnAhoj = new JTextPane();
                GridBagConstraints gbc_txtpnAhoj = new GridBagConstraints();
                gbc_txtpnAhoj.fill = GridBagConstraints.BOTH;
                gbc_txtpnAhoj.insets = new Insets(0, 0, 5, 0);
                gbc_txtpnAhoj.gridx = 2;
                gbc_txtpnAhoj.gridy = 1;
                getContentPane().add(txtpnAhoj, gbc_txtpnAhoj);
                txtpnAhoj.setText("Java Mailer connects you to a specific smtp via SSH tunnel.");
                txtpnAhoj.setBackground(UIManager.getColor("Button.background"));
                
                JLabel lblNewLabel = new JLabel("Copyright (c) 2011, Stepan Vavra, MFF UK");
                lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
                GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
                gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
                gbc_lblNewLabel.gridx = 2;
                gbc_lblNewLabel.gridy = 2;
                getContentPane().add(lblNewLabel, gbc_lblNewLabel);
        
                JPanel p2 = new JPanel();
                JButton ok = new JButton("Ok");
                p2.add(ok);
                GridBagConstraints gbc_p2 = new GridBagConstraints();
                gbc_p2.gridwidth = 3;
                gbc_p2.anchor = GridBagConstraints.NORTH;
                gbc_p2.fill = GridBagConstraints.HORIZONTAL;
                gbc_p2.gridx = 0;
                gbc_p2.gridy = 3;
                getContentPane().add(p2, gbc_p2);

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setVisible(false);
            }
        });

        setSize(396, 231);
    }
}
