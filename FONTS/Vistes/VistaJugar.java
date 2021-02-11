package Vistes;

import Controladors.ControladorPresentacio;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.net.URL;

public class VistaJugar {
    private ControladorPresentacio controladorPresentacio;
    private JPanel panel;
    private int width, height;
    private int dificultad = 1;
    private JLabel labelCol;
    private JLabel labelFil;
    private JLabel labelDif;
    private JLabel labelJugar;
    private MySpinner numColSpinner;
    private MySpinner numFilSpinner;
    private MyButton generarBtn;
    private MyButton continuarBtn;
    private MyButton llegirBtn;
    private JSlider slider;
    private MyLoadingAnimation loadingAnimation;
    private boolean buttonsEnabled;

    public VistaJugar(ControladorPresentacio controladorPresentacio) {
        this.controladorPresentacio = controladorPresentacio;
        inicialitzarComponents();
    }
    private void crearLabels(){
        int widthLabel = 400;
        int heightLabel = 60;

        labelDif = new JLabel("Dificultat");
        Font font = new Font("Arial", Font.PLAIN, 25);
        labelDif.setFont(font);
        labelDif.setForeground(new Color(230,230,230));
        labelDif.setBackground(new Color(39,54,90));
        labelDif.setHorizontalAlignment(SwingConstants.CENTER);
        labelDif.setMaximumSize(new Dimension(widthLabel, heightLabel));
        labelDif.setMinimumSize(new Dimension(widthLabel, heightLabel));
        labelDif.setPreferredSize(new Dimension(widthLabel, heightLabel));
        labelDif.setBounds(width/2-widthLabel/2, height/2-3*heightLabel/2, widthLabel, heightLabel);
        labelJugar = new JLabel("Jugar");
        widthLabel = 400;
        heightLabel = 60;
        font = new Font("Arial", Font.BOLD, 40);
        labelJugar.setFont(font);
        labelJugar.setForeground(new Color(230,230,230));
        labelJugar.setBackground(new Color(39,54,90));
        labelJugar.setHorizontalAlignment(SwingConstants.CENTER);
        labelJugar.setMaximumSize(new Dimension(widthLabel, heightLabel));
        labelJugar.setMinimumSize(new Dimension(widthLabel, heightLabel));
        labelJugar.setPreferredSize(new Dimension(widthLabel, heightLabel));
        labelJugar.setBounds(width/2-widthLabel/2, heightLabel/2, widthLabel, heightLabel);
    }
    private void crearSpinner(){
        int widthLabel = 250;
        int heightLabel = 60;
        int widthSpinner = 130;
        int heightSpinner = 60;
        int spacer = 30;
        Font font = new Font("Arial", Font.PLAIN, 25);
        labelCol = new JLabel("Nombre de columnes");
        labelFil = new JLabel("Nombre de fileres");
        labelCol.setFont(font);
        labelCol.setForeground(new Color(230,230,230));
        labelCol.setBackground(new Color(39,54,90));
        labelCol.setHorizontalAlignment(SwingConstants.LEFT);
        labelCol.setMaximumSize(new Dimension(widthLabel, heightLabel));
        labelCol.setMinimumSize(new Dimension(widthLabel, heightLabel));
        labelCol.setPreferredSize(new Dimension(widthLabel, heightLabel));
        labelCol.setBounds(width/2-(widthLabel+widthSpinner)/2, height/6, widthLabel, heightLabel);
        font = new Font("Arial", Font.PLAIN, 25);
        labelFil.setFont(font);
        labelFil.setForeground(new Color(230,230,230));
        labelFil.setBackground(new Color(39,54,90));
        labelFil.setHorizontalAlignment(SwingConstants.LEFT);
        labelFil.setMaximumSize(new Dimension(widthLabel, heightLabel));
        labelFil.setMinimumSize(new Dimension(widthLabel, heightLabel));
        labelFil.setPreferredSize(new Dimension(widthLabel, heightLabel));
        labelFil.setBounds(width/2-(widthLabel+widthSpinner)/2, height/4, widthLabel, heightLabel);
        numColSpinner = new MySpinner(widthSpinner, heightSpinner, 5,10);
        numFilSpinner = new MySpinner(widthSpinner, heightSpinner, 5,10);
        numFilSpinner.setBounds(width/2-(widthLabel+widthSpinner)/2+widthLabel, height/4, widthSpinner, heightSpinner);
        numColSpinner.setBounds(width/2-(widthLabel+widthSpinner)/2+widthLabel, height/6, widthSpinner, heightSpinner);
    }
    private void crearSlider(){
        int widthSlider = 400;
        int heightSlider = 100;
        slider = new JSlider(0, 100) {
            @Override
            public void updateUI() {
                setUI(new CustomSliderUI(this));
            }
        };
        slider.setBackground(new Color(39,54,90));
        slider.setMaximumSize(new Dimension(widthSlider, heightSlider));
        slider.setMinimumSize(new Dimension(widthSlider, heightSlider));
        slider.setPreferredSize(new Dimension(widthSlider, heightSlider));
        slider.setBounds(width/2-widthSlider/2, height/2-heightSlider/2, widthSlider, heightSlider);
    }
    private void crearBotons(){
        int widthButton = 200;
        int heightButton = 60;
        generarBtn = new MyButton(widthButton, heightButton, "Generar");
        generarBtn.setBounds(width/2-widthButton/2, height/2+heightButton, widthButton, heightButton);
        continuarBtn = new MyButton(widthButton, heightButton, "Continuar");
        continuarBtn.setVisible(controladorPresentacio.podemContinuarPartida());
        llegirBtn = new MyButton(widthButton, heightButton, "Llegir");
        if (controladorPresentacio.podemContinuarPartida()){
            continuarBtn.setBounds(width/2-widthButton/2, height/2+2*heightButton, widthButton, heightButton);
            llegirBtn.setBounds(width/2-widthButton/2, height/2+3*heightButton, widthButton, heightButton);
        }
        else{
            llegirBtn.setBounds(width/2-widthButton/2, height/2+2*heightButton, widthButton, heightButton);
        }
    }
    private void asignar_listenersComponentes() {
        slider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (slider.getValue() <= 25){
                    slider.setValue(0);
                    dificultad = 0;
                }
                else if (slider.getValue() > 25 && slider.getValue() < 75){
                    slider.setValue(50);
                    dificultad = 1;
                }
                else{
                    slider.setValue(100);
                    dificultad = 2;
                }
            }
        });

        generarBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!buttonsEnabled) return;
                super.mouseReleased(e);
                loadingAnimation = new MyLoadingAnimation();
                loadingAnimation.setBounds(width/2-loadingAnimation.getWidth()/2,generarBtn.getY(),loadingAnimation.getWidth(),loadingAnimation.getHeight());
                loadingAnimation.setBackground(new Color(39,54,90));
                loadingAnimation.setVisible(true);
                generarBtn.setVisible(false);
                panel.add(loadingAnimation);
                buttonsEnabled = false;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        controladorPresentacio.canviJugarKakuro(numFilSpinner.getValue(), numColSpinner.getValue(), dificultad);
                    }
                });
                thread.start();
            }
        });
        llegirBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!buttonsEnabled) return;
                super.mouseReleased(e);
                controladorPresentacio.escollirRepositoriKakuro();
            }
        });
        continuarBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!buttonsEnabled) return;
                super.mouseReleased(e);
                controladorPresentacio.canviJugarKakuro();
            }
        });
    }
    private void inicialitzarComponents(){
        panel = new JPanel(null);
        width = 930;
        height = 685;
        panel.setMinimumSize(new Dimension(width, height));
        panel.setMaximumSize(new Dimension(width, height));
        panel.setPreferredSize(new Dimension(width, height));
        panel.setBackground(new Color(39,54,90));

        buttonsEnabled = true;
        crearLabels();
        crearSpinner();
        crearSlider();
        crearBotons();
        asignar_listenersComponentes();

        panel.add(labelFil);
        panel.add(numFilSpinner);
        panel.add(labelCol);
        panel.add(numColSpinner);
        panel.add(labelDif);
        panel.add(slider);
        panel.add(generarBtn);
        panel.add(continuarBtn);
        panel.add(llegirBtn);
        panel.add(labelJugar);
    }
    private void actualitzarButons(){
        int widthButton = 200;
        int heightButton = 60;
        continuarBtn.setVisible(controladorPresentacio.podemContinuarPartida());
        if (controladorPresentacio.podemContinuarPartida()){
            continuarBtn.setBounds(width/2-widthButton/2, height/2+2*heightButton, widthButton, heightButton);
            llegirBtn.setBounds(width/2-widthButton/2, height/2+3*heightButton, widthButton, heightButton);
        }
        else{
            llegirBtn.setBounds(width/2-widthButton/2, height/2+2*heightButton, widthButton, heightButton);
        }
    }
    public JPanel getContent(){
        actualitzarButons();
        return panel;
    }

    public void reset() {
        buttonsEnabled = true;
        generarBtn.setVisible(true);
        loadingAnimation.setVisible(false);
    }


    static class CustomSliderUI extends BasicSliderUI {

        private static final int TRACK_HEIGHT = 8;
        private static final int TRACK_WIDTH = 8;
        private static final int TRACK_ARC = 5;
        private static final Dimension THUMB_AUX_SIZE = new Dimension(20, 20);
        private static final Dimension THUMB_SIZE = new Dimension(40, 40);
        private final RoundRectangle2D.Float trackShape = new RoundRectangle2D.Float();
        private Color trackColor = new Color(246, 146, 36);
        private Color thumbAuxColor = new Color(246, 146, 36);
        private Color thumbColor = new Color(246, 146, 36);
        private Color textColor = new Color(240,240,240);
        public CustomSliderUI(final JSlider b) {
            super(b);
        }

        @Override
        protected void calculateTrackRect() {
            super.calculateTrackRect();
            if (isHorizontal()) {
                trackRect.y = trackRect.y + (trackRect.height - TRACK_HEIGHT) / 2;
                trackRect.height = TRACK_HEIGHT;
            } else {
                trackRect.x = trackRect.x + (trackRect.width - TRACK_WIDTH) / 2;
                trackRect.width = TRACK_WIDTH;
            }
            trackShape.setRoundRect(trackRect.x, trackRect.y, trackRect.width, trackRect.height, TRACK_ARC, TRACK_ARC);
        }

        @Override
        protected void calculateThumbLocation() {
            super.calculateThumbLocation();
            if (isHorizontal()) {
                thumbRect.y = trackRect.y + (trackRect.height - thumbRect.height) / 2;
            } else {
                thumbRect.x = trackRect.x + (trackRect.width - thumbRect.width) / 2;
            }
        }

        @Override
        protected Dimension getThumbSize() {
            return THUMB_SIZE;
        }

        private boolean isHorizontal() {
            return slider.getOrientation() == JSlider.HORIZONTAL;
        }

        @Override
        public void paint(final Graphics g, final JComponent c) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            super.paint(g, c);
        }

        @Override
        public void paintTrack(final Graphics g) {

            Graphics2D g2 = (Graphics2D) g;
            Shape clip = g2.getClip();
            g2.clipRect(0, 0, slider.getWidth(), slider.getHeight());
            g2.setColor(trackColor);
            g2.fill(trackShape);
            g2.setClip(clip);
            g2.setColor(thumbAuxColor);
            g2.fillOval(trackRect.x-THUMB_AUX_SIZE.width/4, trackRect.y-THUMB_AUX_SIZE.height/4, THUMB_AUX_SIZE.width, THUMB_AUX_SIZE.height);
            g2.fillOval(trackRect.x+trackRect.width/2-THUMB_AUX_SIZE.width/4, trackRect.y-THUMB_AUX_SIZE.height/4, THUMB_AUX_SIZE.width, THUMB_AUX_SIZE.height);
            g2.fillOval(trackRect.x+trackRect.width-THUMB_AUX_SIZE.width/4, trackRect.y-THUMB_AUX_SIZE.height/4, THUMB_AUX_SIZE.width, THUMB_AUX_SIZE.height);
            g2.setColor(textColor);
            if (slider.getValue() == 0) drawStringCentered(g2, "F\u00e0cil", trackRect.x, trackRect.y+THUMB_SIZE.height,20);
            else if (slider.getValue() == slider.getMaximum()/2) drawStringCentered(g2, "Normal", trackRect.x+trackRect.width/2, trackRect.y+THUMB_SIZE.height,20);
            else drawStringCentered(g2, "Dif\u00edcil", trackRect.x+trackRect.width-THUMB_AUX_SIZE.height/4, trackRect.y+THUMB_SIZE.height,20);
        }

        @Override
        public void paintThumb(final Graphics g) {
            g.setColor(thumbColor);
            g.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
        }

        @Override
        public void paintFocus(final Graphics g) {}
        void drawStringCentered(Graphics g, String s, int x, int y, int size) {
            Font font = new Font("Arial", Font.PLAIN, size);
            g.setFont(font);
            Rectangle2D bounds = g.getFontMetrics().getStringBounds(s, g);
            g.drawString(s, (int) (x - bounds.getCenterX()), (int) (y - bounds.getCenterY()));
        }
    }

}
