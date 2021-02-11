package Vistes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class MySpinner extends JPanel {
    private int width = 0;
    private int height = 0;
    private int min = 0;
    private int max = 0;
    private int value = 0;
    private Color color_click = new Color(199,112,16);
    private Color text_color = new Color(250,250, 250);
    private String text_font = "Arial Bold";
    private int text_size = 25;
    private Color color = new Color(246,146,36);
    private Dimension arcs = new Dimension(40,40);
    private int BorderV = 5;
    private int BorderH = 5;
    private int thick = 2;
    private Boolean clickL = false;
    private Boolean clickR = false;
    private JTextField textField;
    public MySpinner(int width, int height, int min, int max) {
        this.setLayout(null);
        this.width = width;
        this.height = height;
        this.min = min;
        this.max = max;
        this.setMaximumSize(new Dimension(width, height));
        this.setMinimumSize(new Dimension(width, height));
        this.setPreferredSize(new Dimension(width, height));
        this.setOpaque(false);
        this.value = (min+max)/2;
        textField = new JTextField("0"){
            @Override public void setBorder(Border border) {
                // No!
            }
        };
        int widthTextField = (width-2*BorderH)/3;
        int heightTextField = height-10-2*BorderV;
        textField.setMinimumSize(new Dimension(widthTextField, heightTextField));
        textField.setMaximumSize(new Dimension(widthTextField, heightTextField));
        textField.setPreferredSize(new Dimension(widthTextField, heightTextField));
        textField.setBounds(BorderH+(width-2*BorderH)/3, BorderV+5, widthTextField, heightTextField);
        textField.setBackground(new Color(39,54,90));
        textField.setForeground(text_color);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setCaretColor(Color.WHITE);
        ((AbstractDocument) textField.getDocument ()).setDocumentFilter (new CustomDocumentFilter (min, max));
        Font font = new Font(text_font, Font.PLAIN, text_size);
        textField.setFont(font);
        this.add(textField);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getY() >= BorderV && e.getY() < height-BorderV) {
                    if (e.getX() >= BorderH && e.getX() < (width - 2 * BorderH) / 4) clickL = true;
                    else if (e.getX() >= width-BorderH-(width - 2 * BorderH) / 4 && e.getX() < width-BorderH) clickR = true;
                }
                repaint(BorderH, BorderV, width-1-BorderH, height-1-BorderV);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (clickL){
                    if (value > min) --value;
                    clickL = false;
                }
                else if (clickR){
                    if (value < max) ++value;
                    clickR = false;
                }
                repaint(BorderH, BorderV, width-1-BorderH, height-1-BorderV);
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.fillRoundRect(BorderH, BorderV, width-2*BorderH, height-2*BorderV, arcs.width, arcs.height);//paint background
        g2.setColor(this.getParent().getBackground());
        g2.fillRoundRect(BorderH+thick, BorderV+thick, width-2*BorderH-thick*2, height-2*BorderV-thick*2, arcs.width, arcs.height);
        if (clickL)g2.setColor(color_click);
        else g2.setColor(color);
        g2.setStroke(new BasicStroke(3f));
        g2.drawLine(BorderH+(width-2*BorderH)/8, height/2, BorderH+(width-2*BorderH)/8+5, height/2-5);
        g2.drawLine(BorderH+(width-2*BorderH)/8, height/2, BorderH+(width-2*BorderH)/8+5, height/2+5);
        textField.setText(String.valueOf(value));
        if (clickR)g2.setColor(color_click);
        else g2.setColor(color);
        g2.drawLine(width-BorderH-(width-2*BorderH)/8-5, height/2-5, width-BorderH-(width-2*BorderH)/8, height/2);
        g2.drawLine(width-BorderH-(width-2*BorderH)/8-5, height/2+5, width-BorderH-(width-2*BorderH)/8, height/2);
    }
    public int getValue() {
        return value;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setColor_click(Color color_click) {
        this.color_click = color_click;
    }

    public void setText_color(Color text_color) {
        this.text_color = text_color;
    }

    public void setText_font(String text_font) {
        this.text_font = text_font;
    }

    public void setText_size(int text_size) {
        this.text_size = text_size;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setArcs(Dimension arcs) {
        this.arcs = arcs;
    }

    public void setBorderV(int borderV) {
        BorderV = borderV;
    }

    public void setBorderH(int borderH) {
        BorderH = borderH;
    }

    public void setThick(int thick) {
        this.thick = thick;
    }

    public void setClickL(Boolean clickL) {
        this.clickL = clickL;
    }

    public void setClickR(Boolean clickR) {
        this.clickR = clickR;
    }
    private class CustomDocumentFilter extends DocumentFilter
    {
        private int min, max;

        public CustomDocumentFilter(int min, int max) {
            this.min = min;
            this.max = max;
        }

        @Override public void replace (FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            String textAfterReplacement = new StringBuilder (fb.getDocument ().getText (0, fb.getDocument ().getLength ())).replace (offset, offset + length, text).toString ();
            try {
                int value = Integer.parseInt (textAfterReplacement);
                if (value < min) value = min;
                else if (value > max) value = max;
                super.replace (fb, 0, fb.getDocument ().getLength (), String.valueOf (value), attrs);
            }
            catch (NumberFormatException e) {
                // Handle exception ...
                int value = (min+max)/2;
                super.replace (fb, 0, fb.getDocument ().getLength (), String.valueOf (value), attrs);
            }
        }
    }
}
