package Vistes;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class MyRadioButton extends JPanel{
    private JRadioButton radioButton;
    private JLabel label;
    private Icon icon;
    private final int heightRadio = 30;
    private final int widthRadio = 30;
    public MyRadioButton(int width, int height, String text) {
        this.setLayout(null);
        this.setMinimumSize(new Dimension(width, height));
        this.setMaximumSize(new Dimension(width, height));
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(new Color(0,0,0,0));
        radioButton = new JRadioButton();
        icon = new MyIcon(20,20, false);
        radioButton.setIcon(icon);
        icon = new MyIcon(20,20, true);
        radioButton.setSelectedIcon(icon);
        radioButton.setBackground(new Color(0,0,0,0));
        radioButton.setOpaque(false);
        radioButton.setContentAreaFilled(false);
        radioButton.setBorderPainted(false);

        radioButton.setMaximumSize(new Dimension(widthRadio, heightRadio));
        radioButton.setMinimumSize(new Dimension(widthRadio, heightRadio));
        radioButton.setPreferredSize(new Dimension(widthRadio, heightRadio));
        radioButton.setBounds(0,0,widthRadio, heightRadio);
        radioButton.setSelected(false);

        int widthLabel = width-widthRadio;
        int heightLabel = heightRadio;
        label = new JLabel(text);
        Font font = new Font("Arial Bold", Font.PLAIN, 15);
        label.setFont(font);
        label.setForeground(new Color(250,250,250));
        label.setMinimumSize(new Dimension(widthLabel, heightLabel));
        label.setMaximumSize(new Dimension(widthLabel, heightLabel));
        label.setPreferredSize(new Dimension(widthLabel, heightLabel));
        label.setBounds(widthRadio, 0, widthLabel, heightRadio);

        this.add(radioButton);
        this.add(label);
    }
    public JRadioButton getRadioButton(){
        return radioButton;
    }
    public JLabel getLabel(){
        return label;
    }
    private class MyIcon implements Icon
    {
        private int width, height;
        private final int minorCircle = 4;
        private final float stroke = 1f;
        private boolean selected;
        private Color color = new Color(246,146,36);
        public int getIconHeight() { return height; }

        public int getIconWidth() { return width; }

        public MyIcon(int width, int height, boolean selected) {
            this.width = width;
            this.height = height;
            this.selected = selected;
        }

        public void paintIcon(Component c, Graphics g, int x, int y)
        {
            // store the old color; I like to leave Graphics as I receive them
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(stroke));
            g.setColor(color);
            g2.drawOval(x,y,width,height);
            if (this.selected)g2.fillOval(x+minorCircle, y+minorCircle, width-2*minorCircle, height-2*minorCircle);
        }
    }
}
