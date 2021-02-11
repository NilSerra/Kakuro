package Vistes;

import Controladors.ControladorPresentacio;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicRadioButtonUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class VistaKakuro {
    private ControladorPresentacio controladorPresentacio;
    private JPanel panel;
    private MyButton[] buttons;
    private JLabel labelTemps;
    private celaComponent[][] celes;
    private Integer posY, posX;
    private String[] kakuro;
    private JPanel boardKakuro;
    private MyRadioButton ajudaKakuro;
    private JLabel labelPistes;
    private int celaSizeH, celaSizeV;
    private int n, m;
    private int width, height;

    public VistaKakuro(ControladorPresentacio controladorPresentacio) {
        this.controladorPresentacio = controladorPresentacio;
    }
    public void setKakuro(String kakuro){
        this.kakuro = kakuro.split("\n");
    }

    public void setTemps(long temps){
        if (labelTemps == null) return;
        labelTemps.setText(Long.toString(temps/60)+":"+(temps%60 < 10?"0":"")+Long.toString(temps%60));
        panel.repaint();
    }

    private void inicialitzarKakuro(){
        kakuro[0] = kakuro[0].replace("\n", "");
        String[] size = kakuro[0].split(",");
        n = Integer.parseInt(size[0]);
        m = Integer.parseInt(size[1]);
        panel = new JPanel(null);
        width = 930;
        height = 685;
        panel.setMaximumSize(new Dimension(width, height));
        panel.setMinimumSize(new Dimension(width, height));
        panel.setPreferredSize(new Dimension(width, height));
        panel.setBackground(new Color(39,54,90));
    }
    private void crearRadioButton(){
        int widthRadio = 125;
        int heightRadio = 30;
        ajudaKakuro = new MyRadioButton(125, heightRadio, "Ajuda");
        ajudaKakuro.setBounds(width-widthRadio-30,height/2-6*heightRadio,widthRadio, heightRadio);

        ajudaKakuro.getRadioButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajudaKakuro.getRadioButton().setSelected(true);
                controladorPresentacio.setAjudaPartida(true);
                if (posY != 0 && posX != 0)actualitzarBotonsPossibles();
            }
        });
        panel.add(ajudaKakuro);
    }
    private void creaTaulellKakuro(){
        int sizeBoard = 500;
        int sizeBoardH, sizeBoardV;
        if (m > n) {
            sizeBoardH = sizeBoard;
            sizeBoardV = (int) (sizeBoardH * ((float) n / m));
        }
        else{
            sizeBoardV = sizeBoard;
            sizeBoardH = (int) (sizeBoardV * ((float) m / n));
        }
        celaSizeH = sizeBoardH/m;
        celaSizeV = sizeBoardV/n;
        posX = posY = 0;
        boardKakuro = new JPanel(new GridLayout(n, m));
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
                int finalI = i;
                int finalJ = j;
                celes[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        posY = finalI;
                        posX = finalJ;
                        actualitzarCelaSeleccionada();
                        actualitzarBotonsPossibles();
                        boardKakuro.repaint();
                    }
                });
                celes[i][j].setMaximumSize(new Dimension(celaSizeH, celaSizeV));
                celes[i][j].setMinimumSize(new Dimension(celaSizeH, celaSizeV));
                celes[i][j].setPreferredSize(new Dimension(celaSizeH, celaSizeV));
                boardKakuro.add(celes[i][j]);
            }
        }
        boardKakuro.setMaximumSize(new Dimension(sizeBoardH,sizeBoardV));
        boardKakuro.setMinimumSize(new Dimension(sizeBoardH,sizeBoardV));
        boardKakuro.setPreferredSize(new Dimension(sizeBoardH,sizeBoardV));

        boardKakuro.setBounds(width/2-sizeBoardH/2, height/2-sizeBoardV/2, sizeBoardH, sizeBoardV);
        panel.add(boardKakuro);

        int buttonSize = 70;
        buttons = new MyButton[10];
        for(int i = 0; i<9; ++i){
            MyButton button = new MyButton(buttonSize, buttonSize, String.valueOf(i+1));
            buttons[i] = button;
            buttons[i].setButtonEnabled(false);
            buttons[i].setBorderH(5);
            buttons[i].setBorderV(5);
            int finalI = i;
            buttons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    if(!buttons[finalI].isButtonEnabled()) return;
                    controladorPresentacio.setKakuroNum(posY, posX, finalI+1);
                    actualitzarKakuro(controladorPresentacio.getKakuroActual());
                    actualitzarBotonsPossibles();
                }
            });

            buttons[i].setBounds(width/2-5*buttonSize+i*buttonSize, height-buttonSize-15, buttonSize, buttonSize);
            panel.add(buttons[i]);
        }
        MyButton button = new MyButton(buttonSize, buttonSize, "");
        buttons[9] = button;
        buttons[9].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if(!buttons[9].isButtonEnabled()) return;
                controladorPresentacio.setKakuroNum(posY, posX, 0);
                celes[posY][posX].setValueCelaBlanca("");
                celes[posY][posX].repaint();
                actualitzarBotonsPossibles();
            }
        });
        buttons[9].setBounds(width/2+4*buttonSize, height-buttonSize-15, buttonSize, buttonSize);
        buttons[9].setImage("src\\Images\\borrar_cela_disabled.png");
        buttons[9].setButtonEnabled(false);
        buttons[9].setBorderH(5);
        buttons[9].setBorderV(5);
        panel.add(buttons[9]);
    }

    private void actualitzarKakuro(String kakuroA){
        String[] aux = kakuroA.split("\n");
        for(int i = 0; i< aux.length-1; ++i){
            aux[i+1] = aux[i+1].replace("\n", "");
            String[] line = aux[i+1].split(",");
            String[] line2 = kakuro[i+1].split(",");
            for(int j = 0; j< line.length; ++j){
                String value = line[j];
                boolean esBlanc = false;
                String valorCelaBlanca = "";
                String valorCelaNegraC = "";
                String valorCelaNegraF = "";
                if (!line[j].equals(line2[j])) {
                    if (value.equals("?")) {
                        esBlanc = true;
                    } else if (value.charAt(0) == 'C') {
                        String[] valors = value.split("F");
                        if (valors.length == 1) {
                            valorCelaNegraC += valors[0].replace("C", "");
                        } else if (valors.length == 2) {
                            valorCelaNegraC += valors[0].replace("C", "");
                            valorCelaNegraF += valors[1];
                        }
                    } else if (value.charAt(0) == 'F') {
                        valorCelaNegraF += value.replace("F", "");
                    } else if (value.equals("1") || value.equals("2") || value.equals("3") || value.equals("4") || value.equals("5") || value.equals("6") || value.equals("7") || value.equals("8") || value.equals("9")) {
                        esBlanc = true;
                        valorCelaBlanca += value;
                    }
                    celes[i][j].setEsBlanc(esBlanc);
                    if (esBlanc){
                        celes[i][j].setVal1(valorCelaBlanca);
                    }
                    else{
                        celes[i][j].setVal1(valorCelaNegraC);
                        celes[i][j].setVal2(valorCelaNegraF);
                    }

                    celes[i][j].repaint();
                }
            }
        }
    }

    private void actualitzarCelaSeleccionada(){
        for(int i = 1; i<n; ++i){
            for(int j = 1; j<m; ++j){
                celes[i][j].setSelectedButton((i == posY && j == posX));
            }
        }
    }
    public void setAjuda(boolean ajuda){
        ajudaKakuro.getRadioButton().setSelected(ajuda);
    }
    private void actualitzarBotonsPossibles(){
        boolean[] botonsPossibles;
        if (ajudaKakuro.getRadioButton().isSelected())botonsPossibles = controladorPresentacio.getKakuroValorsPossiblesPista(posY, posX);
        else botonsPossibles = controladorPresentacio.getKakuroValorsPossibles(posY, posX);
        for(int i = 0; i<9; ++i){
            buttons[i].setButtonEnabled(botonsPossibles[i]);
        }

        if (celes[posY][posX].getVal1().equals("")){
            buttons[9].setImage("src\\Images\\borrar_cela_disabled.png");
            buttons[9].setButtonEnabled(false);
        }
        else {
            buttons[9].setImage("src\\Images\\borrar_cela.png");
            buttons[9].setButtonEnabled(true);
        }
        panel.repaint();
    }

    private void crearLabel(){
        labelTemps = new JLabel("0:00");
        Font font = new Font("Arial Bold", Font.PLAIN, 20);
        labelTemps.setFont(font);
        labelTemps.setBounds(width/2,50,100, 20);
        labelTemps.setForeground(new Color(250,250,250));
        labelPistes = new JLabel("Pistes: "+controladorPresentacio.getNumPistes());
        font = new Font("Arial Bold", Font.PLAIN, 15);
        labelPistes.setFont(font);
        int widthLabel = 125;
        int heightLabel = 30;
        labelPistes.setBounds(width-widthLabel-30, height/2-9*heightLabel/2, widthLabel, heightLabel);
        labelPistes.setMinimumSize(new Dimension(widthLabel, heightLabel));
        labelPistes.setMaximumSize(new Dimension(widthLabel, heightLabel));
        labelPistes.setPreferredSize(new Dimension(widthLabel, heightLabel));
        labelPistes.setForeground(new Color(250,250,250));
        panel.add(labelTemps);
        panel.add(labelPistes);
    }

    private void crearButtons(){
        int widthButton = 150;
        int heightButton = 70;

        MyButton verificar = new MyButton(widthButton, heightButton, "Verificar");
        verificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                verificarKakuro(controladorPresentacio.getKakuroActual(), controladorPresentacio.getKakuroResolt());
            }
        });
        MyButton pista = new MyButton(widthButton, heightButton, "Pista");
        pista.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                controladorPresentacio.getPistaKakuro();
                labelPistes.setText("Pistes: "+controladorPresentacio.getNumPistes());
                actualitzarKakuro(controladorPresentacio.getKakuroActual());
            }
        });
        MyButton guardar = new MyButton(widthButton, heightButton, "Guardar");
        guardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                controladorPresentacio.guardarKakuro();
            }
        });
        pista.setBounds(width-widthButton-30, height/2-3*heightButton/2, widthButton, heightButton);
        verificar.setBounds(width-widthButton-30, height/2-heightButton/2, widthButton, heightButton);
        guardar.setBounds(width-widthButton-30, height/2+heightButton/2, widthButton, heightButton);
        panel.add(verificar);
        panel.add(pista);
        panel.add(guardar);
    }

    public JPanel getContent(){
        inicialitzarKakuro();
        crearLabel();
        crearButtons();
        creaTaulellKakuro();
        crearRadioButton();
        return panel;
    }
    private void verificarKakuro(String kakuroActual, String kakuroSolucio){
        int contErrors = 0;
        String[] aux = kakuroActual.split("\n");
        String[] aux2 = kakuroSolucio.split("\n");
        for(int i = 0; i<aux.length-1; ++i){
            String[] line = aux[i+1].split(",");
            String[] line2 = aux2[i+1].split(",");
            for(int j = 0; j<line.length; ++j){
                String value = line[j];
                String value2 = line2[j];
                if (!value.equals(value2)) ++contErrors;
                celes[i][j].setWrong(!value.equals(value2) && !value.equals("?"));
                celes[i][j].repaint();
            }
        }
        if (contErrors == 0) controladorPresentacio.kakuroAcabat();
    }
    class celaComponent extends JButton{
        private Boolean esBlanc;
        private String val1, val2;
        private Boolean wrong;
        private Boolean selectedButton;
        int w, h;

        public celaComponent(int w, int h, Boolean esBlanc, String val1, String val2) {
            this.esBlanc = esBlanc;
            this.val1 = val1;
            this.val2 = val2;
            this.w = w;
            this.h = h;
            this.setEnabled(esBlanc);
            this.wrong = false;
            this.selectedButton = false;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2 = (Graphics2D) g;
            if (this.esBlanc){
                if (!this.wrong)g2.setColor(Color.WHITE);
                else g2.setColor(new Color(255,100,100));

            }
            else g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, w, h);

            if (this.esBlanc){
                if (!this.wrong)g2.setColor(Color.BLACK);
                else g2.setColor(Color.WHITE);
                drawStringCentered(g2, val1, w/2, h/2, w/2);
                if (this.selectedButton){
                    g2.setColor(new Color(140,197,248));
                    g2.setStroke(new BasicStroke(7));
                    g2.drawRect(0,0,w,h);
                }
                g2.drawRect(0, 0, w, h);
            }
            else {
                g2.setColor(Color.WHITE);
                g2.drawRect(0, 0, w, h);

                if (!this.val1.equals("")|| !this.val2.equals("")) {
                    g2.drawLine(0, 0, w, h);
                    drawStringCentered(g2, this.val1, w / 5, h * 4 / 5, w/4);
                    drawStringCentered(g2, this.val2, w * 4 / 5, h/5, w/4);
                }
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

        public void setWrong(Boolean wrong) {
            this.wrong = wrong;
        }

        public String getVal1() {
            return val1;
        }

        public void setSelectedButton(Boolean selected) {
            this.selectedButton = selected;
        }
    }
}


