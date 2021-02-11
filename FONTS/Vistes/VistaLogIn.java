package Vistes;

import Controladors.ControladorPresentacio;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicTextFieldUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class VistaLogIn {
    private ControladorPresentacio controladorPresentacio;
    private JPanel panel;
    private JPanel barraPrograma;
    private JPanel title;
    private JLabel titleWindow;
    private JButton minimizeButton;
    private JButton closeButton;
    private JPanel contentPanel;
    private MyButton boton;
    private MyTextField textField;
    private JFrame frameVista;
    private JLabel labelUsuari;
    int width, height, widthPanel, heightPanel;

    public VistaLogIn(ControladorPresentacio controladorPresentacio) {
        this.controladorPresentacio = controladorPresentacio;
        inicializarComponentes();

    }

    public void hacerVisible() {
        frameVista.pack();
        frameVista.setVisible(true);
    }

    private void inicializarComponentes() {
        inicializar_frameVista();
        asignar_listenersComponentes();
    }

    private void inicializar_textField() {
        textField = new MyTextField();
        int widthText = 160;
        int heightText = 30;

        textField.setMinimumSize(new Dimension(widthText, heightText));
        textField.setMaximumSize(new Dimension(widthText, heightText));
        textField.setPreferredSize(new Dimension(widthText, heightText));
        textField.setBounds(widthPanel / 2 - widthText / 2, heightPanel / 2 - heightText, widthText, heightText);
        contentPanel.add(textField);
    }
    private void crearLabels(){
        labelUsuari = new JLabel("Usuari");
        int widthLabel = 160;
        int heightLabel = 35;
        Font font = new Font("Arial", Font.PLAIN, 20);
        labelUsuari.setFont(font);
        labelUsuari.setForeground(new Color(230,230,230));
        labelUsuari.setBackground(new Color(39,54,90));
        labelUsuari.setHorizontalAlignment(SwingConstants.CENTER);
        labelUsuari.setMaximumSize(new Dimension(widthLabel, heightLabel));
        labelUsuari.setMinimumSize(new Dimension(widthLabel, heightLabel));
        labelUsuari.setPreferredSize(new Dimension(widthLabel, heightLabel));
        labelUsuari.setBounds(widthPanel/2-widthLabel/2, heightPanel/2-2*heightLabel, widthLabel, heightLabel);
        contentPanel.add(labelUsuari);
    }

    private void inicializar_frameVista() {

        frameVista = new JFrame("logIn");
        int pix = 10;
        width = 350 + 2 * pix;
        height = 235 + 2 * pix;

        frameVista.setMinimumSize(new Dimension(width, height));
        frameVista.setMaximumSize(new Dimension(width, height));
        frameVista.setPreferredSize(new Dimension(width, height));
        frameVista.setResizable(false);

        frameVista.setLocationRelativeTo(null);
        contentPanel = new JPanel(null);
        widthPanel = 350;
        heightPanel = 200;

        contentPanel.setMinimumSize(new Dimension(widthPanel, heightPanel));
        contentPanel.setMaximumSize(new Dimension(widthPanel, heightPanel));
        contentPanel.setPreferredSize(new Dimension(widthPanel, heightPanel));
        contentPanel.setBackground(new Color(39, 54, 90));
        contentPanel.setBounds(0, height - heightPanel, widthPanel, heightPanel);
        inicializar_textField();
        inicializar_boton();
        crearLabels();
        panel.add(contentPanel);
        frameVista.setUndecorated(true);
        VistaMain.FrameDragListener frameDragListener = new VistaMain.FrameDragListener(frameVista);
        frameVista.addMouseListener(frameDragListener);
        frameVista.addMouseMotionListener(frameDragListener);
        frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameVista.setBackground(new Color(0f, 0f, 0f, 0f));
        JPanel shadow = new DropShadowPanel(width, height, pix);
        shadow.add(panel);
        frameVista.add(shadow);
        frameVista.pack();
    }

    private void inicializar_boton() {
        int widthButton = 160;
        int heightButton = 60;
        boton = new MyButton(widthButton, heightButton, "Acceptar");
        boton.setBounds(widthPanel / 2 - widthButton/2, heightPanel / 2, widthButton, heightButton);
        contentPanel.add(boton);
    }

    private void asignar_listenersComponentes() {

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorPresentacio.canviMenuJugar();
                frameVista.dispose();
            }
        });
        minimizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameVista.setState(Frame.ICONIFIED);
            }
        });
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                String user = textField.getText().replace("\"#@,;:<>*^|?\\/", "");
                if (user.length() > 0) {
                    controladorPresentacio.canviLogInMenu(textField.getText());
                    frameVista.dispose();
                }
            }
        });
    }

    private class DropShadowPanel extends JPanel {

        private static final long serialVersionUID = 1L;

        public int pixels;

        public DropShadowPanel(int width, int height, int pix) {
            this.pixels = pix;
            Border border = BorderFactory.createEmptyBorder(pixels, pixels, pixels, pixels);
            this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), border));
            this.setLayout(new BorderLayout());
            this.setMinimumSize(new Dimension(width, height));
            this.setMaximumSize(new Dimension(width, height));
            this.setPreferredSize(new Dimension(width, height));
        }

        @Override
        protected void paintComponent(Graphics g) {
            int shade = 0;
            int topOpacity = 80;
            for (int i = 0; i < pixels; i++) {
                g.setColor(new Color(shade, shade, shade, ((topOpacity / pixels) * i)));
                g.drawRect(i, i, this.getWidth() - ((i * 2) + 1), this.getHeight() - ((i * 2) + 1));
            }
        }
    }
}

