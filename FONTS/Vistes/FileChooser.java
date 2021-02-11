package Vistes;

import Controladors.ControladorPresentacio;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicListUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class FileChooser {
    private JButton minimizeButton;
    private JButton closeButton;
    private JPanel contentPanel;
    private JPanel panelFileChooser;
    private MyButton boton;
    private JScrollPane scrollPane;
    private JList list;
    private ControladorPresentacio controladorPresentacio;
    private JFrame frameVista;
    private int width, height;
    private JPanel barraPrograma;
    private JPanel title;
    private JLabel titleWindow;
    private JPanel panel;
    private MyLoadingAnimation loadingAnimation;
    private boolean llegint;
    private int widthPanel, heightPanel;
    public FileChooser(ControladorPresentacio controladorPresentacio) {
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
        llegint = false;
        inicializar_frameVista();
        asignar_listenersComponentes();
    }

    private void inicializar_frameVista() {
        frameVista = new JFrame("fileChooser");
        int pix = 10;
        width = 540 + 2 * pix;
        height = 435 + 2 * pix;

        frameVista.setMinimumSize(new Dimension(width, height));
        frameVista.setMaximumSize(new Dimension(width, height));
        frameVista.setPreferredSize(new Dimension(width, height));
        frameVista.setResizable(false);

        frameVista.setLocationRelativeTo(null);
        contentPanel = new JPanel(null);
        widthPanel = 540;
        heightPanel = 400;

        contentPanel.setMinimumSize(new Dimension(widthPanel, heightPanel));
        contentPanel.setMaximumSize(new Dimension(widthPanel, heightPanel));
        contentPanel.setPreferredSize(new Dimension(widthPanel, heightPanel));
        contentPanel.setBackground(new Color(39, 54, 90));
        contentPanel.setBounds(0, height - heightPanel, widthPanel, heightPanel);
        inicializar_lista();
        inicializar_boton();
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

    private void inicializar_lista(){
        DefaultListModel listModel = new DefaultListModel();
        String[] files = controladorPresentacio.getRepositoris();
        for(int i = 0; i< files.length; ++i){
            listModel.addElement(files[i]);
        }

        //Create the list and put it in a scroll pane.
        int widthList = 300;
        int heightList = 35*files.length;
        list = new JList(){
            @Override
            public void updateUI() {
                setUI(new VistaResoldre.CustomListUI());
            }
        };
        list.setModel(listModel);
        list.setMaximumSize(new Dimension(widthList, heightList));
        list.setMinimumSize(new Dimension(widthList, heightList));
        list.setPreferredSize(new Dimension(widthList, heightList));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFixedCellHeight(35);
        list.setBackground(new Color(39,54,90));

        scrollPane = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER){
            @Override public void setBorder(Border border) {
                // No!
            }
        };
        scrollPane.setVerticalScrollBar( new JScrollBar(){
            @Override
            public void updateUI() {
                setUI(new VistaResoldre.CustomScrollBarUI());
            }
        });

        int widthScroll = widthList;
        int heightScroll = 250;
        scrollPane.setMaximumSize(new Dimension(widthScroll, heightScroll));
        scrollPane.setMinimumSize(new Dimension(widthScroll, heightScroll));
        scrollPane.setPreferredSize(new Dimension(widthScroll, heightScroll));
        scrollPane.setBounds(widthPanel/3-widthScroll/2, heightPanel/2-heightScroll/2, widthScroll, heightScroll);
        contentPanel.add(scrollPane);
    }
    private void inicializar_boton(){
        int widthButton = 160;
        int heightButton = 60;
        boton = new MyButton(widthButton, heightButton, "Aceptar");
        boton.setBounds(9*width/12-widthButton/2, height/2-heightButton, widthButton, heightButton);
        contentPanel.add(boton);
    }
    private void asignar_listenersComponentes() {

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (llegint) return;
                controladorPresentacio.canviMenuJugar();
                frameVista.dispose();
            }
        });
        minimizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (llegint) return;
                frameVista.setState(Frame.ICONIFIED);
            }
        });
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (llegint) return;
                super.mouseReleased(e);
                if (list.getSelectedIndex() != -1) {
                    loadingAnimation = new MyLoadingAnimation();
                    loadingAnimation.setBounds(9*width/12-loadingAnimation.getWidth()/2, height/2-loadingAnimation.getHeight(), loadingAnimation.getWidth(), loadingAnimation.getHeight());
                    loadingAnimation.setBackground(new Color(39, 54, 90));
                    loadingAnimation.setVisible(true);
                    contentPanel.add(loadingAnimation);
                    boton.setVisible(false);
                    llegint = true;
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            controladorPresentacio.repositoriKakuroEscollit(list.getSelectedValue().toString());
                            frameVista.dispose();
                        }
                    });
                    t.start();
                }
            }
        });
    }

    static class CustomScrollBarUI extends BasicScrollBarUI {

        private Color thumbColor = new Color(246, 146, 36);
        private Color trackColor = new Color(23,33,53);
        private int arc = 10;
        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            // your code
            Graphics2D g2 = (Graphics2D)g.create();
            RenderingHints qualityHints =
                    new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHints(qualityHints);

            g.setColor(trackColor);
            g.fillRoundRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height,10,10);
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D)g.create();
            RenderingHints qualityHints =
                    new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHints(qualityHints);

            g.setColor(thumbColor);
            g.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, arc, arc);
        }

        protected JButton createZeroButton() {
            JButton button = new JButton("zero button");
            Dimension zeroDim = new Dimension(0,0);
            button.setPreferredSize(zeroDim);
            button.setMinimumSize(zeroDim);
            button.setMaximumSize(zeroDim);
            return button;
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

    }
    static class CustomListUI extends BasicListUI {
        private Color textColor = new Color(240,240,240);
        private Color selectedCellColor = new Color(23,33,53);
        private Color cellColor = new Color(32, 46, 78);
        private int margin = 5;
        private int arc = 20;
        public CustomListUI() {

        }

        @Override
        protected void paintCell(Graphics g, int row, Rectangle rowBounds, ListCellRenderer<Object> cellRenderer, ListModel<Object> dataModel, ListSelectionModel selModel, int leadIndex) {
            if (leadIndex == row) g.setColor(selectedCellColor);
            else g.setColor(cellColor);
            g.fillRoundRect(rowBounds.x+margin,rowBounds.y+margin,rowBounds.width-2*margin, rowBounds.height-2*margin, arc, arc);
            g.setColor(textColor);
            String aux[] = dataModel.getElementAt(row).toString().split(",");
            String puntuacio = aux[aux.length-1];
            String name = row+1+". "+dataModel.getElementAt(row).toString().replaceAll(","+puntuacio+"$", "");
            drawStringLeft(g, name, rowBounds.x+4*margin,rowBounds.y+(rowBounds.height+margin)/2, rowBounds.height/2);
            drawStringRight(g, puntuacio, rowBounds.width-4*margin,rowBounds.y+(rowBounds.height+margin)/2, rowBounds.height/2);
        }
        void drawStringLeft(Graphics g, String s, int x, int y, int size) {
            Font font = new Font("Arial", Font.PLAIN, size);
            g.setFont(font);
            Rectangle2D bounds = g.getFontMetrics().getStringBounds(s, g);
            g.drawString(s, x, (int) (y - bounds.getCenterY()));
        }
        void drawStringRight(Graphics g, String s, int x, int y, int size) {
            Font font = new Font("Arial", Font.PLAIN, size);
            g.setFont(font);
            Rectangle2D bounds = g.getFontMetrics().getStringBounds(s, g);
            g.drawString(s, (int) (x - bounds.getWidth()), (int) (y - bounds.getCenterY()));
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
