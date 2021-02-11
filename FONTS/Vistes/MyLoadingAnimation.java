package Vistes;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class MyLoadingAnimation extends JPanel {
    private final int width = 48;
    private final int height = 48;
    private int angle = 0;
    private BufferedImage image;
    public MyLoadingAnimation() {
        this.setMinimumSize(new Dimension(width, height));
        this.setMaximumSize(new Dimension(width, height));
        this.setPreferredSize(new Dimension(width, height));
        int interval = 30;
        try {
            image = ImageIO.read(new File("src\\Images\\rolling.png"));
        }
        catch(Exception e){
            System.out.println("Error llegint rolling");
        }
        Timer timer = new Timer(interval, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                angle += 10;
                repaint();
            }
        });
        timer.start();
        this.setBackground(new Color(0,0,0,0));
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        double rotationRequired = Math.toRadians (angle);
        double locationX = image.getWidth() / 2;
        double locationY = image.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        if (image != null) g.drawImage(op.filter(image, null), 0, 0, null);;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
