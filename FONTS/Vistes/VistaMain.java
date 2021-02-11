package Vistes;

import Controladors.ControladorPresentacio;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VistaMain {
    private ControladorPresentacio controladorPresentacio;
    private JButton crearKakuroButton;
    private JPanel panel;
    private JPanel contentPanel;
    private JPanel menuPanel;
    private JButton closeButton;
    private JPanel barraPrograma;
    private JLabel titleWindow;
    private JButton minimizeButton;
    private JPanel jugarOption;
    private JPanel rankingOption;
    private JPanel title;
    private JPanel window;
    private JPanel editOption;
    private JPanel repositoriOption;
    private JPanel resoldreOption;
    private JPanel helpOption;
    private JPanel infoOption;
    private JPanel closeSessionOption;
    private JPanel userPanel;
    private JLabel userName;
    private JFrame frameVista;
    private JPanel[] botonesMenu;
    private int buttonClicked = -1;
    public VistaMain(ControladorPresentacio controladorPresentacio) {
        this.controladorPresentacio = controladorPresentacio;
        inicializarComponentes();

    }

    public void hacerVisible(){
        frameVista.pack();
        frameVista.setVisible(true);
    }
    public void activar() {
        frameVista.setEnabled(true);
    }

    public void desactivar() {
        frameVista.setEnabled(false);
    }

    private void inicializarComponentes() {
        inicializar_frameVista();
        crearBotonesMenu();
        asignar_listenersComponentes();
    }

    private void inicializar_frameVista() {

        frameVista = new JFrame("Main");
        int pix = 8;
        int width = 1080+2*pix;
        int height = 720+2*pix;
        frameVista.setMinimumSize(new Dimension(width,height));
        frameVista.setMaximumSize(new Dimension(width,height));
        frameVista.setPreferredSize(new Dimension(width,height));
        frameVista.setResizable(false);

        frameVista.setLocationRelativeTo(null);

        frameVista.setUndecorated(true);
        FrameDragListener frameDragListener = new FrameDragListener(frameVista);
        frameVista.addMouseListener(frameDragListener);
        frameVista.addMouseMotionListener(frameDragListener);
        frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameVista.setBackground(new Color(0f, 0f, 0f, 0f));
        JPanel shadow = new DropShadowPanel(width,height, pix);
        shadow.add(panel);
        frameVista.add(shadow);
        frameVista.pack();
    }
    public void setContent(JPanel content){
        contentPanel.removeAll();
        contentPanel.add(content);
        SwingUtilities.updateComponentTreeUI(frameVista);
    }

    private void crearBotonesMenu(){
        botonesMenu = new JPanel[8];
        botonesMenu[0] = jugarOption;
        botonesMenu[1] = rankingOption;
        botonesMenu[2] = editOption;
        botonesMenu[3] = repositoriOption;
        botonesMenu[4] = resoldreOption;
        botonesMenu[5] = helpOption;
        botonesMenu[6] = infoOption;
        botonesMenu[7] = closeSessionOption;
    }
    public void setOptionMenu(int option, Boolean click){
        if (buttonClicked != -1){
            botonesMenu[buttonClicked].setBackground(new Color(23,33,53));
            botonesMenu[buttonClicked].repaint();
        }
        if (!click)botonesMenu[option].setBackground(new Color(39,55,80));
        else botonesMenu[option].setBackground(new Color(59,75,100));
        botonesMenu[option].repaint();
        buttonClicked = option;
    }
    private void asignar_listenersComponentes() {

        jugarOption.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setOptionMenu(0, true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setOptionMenu(0, false);
                controladorPresentacio.canviMenuJugar();
            }
        });
        rankingOption.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setOptionMenu(1, true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setOptionMenu(1, false);
                controladorPresentacio.canviMenuRanking();
            }
        });
        editOption.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setOptionMenu(2, true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setOptionMenu(2, false);
                controladorPresentacio.canviMenuEditar();
            }
        });
        repositoriOption.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setOptionMenu(3, true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setOptionMenu(3, false);
                controladorPresentacio.canviMenuRepositori();
            }
        });
        resoldreOption.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setOptionMenu(4, true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setOptionMenu(4, false);
                controladorPresentacio.canviMenuResoldre();
            }
        });

        helpOption.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setOptionMenu(5, true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setOptionMenu(5, false);
                controladorPresentacio.canviMenuHelp();
            }
        });

        infoOption.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setOptionMenu(6, true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setOptionMenu(6, false);
                controladorPresentacio.canviMenuInfo();
            }
        });
        closeSessionOption.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setOptionMenu(7, true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setOptionMenu(7, false);
                frameVista.dispose();
                controladorPresentacio.canviMenuLogIn();
            }
        });
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameVista.dispose();
            }
        });
        minimizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameVista.setState(Frame.ICONIFIED);
            }
        });
    }

    public void setName(String text) {
        userName.setText(text);
    }

    public static class FrameDragListener extends MouseAdapter {

        private final JFrame frame;
        private Point mouseDownCompCoords = null;

        public FrameDragListener(JFrame frame) {
            this.frame = frame;
        }

        public void mouseReleased(MouseEvent e) {
            mouseDownCompCoords = null;
        }

        public void mousePressed(MouseEvent e) {
            mouseDownCompCoords = e.getPoint();
        }

        public void mouseDragged(MouseEvent e) {
            Point currCoords = e.getLocationOnScreen();
            frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
        }
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
