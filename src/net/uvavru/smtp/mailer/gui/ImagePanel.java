package net.uvavru.smtp.mailer.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * The class {@code ImagePanel} provide ability to have a picture on
 * {@link JPanel} background.
 * 
 * @author stepan
 *
 */
public class ImagePanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 7269311915479728109L;
    private Image img;

    /**
     * Creates {@code ImagePanel} instance from a filesystem iamge path.
     * 
     * @param img Image
     */
    public ImagePanel(String img) {
      this(new ImageIcon(img).getImage());
    }

    /**
     * Creates {@code ImagePanel} instance from an {@code Image} instance.
     * 
     * @param img Image
     */
    public ImagePanel(Image img) {
      this.img = img;
      Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
      setPreferredSize(size);
      setMinimumSize(size);
      setMaximumSize(size);
      setSize(size);
      setLayout(null);
    }

    @Override
    public void paintComponent(Graphics g) {
      g.drawImage(img, 0, 0, null);
    }
}
