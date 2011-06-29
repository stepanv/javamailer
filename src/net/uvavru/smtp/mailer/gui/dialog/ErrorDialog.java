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
public class ErrorDialog extends JDialog {

    /**
         * 
         */
    private static final long serialVersionUID = -3249076672761982035L;
    private JLabel textLabel = new JLabel("");
    
    public void setText(String text) {
        textLabel.setText(text);
    }

    public ErrorDialog(JFrame parent) {
        super(parent, "About Dialog", true);
        setTitle("Error");
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 197, 0};
        gridBagLayout.rowHeights = new int[]{90, 34, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);
                
                Box b = Box.createVerticalBox();
                b.add(Box.createGlue());
                b.add(textLabel);
                
                Component horizontalGlue = Box.createHorizontalGlue();
                b.add(horizontalGlue);
                b.add(Box.createGlue());
                GridBagConstraints gbc_b = new GridBagConstraints();
                gbc_b.fill = GridBagConstraints.BOTH;
                gbc_b.insets = new Insets(0, 0, 5, 0);
                gbc_b.gridx = 1;
                gbc_b.gridy = 0;
                getContentPane().add(b, gbc_b);
        
                JPanel p2 = new JPanel();
                JButton close = new JButton("Close");
                p2.add(close);
                GridBagConstraints gbc_p2 = new GridBagConstraints();
                gbc_p2.gridwidth = 2;
                gbc_p2.anchor = GridBagConstraints.NORTH;
                gbc_p2.fill = GridBagConstraints.HORIZONTAL;
                gbc_p2.gridx = 0;
                gbc_p2.gridy = 1;
                getContentPane().add(p2, gbc_p2);

        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setVisible(false);
            }
        });

        setSize(247, 208);
    }
}
