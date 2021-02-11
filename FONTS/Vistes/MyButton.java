package Vistes;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class MyButton extends JPanel {
    private int width = 0;
    private int height = 0;
    private String text = "";
    private Color color = new Color(246,146,36);
    private Color color_click = new Color(199,112,16);
    private Color color_disable = new Color(200,200,200);
    private Color text_color = new Color(250,250, 250);
    private Color text_color_disable = new Color(140,140,140);
    private String text_font = "Arial Bold";
    private int text_size = 25;
    private Boolean clicked = false;
    private Dimension arcs = new Dimension(30,30);
    private int BorderV = 5;
    private int BorderH = 10;
    private boolean buttonEnabled = true;
    private BufferedImage image;
    public MyButton(int width, int height, String text) {
        this.width = width;
        this.height = height;
        this.text = text;
        this.setMaximumSize(new Dimension(width, height));
        this.setMinimumSize(new Dimension(width, height));
        this.setPreferredSize(new Dimension(width, height));
        this.setOpaque(false);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!buttonEnabled) return;
                clicked = true;
                repaint(BorderH, BorderV, width-1-BorderH, height-1-BorderV);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!buttonEnabled) return;
                clicked = false;
                repaint(BorderH, BorderV, width-1-BorderH, height-1-BorderV);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (clicked)g2.setColor(color_click);
        else if (!buttonEnabled) g2.setColor(color_disable);
        else g2.setColor(color);
        g2.fillRoundRect(BorderH, BorderV, width-1-2*BorderH, height-1-2*BorderV, arcs.width, arcs.height);//paint background
        g2.setColor((buttonEnabled?text_color:text_color_disable));
        drawStringCentered(g2, text, width/2, height/2);
        if (image != null) g2.drawImage(image, 0,0, null);

    }
    void drawStringCentered(Graphics g, String s, int x, int y) {
        Font font = new Font(text_font, Font.PLAIN, text_size);
        g.setFont(font);
        Rectangle2D bounds = g.getFontMetrics().getStringBounds(s, g);
        g.drawString(s, (int) (x - bounds.getCenterX()), (int) (y - bounds.getCenterY()));
    }

    public void setImage(String path){
        try {
            image = ImageIO.read(new File(path));
        }
        catch (IOException e){
            System.out.println("Error reading image");
        }
    }
    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor_click() {
        return color_click;
    }

    public void setColor_click(Color color_click) {
        this.color_click = color_click;
    }

    public Color getColor_disable() {
        return color_disable;
    }

    public void setColor_disable(Color color_disable) {
        this.color_disable = color_disable;
    }

    public Color getText_color() {
        return text_color;
    }

    public void setText_color(Color text_color) {
        this.text_color = text_color;
    }

    public Color getText_color_disable() {
        return text_color_disable;
    }

    public void setText_color_disable(Color text_color_disable) {
        this.text_color_disable = text_color_disable;
    }

    public String getText_font() {
        return text_font;
    }

    public void setText_font(String text_font) {
        this.text_font = text_font;
    }

    public int getText_size() {
        return text_size;
    }

    public void setText_size(int text_size) {
        this.text_size = text_size;
    }

    public Boolean getClicked() {
        return clicked;
    }

    public void setClicked(Boolean clicked) {
        this.clicked = clicked;
    }

    public Dimension getArcs() {
        return arcs;
    }

    public void setArcs(Dimension arcs) {
        this.arcs = arcs;
    }

    public int getBorderV() {
        return BorderV;
    }

    public void setBorderV(int borderV) {
        BorderV = borderV;
    }

    public int getBorderH() {
        return BorderH;
    }

    public void setBorderH(int borderH) {
        BorderH = borderH;
    }

    public boolean isButtonEnabled() {
        return buttonEnabled;
    }

    public void setButtonEnabled(boolean buttonEnabled) {
        this.buttonEnabled = buttonEnabled;
    }
}