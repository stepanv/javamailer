package net.uvavru.smtp.mailer.gui.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * Swing {@link JDialog} component Help dialog.
 * Content of this class was generated so please don't try to look at it 
 * using a text editor.
 * 
 * @author stepan
 *
 */
public class HelpDialog extends JDialog {
    /**
     * 
     */
    private static final long serialVersionUID = -8926388172874646152L;

    public HelpDialog(JFrame parent) {
        super(parent, "Help Dialog", true);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{599, 0};
        gridBagLayout.rowHeights = new int[]{391, 34, 0};
        gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);
                
                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                GridBagConstraints gbc_scrollPane = new GridBagConstraints();
                gbc_scrollPane.fill = GridBagConstraints.BOTH;
                gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
                gbc_scrollPane.gridx = 0;
                gbc_scrollPane.gridy = 0;
                getContentPane().add(scrollPane, gbc_scrollPane);
                
                JPanel panel_14 = new JPanel();
                panel_14.setPreferredSize(new Dimension(500, 600));
                scrollPane.setViewportView(panel_14);
                GridBagLayout gbl_panel_14 = new GridBagLayout();
                gbl_panel_14.columnWidths = new int[]{294, 0};
                gbl_panel_14.rowHeights = new int[]{0, 0, 0, 0};
                gbl_panel_14.columnWeights = new double[]{1.0, Double.MIN_VALUE};
                gbl_panel_14.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
                panel_14.setLayout(gbl_panel_14);
                
                JPanel panel_11 = new JPanel();
                GridBagConstraints gbc_panel_11 = new GridBagConstraints();
                gbc_panel_11.fill = GridBagConstraints.BOTH;
                gbc_panel_11.insets = new Insets(0, 0, 5, 0);
                gbc_panel_11.gridx = 0;
                gbc_panel_11.gridy = 0;
                panel_14.add(panel_11, gbc_panel_11);
                panel_11.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Server settings", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
                GridBagLayout gbl_panel_11 = new GridBagLayout();
                gbl_panel_11.columnWidths = new int[]{294, 0};
                gbl_panel_11.rowHeights = new int[]{0, 0, 0, 0, 0};
                gbl_panel_11.columnWeights = new double[]{1.0, Double.MIN_VALUE};
                gbl_panel_11.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
                panel_11.setLayout(gbl_panel_11);
                
                JPanel panel_7 = new JPanel();
                GridBagConstraints gbc_panel_7 = new GridBagConstraints();
                gbc_panel_7.fill = GridBagConstraints.BOTH;
                gbc_panel_7.insets = new Insets(0, 0, 5, 0);
                gbc_panel_7.gridx = 0;
                gbc_panel_7.gridy = 0;
                panel_11.add(panel_7, gbc_panel_7);
                GridBagLayout gbl_panel_7 = new GridBagLayout();
                gbl_panel_7.columnWidths = new int[]{293, 303, 0};
                gbl_panel_7.rowHeights = new int[]{0, 0};
                gbl_panel_7.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
                gbl_panel_7.rowWeights = new double[]{1.0, Double.MIN_VALUE};
                panel_7.setLayout(gbl_panel_7);
                
                JLabel lblConnectionMaxDuration = new JLabel("Connection timeout duration.");
                lblConnectionMaxDuration.setFont(new Font("Dialog", Font.PLAIN, 11));
                GridBagConstraints gbc_lblConnectionMaxDuration = new GridBagConstraints();
                gbc_lblConnectionMaxDuration.anchor = GridBagConstraints.WEST;
                gbc_lblConnectionMaxDuration.insets = new Insets(0, 0, 0, 5);
                gbc_lblConnectionMaxDuration.gridx = 0;
                gbc_lblConnectionMaxDuration.gridy = 0;
                panel_7.add(lblConnectionMaxDuration, gbc_lblConnectionMaxDuration);
                
                JTextPane txtpnSmtpClientName_1 = new JTextPane();
                txtpnSmtpClientName_1.setText("Timeout in miliseconds how long SMTP client can be idle (without input).");
                txtpnSmtpClientName_1.setFont(new Font("Dialog", Font.PLAIN, 11));
                txtpnSmtpClientName_1.setBackground(UIManager.getColor("Button.background"));
                GridBagConstraints gbc_txtpnSmtpClientName_1 = new GridBagConstraints();
                gbc_txtpnSmtpClientName_1.fill = GridBagConstraints.BOTH;
                gbc_txtpnSmtpClientName_1.gridx = 1;
                gbc_txtpnSmtpClientName_1.gridy = 0;
                panel_7.add(txtpnSmtpClientName_1, gbc_txtpnSmtpClientName_1);
                
                JPanel panel_8 = new JPanel();
                GridBagConstraints gbc_panel_8 = new GridBagConstraints();
                gbc_panel_8.fill = GridBagConstraints.BOTH;
                gbc_panel_8.insets = new Insets(0, 0, 5, 0);
                gbc_panel_8.gridx = 0;
                gbc_panel_8.gridy = 1;
                panel_11.add(panel_8, gbc_panel_8);
                GridBagLayout gbl_panel_8 = new GridBagLayout();
                gbl_panel_8.columnWidths = new int[]{293, 303, 0};
                gbl_panel_8.rowHeights = new int[]{0, 0};
                gbl_panel_8.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
                gbl_panel_8.rowWeights = new double[]{1.0, Double.MIN_VALUE};
                panel_8.setLayout(gbl_panel_8);
                
                JLabel lblListeningPort = new JLabel("Listening port");
                lblListeningPort.setFont(new Font("Dialog", Font.PLAIN, 11));
                GridBagConstraints gbc_lblListeningPort = new GridBagConstraints();
                gbc_lblListeningPort.anchor = GridBagConstraints.WEST;
                gbc_lblListeningPort.insets = new Insets(0, 0, 0, 5);
                gbc_lblListeningPort.gridx = 0;
                gbc_lblListeningPort.gridy = 0;
                panel_8.add(lblListeningPort, gbc_lblListeningPort);
                
                JTextPane txtpnPortToListen = new JTextPane();
                txtpnPortToListen.setText("Port to listen on. (be aware of ports bellow 1024 which cannot be binded without elavated rights)");
                txtpnPortToListen.setFont(new Font("Dialog", Font.PLAIN, 11));
                txtpnPortToListen.setBackground(UIManager.getColor("Button.background"));
                GridBagConstraints gbc_txtpnPortToListen = new GridBagConstraints();
                gbc_txtpnPortToListen.fill = GridBagConstraints.BOTH;
                gbc_txtpnPortToListen.gridx = 1;
                gbc_txtpnPortToListen.gridy = 0;
                panel_8.add(txtpnPortToListen, gbc_txtpnPortToListen);
                
                JPanel panel_9 = new JPanel();
                GridBagConstraints gbc_panel_9 = new GridBagConstraints();
                gbc_panel_9.fill = GridBagConstraints.BOTH;
                gbc_panel_9.insets = new Insets(0, 0, 5, 0);
                gbc_panel_9.gridx = 0;
                gbc_panel_9.gridy = 2;
                panel_11.add(panel_9, gbc_panel_9);
                GridBagLayout gbl_panel_9 = new GridBagLayout();
                gbl_panel_9.columnWidths = new int[]{293, 303, 0};
                gbl_panel_9.rowHeights = new int[]{0, 0};
                gbl_panel_9.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
                gbl_panel_9.rowWeights = new double[]{1.0, Double.MIN_VALUE};
                panel_9.setLayout(gbl_panel_9);
                
                JLabel lblRegExpPattern = new JLabel("Reg exp pattern for clients");
                lblRegExpPattern.setFont(new Font("Dialog", Font.PLAIN, 11));
                GridBagConstraints gbc_lblRegExpPattern = new GridBagConstraints();
                gbc_lblRegExpPattern.anchor = GridBagConstraints.WEST;
                gbc_lblRegExpPattern.insets = new Insets(0, 0, 0, 5);
                gbc_lblRegExpPattern.gridx = 0;
                gbc_lblRegExpPattern.gridy = 0;
                panel_9.add(lblRegExpPattern, gbc_lblRegExpPattern);
                
                JTextPane txtpnJavaRegularExpression = new JTextPane();
                txtpnJavaRegularExpression.setText("Java regular expression used to decide wheter's client IP is allowed.");
                txtpnJavaRegularExpression.setFont(new Font("Dialog", Font.PLAIN, 11));
                txtpnJavaRegularExpression.setBackground(UIManager.getColor("Button.background"));
                GridBagConstraints gbc_txtpnJavaRegularExpression = new GridBagConstraints();
                gbc_txtpnJavaRegularExpression.fill = GridBagConstraints.BOTH;
                gbc_txtpnJavaRegularExpression.gridx = 1;
                gbc_txtpnJavaRegularExpression.gridy = 0;
                panel_9.add(txtpnJavaRegularExpression, gbc_txtpnJavaRegularExpression);
                
                JPanel panel_10 = new JPanel();
                GridBagConstraints gbc_panel_10 = new GridBagConstraints();
                gbc_panel_10.fill = GridBagConstraints.BOTH;
                gbc_panel_10.gridx = 0;
                gbc_panel_10.gridy = 3;
                panel_11.add(panel_10, gbc_panel_10);
                GridBagLayout gbl_panel_10 = new GridBagLayout();
                gbl_panel_10.columnWidths = new int[]{293, 303, 0};
                gbl_panel_10.rowHeights = new int[]{0, 0};
                gbl_panel_10.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
                gbl_panel_10.rowWeights = new double[]{1.0, Double.MIN_VALUE};
                panel_10.setLayout(gbl_panel_10);
                
                JLabel lblThreadCount = new JLabel("Thread count");
                lblThreadCount.setFont(new Font("Dialog", Font.PLAIN, 11));
                GridBagConstraints gbc_lblThreadCount = new GridBagConstraints();
                gbc_lblThreadCount.anchor = GridBagConstraints.WEST;
                gbc_lblThreadCount.insets = new Insets(0, 0, 0, 5);
                gbc_lblThreadCount.gridx = 0;
                gbc_lblThreadCount.gridy = 0;
                panel_10.add(lblThreadCount, gbc_lblThreadCount);
                
                JTextPane txtpnDefaultCountOf = new JTextPane();
                txtpnDefaultCountOf.setText("Default count of threads waiting for client's connection. In most cases 5 is OK.");
                txtpnDefaultCountOf.setFont(new Font("Dialog", Font.PLAIN, 11));
                txtpnDefaultCountOf.setBackground(UIManager.getColor("Button.background"));
                GridBagConstraints gbc_txtpnDefaultCountOf = new GridBagConstraints();
                gbc_txtpnDefaultCountOf.fill = GridBagConstraints.BOTH;
                gbc_txtpnDefaultCountOf.gridx = 1;
                gbc_txtpnDefaultCountOf.gridy = 0;
                panel_10.add(txtpnDefaultCountOf, gbc_txtpnDefaultCountOf);
                
                JPanel panel_12 = new JPanel();
                GridBagConstraints gbc_panel_12 = new GridBagConstraints();
                gbc_panel_12.fill = GridBagConstraints.BOTH;
                gbc_panel_12.insets = new Insets(0, 0, 5, 0);
                gbc_panel_12.gridx = 0;
                gbc_panel_12.gridy = 1;
                panel_14.add(panel_12, gbc_panel_12);
                panel_12.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "SSH Settings", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
                GridBagLayout gbl_panel_12 = new GridBagLayout();
                gbl_panel_12.columnWidths = new int[]{294, 0};
                gbl_panel_12.rowHeights = new int[]{0, 0, 24, 24, 0};
                gbl_panel_12.columnWeights = new double[]{1.0, Double.MIN_VALUE};
                gbl_panel_12.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
                panel_12.setLayout(gbl_panel_12);
                
                JPanel panel_3 = new JPanel();
                GridBagConstraints gbc_panel_3 = new GridBagConstraints();
                gbc_panel_3.fill = GridBagConstraints.BOTH;
                gbc_panel_3.insets = new Insets(0, 0, 5, 0);
                gbc_panel_3.gridx = 0;
                gbc_panel_3.gridy = 0;
                panel_12.add(panel_3, gbc_panel_3);
                GridBagLayout gbl_panel_3 = new GridBagLayout();
                gbl_panel_3.columnWidths = new int[]{293, 303, 0};
                gbl_panel_3.rowHeights = new int[]{0, 0};
                gbl_panel_3.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
                gbl_panel_3.rowWeights = new double[]{1.0, Double.MIN_VALUE};
                panel_3.setLayout(gbl_panel_3);
                
                JLabel lblAdditionalParameters = new JLabel("Additional parameters");
                lblAdditionalParameters.setFont(new Font("Dialog", Font.PLAIN, 11));
                GridBagConstraints gbc_lblAdditionalParameters = new GridBagConstraints();
                gbc_lblAdditionalParameters.anchor = GridBagConstraints.WEST;
                gbc_lblAdditionalParameters.insets = new Insets(0, 0, 0, 5);
                gbc_lblAdditionalParameters.gridx = 0;
                gbc_lblAdditionalParameters.gridy = 0;
                panel_3.add(lblAdditionalParameters, gbc_lblAdditionalParameters);
                
                JTextPane txtpnParametersWhichAre = new JTextPane();
                txtpnParametersWhichAre.setText("Parameters which are added when running the Executable. It can be a password used as a parameter to plink (windows) (e.g. '-pw passw'). It might be SSH agent specific arguments.\nCan be empty.");
                txtpnParametersWhichAre.setFont(new Font("Dialog", Font.PLAIN, 11));
                txtpnParametersWhichAre.setBackground(UIManager.getColor("Button.background"));
                GridBagConstraints gbc_txtpnParametersWhichAre = new GridBagConstraints();
                gbc_txtpnParametersWhichAre.fill = GridBagConstraints.BOTH;
                gbc_txtpnParametersWhichAre.gridx = 1;
                gbc_txtpnParametersWhichAre.gridy = 0;
                panel_3.add(txtpnParametersWhichAre, gbc_txtpnParametersWhichAre);
                
                JPanel panel_4 = new JPanel();
                GridBagConstraints gbc_panel_4 = new GridBagConstraints();
                gbc_panel_4.fill = GridBagConstraints.BOTH;
                gbc_panel_4.insets = new Insets(0, 0, 5, 0);
                gbc_panel_4.gridx = 0;
                gbc_panel_4.gridy = 1;
                panel_12.add(panel_4, gbc_panel_4);
                GridBagLayout gbl_panel_4 = new GridBagLayout();
                gbl_panel_4.columnWidths = new int[]{293, 303, 0};
                gbl_panel_4.rowHeights = new int[]{0, 0};
                gbl_panel_4.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
                gbl_panel_4.rowWeights = new double[]{1.0, Double.MIN_VALUE};
                panel_4.setLayout(gbl_panel_4);
                
                JLabel lblHost = new JLabel("Host");
                lblHost.setFont(new Font("Dialog", Font.PLAIN, 11));
                GridBagConstraints gbc_lblHost = new GridBagConstraints();
                gbc_lblHost.anchor = GridBagConstraints.WEST;
                gbc_lblHost.insets = new Insets(0, 0, 0, 5);
                gbc_lblHost.gridx = 0;
                gbc_lblHost.gridy = 0;
                panel_4.add(lblHost, gbc_lblHost);
                
                JTextPane txtpnSshServerHostname = new JTextPane();
                txtpnSshServerHostname.setText("SSH server hostname / IP address");
                txtpnSshServerHostname.setFont(new Font("Dialog", Font.PLAIN, 11));
                txtpnSshServerHostname.setBackground(UIManager.getColor("Button.background"));
                GridBagConstraints gbc_txtpnSshServerHostname = new GridBagConstraints();
                gbc_txtpnSshServerHostname.fill = GridBagConstraints.BOTH;
                gbc_txtpnSshServerHostname.gridx = 1;
                gbc_txtpnSshServerHostname.gridy = 0;
                panel_4.add(txtpnSshServerHostname, gbc_txtpnSshServerHostname);
                
                JPanel panel_5 = new JPanel();
                GridBagConstraints gbc_panel_5 = new GridBagConstraints();
                gbc_panel_5.fill = GridBagConstraints.BOTH;
                gbc_panel_5.insets = new Insets(0, 0, 5, 0);
                gbc_panel_5.gridx = 0;
                gbc_panel_5.gridy = 2;
                panel_12.add(panel_5, gbc_panel_5);
                GridBagLayout gbl_panel_5 = new GridBagLayout();
                gbl_panel_5.columnWidths = new int[]{293, 303, 0};
                gbl_panel_5.rowHeights = new int[]{0, 0};
                gbl_panel_5.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
                gbl_panel_5.rowWeights = new double[]{1.0, Double.MIN_VALUE};
                panel_5.setLayout(gbl_panel_5);
                
                JLabel lblSshExecutable = new JLabel("SSH executable");
                lblSshExecutable.setFont(new Font("Dialog", Font.PLAIN, 11));
                GridBagConstraints gbc_lblSshExecutable = new GridBagConstraints();
                gbc_lblSshExecutable.anchor = GridBagConstraints.WEST;
                gbc_lblSshExecutable.insets = new Insets(0, 0, 0, 5);
                gbc_lblSshExecutable.gridx = 0;
                gbc_lblSshExecutable.gridy = 0;
                panel_5.add(lblSshExecutable, gbc_lblSshExecutable);
                
                JTextPane txtpnExecutableWhichIs = new JTextPane();
                txtpnExecutableWhichIs.setText("Executable which is executed to connect to a SSH server. Usually it might be 'ssh' (unix world), 'plink' or 'putty' (windows) or any other application.\nIf the executable isn't on PATH, full path is required.");
                txtpnExecutableWhichIs.setFont(new Font("Dialog", Font.PLAIN, 11));
                txtpnExecutableWhichIs.setBackground(UIManager.getColor("Button.background"));
                GridBagConstraints gbc_txtpnExecutableWhichIs = new GridBagConstraints();
                gbc_txtpnExecutableWhichIs.fill = GridBagConstraints.BOTH;
                gbc_txtpnExecutableWhichIs.gridx = 1;
                gbc_txtpnExecutableWhichIs.gridy = 0;
                panel_5.add(txtpnExecutableWhichIs, gbc_txtpnExecutableWhichIs);
                
                JPanel panel_6 = new JPanel();
                GridBagConstraints gbc_panel_6 = new GridBagConstraints();
                gbc_panel_6.fill = GridBagConstraints.BOTH;
                gbc_panel_6.gridx = 0;
                gbc_panel_6.gridy = 3;
                panel_12.add(panel_6, gbc_panel_6);
                GridBagLayout gbl_panel_6 = new GridBagLayout();
                gbl_panel_6.columnWidths = new int[]{293, 303, 0};
                gbl_panel_6.rowHeights = new int[]{17, 0};
                gbl_panel_6.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
                gbl_panel_6.rowWeights = new double[]{1.0, Double.MIN_VALUE};
                panel_6.setLayout(gbl_panel_6);
                
                JLabel lblUser = new JLabel("User");
                lblUser.setFont(new Font("Dialog", Font.PLAIN, 11));
                GridBagConstraints gbc_lblUser = new GridBagConstraints();
                gbc_lblUser.anchor = GridBagConstraints.WEST;
                gbc_lblUser.insets = new Insets(0, 0, 0, 5);
                gbc_lblUser.gridx = 0;
                gbc_lblUser.gridy = 0;
                panel_6.add(lblUser, gbc_lblUser);
                
                JTextPane txtpnSshLoginSent = new JTextPane();
                txtpnSshLoginSent.setText("SSH login sent to a SSH server.");
                txtpnSshLoginSent.setFont(new Font("Dialog", Font.PLAIN, 11));
                txtpnSshLoginSent.setBackground(UIManager.getColor("Button.background"));
                GridBagConstraints gbc_txtpnSshLoginSent = new GridBagConstraints();
                gbc_txtpnSshLoginSent.fill = GridBagConstraints.BOTH;
                gbc_txtpnSshLoginSent.gridx = 1;
                gbc_txtpnSshLoginSent.gridy = 0;
                panel_6.add(txtpnSshLoginSent, gbc_txtpnSshLoginSent);
                
                JPanel panel_13 = new JPanel();
                GridBagConstraints gbc_panel_13 = new GridBagConstraints();
                gbc_panel_13.fill = GridBagConstraints.BOTH;
                gbc_panel_13.gridx = 0;
                gbc_panel_13.gridy = 2;
                panel_14.add(panel_13, gbc_panel_13);
                panel_13.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "SMTP settings", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
                GridBagLayout gbl_panel_13 = new GridBagLayout();
                gbl_panel_13.columnWidths = new int[]{294, 0};
                gbl_panel_13.rowHeights = new int[]{0, 0, 0, 0};
                gbl_panel_13.columnWeights = new double[]{1.0, Double.MIN_VALUE};
                gbl_panel_13.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
                panel_13.setLayout(gbl_panel_13);
                
                JPanel panel_2 = new JPanel();
                GridBagConstraints gbc_panel_2 = new GridBagConstraints();
                gbc_panel_2.fill = GridBagConstraints.BOTH;
                gbc_panel_2.insets = new Insets(0, 0, 5, 0);
                gbc_panel_2.gridx = 0;
                gbc_panel_2.gridy = 0;
                panel_13.add(panel_2, gbc_panel_2);
                GridBagLayout gbl_panel_2 = new GridBagLayout();
                gbl_panel_2.columnWidths = new int[]{293, 303, 0};
                gbl_panel_2.rowHeights = new int[]{0, 0};
                gbl_panel_2.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
                gbl_panel_2.rowWeights = new double[]{1.0, Double.MIN_VALUE};
                panel_2.setLayout(gbl_panel_2);
                
                JLabel lblServerSmtpHelo = new JLabel("Server SMTP HELO pretended");
                lblServerSmtpHelo.setFont(new Font("Dialog", Font.PLAIN, 11));
                GridBagConstraints gbc_lblServerSmtpHelo = new GridBagConstraints();
                gbc_lblServerSmtpHelo.anchor = GridBagConstraints.WEST;
                gbc_lblServerSmtpHelo.insets = new Insets(0, 0, 0, 5);
                gbc_lblServerSmtpHelo.gridx = 0;
                gbc_lblServerSmtpHelo.gridy = 0;
                panel_2.add(lblServerSmtpHelo, gbc_lblServerSmtpHelo);
                
                JTextPane txtpnSmtpClientName = new JTextPane();
                txtpnSmtpClientName.setText("SMTP client name sent to the remote SMTP server as a part of command HELO (EHLO). This gives an ability to hide the fact that SSH server is used as a proxy.");
                txtpnSmtpClientName.setFont(new Font("Dialog", Font.PLAIN, 11));
                txtpnSmtpClientName.setBackground(UIManager.getColor("Button.background"));
                GridBagConstraints gbc_txtpnSmtpClientName = new GridBagConstraints();
                gbc_txtpnSmtpClientName.fill = GridBagConstraints.BOTH;
                gbc_txtpnSmtpClientName.gridx = 1;
                gbc_txtpnSmtpClientName.gridy = 0;
                panel_2.add(txtpnSmtpClientName, gbc_txtpnSmtpClientName);
                
                JPanel panel_1 = new JPanel();
                GridBagConstraints gbc_panel_1 = new GridBagConstraints();
                gbc_panel_1.fill = GridBagConstraints.BOTH;
                gbc_panel_1.insets = new Insets(0, 0, 5, 0);
                gbc_panel_1.gridx = 0;
                gbc_panel_1.gridy = 1;
                panel_13.add(panel_1, gbc_panel_1);
                panel_1.setBorder(new EmptyBorder(3, 0, 3, 0));
                GridBagLayout gbl_panel_1 = new GridBagLayout();
                gbl_panel_1.columnWidths = new int[]{293, 303, 0};
                gbl_panel_1.rowHeights = new int[]{0, 0};
                gbl_panel_1.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
                gbl_panel_1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
                panel_1.setLayout(gbl_panel_1);
                
                JLabel lblNewLabel = new JLabel("SMTP to connect to");
                lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
                GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
                gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
                gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
                gbc_lblNewLabel.gridx = 0;
                gbc_lblNewLabel.gridy = 0;
                panel_1.add(lblNewLabel, gbc_lblNewLabel);
                
                JTextPane txtpnThePortWhich = new JTextPane();
                txtpnThePortWhich.setBackground(UIManager.getColor("Button.background"));
                txtpnThePortWhich.setFont(new Font("Dialog", Font.PLAIN, 11));
                txtpnThePortWhich.setText("SMTP server address it is expected to connect");
                GridBagConstraints gbc_txtpnThePortWhich = new GridBagConstraints();
                gbc_txtpnThePortWhich.fill = GridBagConstraints.BOTH;
                gbc_txtpnThePortWhich.gridx = 1;
                gbc_txtpnThePortWhich.gridy = 0;
                panel_1.add(txtpnThePortWhich, gbc_txtpnThePortWhich);
                
                JPanel panel = new JPanel();
                GridBagConstraints gbc_panel = new GridBagConstraints();
                gbc_panel.fill = GridBagConstraints.BOTH;
                gbc_panel.gridx = 0;
                gbc_panel.gridy = 2;
                panel_13.add(panel, gbc_panel);
                GridBagLayout gbl_panel = new GridBagLayout();
                gbl_panel.columnWidths = new int[]{293, 303, 0};
                gbl_panel.rowHeights = new int[]{0, 0};
                gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
                gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
                panel.setLayout(gbl_panel);
                
                JLabel label = new JLabel("SMTP listening port");
                label.setFont(new Font("Dialog", Font.PLAIN, 11));
                GridBagConstraints gbc_label = new GridBagConstraints();
                gbc_label.anchor = GridBagConstraints.WEST;
                gbc_label.insets = new Insets(0, 0, 0, 5);
                gbc_label.gridx = 0;
                gbc_label.gridy = 0;
                panel.add(label, gbc_label);
                
                JTextPane textPane = new JTextPane();
                textPane.setText("The port which is remote SMTP server listening on.");
                textPane.setFont(new Font("Dialog", Font.PLAIN, 11));
                textPane.setBackground(UIManager.getColor("Button.background"));
                GridBagConstraints gbc_textPane = new GridBagConstraints();
                gbc_textPane.fill = GridBagConstraints.BOTH;
                gbc_textPane.gridx = 1;
                gbc_textPane.gridy = 0;
                panel.add(textPane, gbc_textPane);
        
                JPanel p2 = new JPanel();
                JButton ok = new JButton("Ok");
                p2.add(ok);
                GridBagConstraints gbc_p2 = new GridBagConstraints();
                gbc_p2.anchor = GridBagConstraints.NORTH;
                gbc_p2.fill = GridBagConstraints.HORIZONTAL;
                gbc_p2.gridx = 0;
                gbc_p2.gridy = 1;
                getContentPane().add(p2, gbc_p2);

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setVisible(false);
            }
        });

        setSize(606, 421);
    }
}
