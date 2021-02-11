package Vistes;

import Controladors.ControladorPresentacio;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicListUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class VistaResoldre {
    private ControladorPresentacio controladorPresentacio;
    private JPanel panel;
    private JScrollPane scrollPane;
    private celaComponent[][] celes;
    private MyButton resoldre;
    private DefaultListModel listModel;
    private int width,height;
    private String[] repositoris;
    private JLabel labelTemps;
    private JLabel labelResoldre;
    private JList list;
    private MyLoadingAnimation loadingAnimation;

    JPanel boardKakuro;

    public VistaResoldre(ControladorPresentacio controladorPresentacio) {
        this.controladorPresentacio = controladorPresentacio;
        inicialitzarComponents();
    }
    private void inicializar_lista(){
        listModel = new DefaultListModel();
        repositoris = controladorPresentacio.getRepositoris();
        for(int i = 0; i< repositoris.length; ++i){
            listModel.add(i, repositoris[i]);
        }

        //Create the list and put it in a scroll pane.
        int widthList = 300;
        int heightList = 35*repositoris.length;
        list = new JList(){
            @Override
            public void updateUI() {
                setUI(new CustomListUI());
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
                setUI(new CustomScrollBarUI());
            }
        });

        int widthScroll = widthList;
        int heightScroll = 400;
        scrollPane.setMaximumSize(new Dimension(widthScroll, heightScroll));
        scrollPane.setMinimumSize(new Dimension(widthScroll, heightScroll));
        scrollPane.setPreferredSize(new Dimension(widthScroll, heightScroll));
        scrollPane.setBounds(width/3-2*widthScroll/3, height/2-heightScroll/2, widthScroll, heightScroll);
    }
    private void crearBoto(){
        int widthButton = 150;
        int heightButton = 70;
        resoldre = new MyButton(widthButton, heightButton, "Resoldre");
        resoldre.setBounds(2*width/3-widthButton/2, height-heightButton*2, widthButton, heightButton);
    }
    private void inicialitzarComponents(){
        panel = new JPanel(null);
        width = 930;
        height = 685;
        panel.setMaximumSize(new Dimension(width, height));
        panel.setMinimumSize(new Dimension(width, height));
        panel.setPreferredSize(new Dimension(width, height));
        panel.setBackground(new Color(39,54,90));
        boardKakuro = new JPanel();

        crearBoto();
        inicializar_lista();
        crearLabel();
        if (repositoris.length > 0){
            list.setSelectedIndex(0);
            crearKakuro(controladorPresentacio.getRepositori(repositoris[0]));
        }
        asignar_listenersComponentes();


        panel.add(resoldre);
        panel.add(boardKakuro);
        panel.add(scrollPane);
        panel.add(labelTemps);
        panel.add(labelResoldre);
    }


    private void crearKakuro(String kakuroText){
        String[] kakuro = kakuroText.split("\n");
        kakuro[0] = kakuro[0].replace("\n", "");
        String[] size = kakuro[0].split(",");
        int n = Integer.parseInt(size[0].replaceAll("[^0-9.]", ""));
        int m = Integer.parseInt(size[1].replaceAll("[^0-9.]", ""));

        int sizeBoard = 400;
        int sizeBoardH, sizeBoardV;
        if (m > n) {
            sizeBoardH = sizeBoard;
            sizeBoardV = (int) (sizeBoardH * ((float) n / m));
        }
        else{
            sizeBoardV = sizeBoard;
            sizeBoardH = (int) (sizeBoardV * ((float) m / n));
        }
        int celaSizeH = sizeBoardH/m;
        int celaSizeV = sizeBoardV/n;

        boardKakuro.setLayout(new GridLayout(n, m));
        Integer index = 0;
        celes = new celaComponent[n][m];
        for(int i = 0; i< kakuro.length-1; ++i){
            kakuro[i+1] = kakuro[i+1].replace("\n", "");
            String[] line = kakuro[i+1].split(",");
            for(int j = 0; j< line.length; ++j){
                String value = line[j];
                boolean esBlanc = false;
                String valorCelaBlanca = "";
                String valorCelaNegraC = "";
                String valorCelaNegraF = "";

                if (value.contains("?")){
                    esBlanc = true;
                }
                else if (value.charAt(0) == 'C'){
                    String[] valors = value.split("F");
                    if(valors.length==1){
                        valorCelaNegraC += valors[0].replace("C", "");
                    }
                    else if(valors.length==2){
                        valorCelaNegraC += valors[0].replace("C", "");
                        valorCelaNegraF += valors[1];
                    }
                }
                else if(value.charAt(0) == 'F'){
                    valorCelaNegraF += value.replace("F", "");
                }
                else if (value.contains("1") ||value.contains("2") ||value.contains("3") ||value.contains("4")||value.contains("5")||value.contains("6")||value.contains("7")||value.contains("8")||value.contains("9")){
                    esBlanc = true;
                    valorCelaBlanca += value;
                }
                if (esBlanc)celes[i][j] = new celaComponent(celaSizeH,celaSizeV,true, valorCelaBlanca, "");
                else celes[i][j] = new celaComponent(celaSizeH, celaSizeV, false, valorCelaNegraC, valorCelaNegraF);
                celes[i][j].setMaximumSize(new Dimension(celaSizeH, celaSizeV));
                celes[i][j].setMinimumSize(new Dimension(celaSizeH, celaSizeV));
                celes[i][j].setPreferredSize(new Dimension(celaSizeH, celaSizeV));
                boardKakuro.add(celes[i][j]);
            }
        }
        boardKakuro.setMaximumSize(new Dimension(sizeBoardH,sizeBoardV));
        boardKakuro.setMinimumSize(new Dimension(sizeBoardH,sizeBoardV));
        boardKakuro.setPreferredSize(new Dimension(sizeBoardH,sizeBoardV));
        boardKakuro.setBounds(2*width/3-sizeBoardH/2, height/2-sizeBoardV/2, sizeBoardH, sizeBoardV);
    }

    private void asignar_listenersComponentes() {

        resoldre.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int index = list.getSelectedIndex();
                if (index != -1) {
                    loadingAnimation = new MyLoadingAnimation();
                    loadingAnimation.setBounds(2*width/3-loadingAnimation.getWidth()/2, height-7*resoldre.getHeight()/4, loadingAnimation.getWidth(), loadingAnimation.getHeight());
                    loadingAnimation.setBackground(new Color(39, 54, 90));
                    loadingAnimation.setVisible(true);
                    panel.add(loadingAnimation);
                    resoldre.setVisible(false);
                    list.setEnabled(false);
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String kakuro = controladorPresentacio.resoldreKakuro(repositoris[index]);
                            setTempsResoldre(controladorPresentacio.getTempsResoldre());
                            loadingAnimation.setVisible(false);
                            resoldre.setVisible(true);
                            boardKakuro.removeAll();
                            crearKakuro(kakuro);
                            boardKakuro.revalidate();
                            boardKakuro.repaint();
                            list.setEnabled(true);
                        }
                    });
                    t.start();
                }
            }
        });
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = list.getSelectedIndex();
                if (index != -1) {
                    boardKakuro.removeAll();
                    crearKakuro(controladorPresentacio.getRepositori(repositoris[index]));
                    boardKakuro.revalidate();
                    boardKakuro.repaint();
                }
            }
        });
    }
    private void crearLabel(){
        labelTemps = new JLabel("");
        Font font = new Font("Arial Bold", Font.PLAIN, 20);
        labelTemps.setFont(font);
        int widthLabel = 300;
        int heightLabel = 20;
        labelTemps.setBounds(width/2-widthLabel/2,5*heightLabel,widthLabel, heightLabel);
        labelTemps.setMaximumSize(new Dimension(widthLabel,heightLabel));
        labelTemps.setMinimumSize(new Dimension(widthLabel,heightLabel));
        labelTemps.setPreferredSize(new Dimension(widthLabel,heightLabel));
        labelTemps.setForeground(new Color(250,250,250));
        labelTemps.setHorizontalAlignment(SwingConstants.CENTER);
        labelResoldre = new JLabel("Resoldre");
        widthLabel = 400;
        heightLabel = 60;
        font = new Font("Arial", Font.BOLD, 40);
        labelResoldre.setFont(font);
        labelResoldre.setForeground(new Color(230,230,230));
        labelResoldre.setBackground(new Color(39,54,90));
        labelResoldre.setHorizontalAlignment(SwingConstants.CENTER);
        labelResoldre.setMaximumSize(new Dimension(widthLabel, heightLabel));
        labelResoldre.setMinimumSize(new Dimension(widthLabel, heightLabel));
        labelResoldre.setPreferredSize(new Dimension(widthLabel, heightLabel));
        labelResoldre.setBounds(width/2-widthLabel/2, heightLabel/2, widthLabel, heightLabel);
    }

    public void setTempsResoldre(long temps){
        if (labelTemps == null) return;
        labelTemps.setVisible(true);
        labelTemps.setText("La maquina ha tardat "+Long.toString(temps)+" ms");
        panel.revalidate();
        panel.repaint();
    }
    public JPanel getContent(){
        return panel;
    }

    class celaComponent extends JButton{
        private Boolean esBlanc;
        private String val1, val2;
        private Boolean celaSeleccionada;
        int w, h;

        public celaComponent(int w, int h, Boolean esBlanc, String val1, String val2) {
            this.esBlanc = esBlanc;
            this.val1 = val1;
            this.val2 = val2;
            this.w = w;
            this.h = h;
            this.celaSeleccionada = false;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2 = (Graphics2D) g;
            if (this.esBlanc)g2.setColor(Color.WHITE);
            else g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, w, h);

            if (this.esBlanc){
                g2.setColor(Color.BLACK);
                drawStringCentered(g2, val1, w/2, h/2, w/2);
                g2.drawRect(0, 0, w, h);
            }
            else {
                g2.setColor(Color.WHITE);
                g2.drawRect(0, 0, w, h);

                if (!this.val1.equals("")|| !this.val2.equals("")) {
                    g2.drawLine(0, 0, w, h);
                    drawStringCentered(g2, this.val1, w / 5, h * 4 / 5, w/4); //Val1 vertical
                    drawStringCentered(g2, this.val2, w * 4 / 5, h/5, w/4); //Val2 horitzontal
                }
            }
            if (this.celaSeleccionada){
                g2.setColor(new Color(140,197,248));
                g2.setStroke(new BasicStroke(7));
                g2.drawRect(0,0,w,h);
            }
        }
        public void setValueCelaBlanca(String value){
            this.val1 = value;
        }
        void drawStringCentered(Graphics g, String s, int x, int y, int size) {
            Font font = new Font("Arial", Font.PLAIN, size);
            g.setFont(font);
            Rectangle2D bounds = g.getFontMetrics().getStringBounds(s, g);
            g.drawString(s, (int) (x - bounds.getCenterX()), (int) (y - bounds.getCenterY()));
        }

        public void setEsBlanc(Boolean esBlanc) {
            this.esBlanc = esBlanc;
        }

        public void setVal1(String val1) {
            this.val1 = val1;
        }

        public void setVal2(String val2) {
            this.val2 = val2;
        }

        public String getVal1() {
            return val1;
        }

        public String getVal2() {
            return val2;
        }

        public void setCelaSeleccionada(Boolean celaSeleccionada) {
            this.celaSeleccionada = celaSeleccionada;
        }

        public boolean getEsBlanc(){
            return this.esBlanc;
        }
    }
    static class CustomScrollBarUI extends BasicScrollBarUI {

        private Color thumbColor = new Color(246, 146, 36);
        private Color trackColor = new Color(23,33,53);
        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            // your code
            Graphics2D g2 = (Graphics2D)g.create();
            RenderingHints qualityHints =
                    new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHints(qualityHints);

            g.setColor(trackColor);
            g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D)g.create();
            RenderingHints qualityHints =
                    new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHints(qualityHints);

            g.setColor(thumbColor);
            g.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
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

        public CustomListUI() {

        }

        @Override
        protected void paintCell(Graphics g, int row, Rectangle rowBounds, ListCellRenderer<Object> cellRenderer, ListModel<Object> dataModel, ListSelectionModel selModel, int leadIndex) {
            //super.paintCell(g, row, rowBounds, cellRenderer, dataModel, selModel, leadIndex);
            if (leadIndex == row) g.setColor(selectedCellColor);
            else g.setColor(cellColor);
            g.fillRect(rowBounds.x,rowBounds.y,rowBounds.width, rowBounds.height);
            g.setColor(textColor);
            drawStringCentered(g, dataModel.getElementAt(row).toString(), rowBounds.x+rowBounds.width/2, rowBounds.y+rowBounds.height/2, rowBounds.height/2);
        }
        void drawStringCentered(Graphics g, String s, int x, int y, int size) {
            Font font = new Font("Arial", Font.PLAIN, size);
            g.setFont(font);
            Rectangle2D bounds = g.getFontMetrics().getStringBounds(s, g);
            g.drawString(s, (int) (x - bounds.getCenterX()), (int) (y - bounds.getCenterY()));
        }

    }
}

