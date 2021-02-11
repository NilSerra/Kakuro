package Vistes;

import Controladors.ControladorPresentacio;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class VistaEditar {
    private ControladorPresentacio controladorPresentacio;
    private JPanel panel;
    private JPanel sizePanel;
    private String kakuroEditat;
    private JLabel labelCol;
    private JLabel labelFil;
    private celaComponent[][] celes;
    private MySpinner numColSpinner;
    private MySpinner numFilSpinner;
    private MyRadioButton esBlanca;
    private MySpinner spinnerNegresFil;
    private MySpinner spinnerNegresCol;
    private MySpinner spinnerBlanques;
    private MyTextField textField;
    private MyButton verificar;
    private MyButton guardar;
    private int width,height;


    private int celaX, celaY;
    JPanel boardKakuro;
    public VistaEditar(ControladorPresentacio controladorPresentacio) {
        this.controladorPresentacio = controladorPresentacio;
        inicialitzarComponents();
    }

    private void crearLabel(){
        int width = 250;
        int height = 60;
        Font font = new Font("Arial", Font.PLAIN, 25);
        labelCol = new JLabel("Nombre de columnes");
        labelFil = new JLabel("Nombre de fileres");
        labelCol.setFont(font);
        labelCol.setForeground(new Color(230,230,230));
        labelCol.setBackground(new Color(39,54,90));
        labelCol.setHorizontalAlignment(SwingConstants.LEFT);
        labelCol.setMaximumSize(new Dimension(width, height));
        labelCol.setMinimumSize(new Dimension(width, height));
        labelCol.setPreferredSize(new Dimension(width, height));

        font = new Font("Arial", Font.PLAIN, 25);
        labelFil.setFont(font);
        labelFil.setForeground(new Color(230,230,230));
        labelFil.setBackground(new Color(39,54,90));
        labelFil.setHorizontalAlignment(SwingConstants.LEFT);

        width = 200;
        labelFil.setMaximumSize(new Dimension(width, height));
        labelFil.setMinimumSize(new Dimension(width, height));
        labelFil.setPreferredSize(new Dimension(width, height));

    }
    private void crearBotons(){
        int widthButton = 150;
        int heightButton = 70;
        verificar = new MyButton(widthButton, heightButton, "Verificar");
        verificar.setBounds(width-widthButton-30, height/2-heightButton/2, widthButton, heightButton);
        guardar = new MyButton(widthButton, heightButton, "Guardar");
        guardar.setBounds(width-widthButton-30, height/2+4*heightButton/3, widthButton, heightButton);
        guardar.setVisible(false);
    }
    private void crearSpinner(){
        numColSpinner = new MySpinner(130, 60, 5,13);
        numFilSpinner = new MySpinner(130, 60, 5,13);
        int widthSpinner = 100;
        int heightSpinner = 50;
        spinnerNegresFil = new MySpinner(widthSpinner,heightSpinner,0,45);
        spinnerNegresCol = new MySpinner(widthSpinner,heightSpinner,0,45);
        spinnerBlanques = new MySpinner(widthSpinner,heightSpinner,0,9);

        spinnerNegresFil.setBounds(width-widthSpinner-30, height/5, widthSpinner, heightSpinner);
        spinnerBlanques.setBounds(width-widthSpinner-30, height/5, widthSpinner, heightSpinner);
        spinnerNegresCol.setBounds(width-widthSpinner-30, height/5+heightSpinner, widthSpinner, heightSpinner);
        spinnerBlanques.setValue(0);
        spinnerNegresFil.setValue(0);
        spinnerNegresCol.setValue(0);
    }
    private void crearSize(){
        sizePanel = new JPanel();
        int widthSize = 820;
        int heightSize = 70;
        sizePanel.setMaximumSize(new Dimension(widthSize, heightSize));
        sizePanel.setMinimumSize(new Dimension(widthSize, heightSize));
        sizePanel.setPreferredSize(new Dimension(widthSize, heightSize));
        sizePanel.setBackground(new Color(39,54,90));
        sizePanel.setBounds(width/2-widthSize/2, 0, widthSize, heightSize);
    }
    private void crearTextField(){
        textField = new MyTextField();
        int widthSize = 140;
        int heightSize = 30;
        textField.setMaximumSize(new Dimension(widthSize, heightSize));
        textField.setMinimumSize(new Dimension(widthSize, heightSize));
        textField.setPreferredSize(new Dimension(widthSize, heightSize));
        textField.setBounds(width-widthSize-30, height/2+heightSize*2, widthSize, heightSize);
        textField.setVisible(false);
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

        int widthRadio = 135;
        int heightRadio = 30;
        esBlanca = new MyRadioButton(widthRadio, heightRadio, "Casella blanca");

        esBlanca.setBounds(width-widthRadio-30, height/4- heightRadio*2, widthRadio, heightRadio);
        crearLabel();
        crearSpinner();
        crearKakuro();
        crearBotons();
        crearSize();
        crearTextField();
        asignar_listenersComponentes();

        esBlanca.setVisible(false);
        spinnerBlanques.setVisible(false);
        spinnerNegresCol.setVisible(false);
        spinnerNegresFil.setVisible(false);

        sizePanel.add(labelFil);
        sizePanel.add(numFilSpinner);
        sizePanel.add(labelCol);
        sizePanel.add(numColSpinner);
        panel.add(sizePanel);
        panel.add(esBlanca);
        panel.add(spinnerNegresFil);
        panel.add(spinnerNegresCol);
        panel.add(spinnerBlanques);
        panel.add(verificar);
        panel.add(guardar);
        panel.add(textField);
        panel.add(boardKakuro);
    }

    private void crearKakuro(){
        kakuroEditat = "";
        celaX = celaY = 0;
        int sizeBoard = 500;
        int sizeBoardH, sizeBoardV;
        int n = numFilSpinner.getValue();
        int m = numColSpinner.getValue();
        kakuroEditat = kakuroEditat.concat(n+","+m+"\n");
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

        boardKakuro.setLayout(new GridLayout(n,m));
        celes = new celaComponent[n][m];
        for(int i = 0; i<n; ++i){
            for(int j = 0; j<m; ++j){
                if (i == 0 | j == 0)celes[i][j] = new celaComponent(celaSizeH,celaSizeV,false, "", "");
                else celes[i][j] = new celaComponent(celaSizeH, celaSizeV, true, "", "");
                celes[i][j].setMaximumSize(new Dimension(celaSizeH, celaSizeV));
                celes[i][j].setMinimumSize(new Dimension(celaSizeH, celaSizeV));
                celes[i][j].setPreferredSize(new Dimension(celaSizeH, celaSizeV));
                int finalI = i;
                int finalJ = j;
                celes[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for(int i = 0; i<n; ++i){
                            for(int j = 0; j<m;++j) celes[i][j].setCelaSeleccionada(false);
                        }
                        celaX = finalJ;
                        celaY = finalI;
                        modificarCela();
                        if (celes[finalI][finalJ].getEsBlanc()){
                            spinnerBlanques.setValue((celes[finalI][finalJ].getVal1().equals("") ?0:Integer.parseInt(celes[finalI][finalJ].getVal1())));
                        }
                        else{
                            spinnerNegresCol.setValue((celes[finalI][finalJ].getVal1().equals("") ?0:Integer.parseInt(celes[finalI][finalJ].getVal1())));
                            spinnerNegresFil.setValue((celes[finalI][finalJ].getVal2().equals("") ?0:Integer.parseInt(celes[finalI][finalJ].getVal2())));
                        }
                        celes[finalI][finalJ].setCelaSeleccionada(true);
                        spinnerNegresFil.repaint();
                        spinnerNegresCol.repaint();
                        spinnerBlanques.repaint();
                        boardKakuro.repaint();
                    }
                });
                boardKakuro.add(celes[i][j]);
            }
        }
        for(int i = 0; i<n; ++i){
            String linia = "";
            for(int j = 0; j<m; ++j){
                if (celes[i][j].getEsBlanc()) linia += "?,";
                else linia += "*,";
            }
            kakuroEditat = kakuroEditat.concat(linia.substring(0,linia.length()-1));
            if(i<n-1) kakuroEditat = kakuroEditat.concat("\n");
        }
        boardKakuro.setMaximumSize(new Dimension(sizeBoardH,sizeBoardV));
        boardKakuro.setMinimumSize(new Dimension(sizeBoardH,sizeBoardV));
        boardKakuro.setPreferredSize(new Dimension(sizeBoardH,sizeBoardV));
        controladorPresentacio.setKakuroEditat(kakuroEditat);
        boardKakuro.setBounds(width/2-sizeBoardH/2, height/2-sizeBoardV/2, sizeBoardH, sizeBoardV);
    }

    private void crearKakuro(String kakuroText){
        boardKakuro.removeAll();
        String[] kakuro = kakuroText.split("\n");
        kakuro[0] = kakuro[0].replace("\n", "");
        String[] size = kakuro[0].split(",");
        int n = Integer.parseInt(size[0].replaceAll("[^0-9.]", ""));
        int m = Integer.parseInt(size[1].replaceAll("[^0-9.]", ""));
        numFilSpinner.setValue(n);
        numColSpinner.setValue(m);
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
        int celaSizeH = sizeBoardH/m;
        int celaSizeV = sizeBoardV/n;

        boardKakuro.setLayout(new GridLayout(n, m));
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
                        valorCelaNegraC += valors[0].replace("C", "").replaceAll("[^\\d]", ""); ;
                    }
                    else if(valors.length==2){
                        valorCelaNegraC += valors[0].replace("C", "").replaceAll("[^\\d]", ""); ;
                        valorCelaNegraF += valors[1].replaceAll("[^\\d]", ""); ;
                    }
                }
                else if(value.charAt(0) == 'F'){
                    valorCelaNegraF += value.replace("F", "").replaceAll("[^\\d]", ""); ;
                }
                else if (value.contains("1") ||value.contains("2") ||value.contains("3") ||value.contains("4")||value.contains("5")||value.contains("6")||value.contains("7")||value.contains("8")||value.contains("9")){
                    esBlanc = true;
                    valorCelaBlanca += value.replaceAll("[^\\d]", ""); ;
                }
                if (esBlanc)celes[i][j] = new celaComponent(celaSizeH,celaSizeV,true, valorCelaBlanca, "");
                else celes[i][j] = new celaComponent(celaSizeH, celaSizeV, false, valorCelaNegraC, valorCelaNegraF);
                celes[i][j].setMaximumSize(new Dimension(celaSizeH, celaSizeV));
                celes[i][j].setMinimumSize(new Dimension(celaSizeH, celaSizeV));
                celes[i][j].setPreferredSize(new Dimension(celaSizeH, celaSizeV));
                int finalI = i;
                int finalJ = j;
                celes[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for(int i = 0; i<n; ++i){
                            for(int j = 0; j<m;++j) celes[i][j].setCelaSeleccionada(false);
                        }
                        celaX = finalJ;
                        celaY = finalI;
                        modificarCela();
                        if (celes[finalI][finalJ].getEsBlanc()){
                            spinnerBlanques.setValue((celes[finalI][finalJ].getVal1().equals("") ?0:Integer.parseInt(celes[finalI][finalJ].getVal1().replaceAll("[^\\d]", "") )));
                        }
                        else{
                            spinnerNegresCol.setValue((celes[finalI][finalJ].getVal1().equals("") ?0:Integer.parseInt(celes[finalI][finalJ].getVal1().replaceAll("[^\\d]", "") )));
                            spinnerNegresFil.setValue((celes[finalI][finalJ].getVal2().equals("") ?0:Integer.parseInt(celes[finalI][finalJ].getVal2().replaceAll("[^\\d]", ""))));
                        }

                        spinnerNegresFil.repaint();
                        spinnerNegresCol.repaint();
                        spinnerBlanques.repaint();
                        celes[finalI][finalJ].setCelaSeleccionada(true);
                        boardKakuro.repaint();
                    }
                });
                boardKakuro.add(celes[i][j]);
            }
        }
        boardKakuro.setMaximumSize(new Dimension(sizeBoardH,sizeBoardV));
        boardKakuro.setMinimumSize(new Dimension(sizeBoardH,sizeBoardV));
        boardKakuro.setPreferredSize(new Dimension(sizeBoardH,sizeBoardV));
        boardKakuro.setBounds(width/2-sizeBoardH/2, height/2-sizeBoardV/2, sizeBoardH, sizeBoardV);
    }
    private void modificarCela(){
        textField.setVisible(false);
        guardar.setVisible(false);
        esBlanca.setVisible(true);

        controladorPresentacio.editarCelaKakuro(celaY, celaX, celes[celaY][celaX].getEsBlanc(), celes[celaY][celaX].getVal1(), celes[celaY][celaX].getVal2());
        spinnerBlanques.setVisible(celes[celaY][celaX].getEsBlanc());
        spinnerNegresFil.setVisible(!celes[celaY][celaX].getEsBlanc());
        spinnerNegresCol.setVisible(!celes[celaY][celaX].getEsBlanc());
        esBlanca.getRadioButton().setSelected(celes[celaY][celaX].getEsBlanc());
        boardKakuro.revalidate();
        boardKakuro.repaint();
    }
    private void asignar_listenersComponentes() {
        numFilSpinner.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                boardKakuro.removeAll();
                crearKakuro();
                boardKakuro.revalidate();
                boardKakuro.repaint();
            }
        });
        numColSpinner.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                boardKakuro.removeAll();
                crearKakuro();
                boardKakuro.revalidate();
                boardKakuro.repaint();
            }
        });
        esBlanca.getRadioButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                celes[celaY][celaX].setEsBlanc(esBlanca.getRadioButton().isSelected());
                celes[celaY][celaX].setVal1("");
                celes[celaY][celaX].setVal2("");
                if (celes[celaY][celaX].getEsBlanc()){
                    spinnerBlanques.setValue((celes[celaY][celaX].getVal1().equals("") ?0:Integer.parseInt(celes[celaY][celaX].getVal1())));
                }
                else{
                    spinnerNegresCol.setValue((celes[celaY][celaX].getVal1().equals("") ?0:Integer.parseInt(celes[celaY][celaX].getVal1())));
                    spinnerNegresFil.setValue((celes[celaY][celaX].getVal2().equals("") ?0:Integer.parseInt(celes[celaY][celaX].getVal2())));
                }
                modificarCela();
            }
        });
        spinnerNegresFil.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                celes[celaY][celaX].setVal2((spinnerNegresFil.getValue()==0?"":String.valueOf(spinnerNegresFil.getValue())));
                modificarCela();
            }
        });
        spinnerNegresCol.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                celes[celaY][celaX].setVal1((spinnerNegresCol.getValue()==0?"":String.valueOf(spinnerNegresCol.getValue())));
                modificarCela();
            }
        });
        spinnerBlanques.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                celes[celaY][celaX].setVal1((spinnerBlanques.getValue()==0?"":String.valueOf(spinnerBlanques.getValue())));
                modificarCela();
            }
        });
        verificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (controladorPresentacio.verificarKakuroEditat()){
                    textField.setVisible(true);
                    guardar.setVisible(true);
                }
            }
        });
        guardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                String fileName = textField.getText().replace("\"#@,;:<>*^|?\\/", "");
                if (fileName.length() > 0){
                    controladorPresentacio.guardarKakuroARepositori(textField.getText());
                    textField.setVisible(false);
                    guardar.setVisible(false);
                }
            }
        });
    }
    public JPanel getContent(){
        return panel;
    }

    public void setKakuro(String kakuro) {
        textField.setVisible(false);
        guardar.setVisible(false);
        controladorPresentacio.setKakuroEditat(kakuro);
        crearKakuro(kakuro);
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
                drawStringCentered(g2, val1, w/2, h/2, 30);
                g2.drawRect(0, 0, w, h);
            }
            else {
                g2.setColor(Color.WHITE);
                g2.drawRect(0, 0, w, h);

                if (!this.val1.equals("")|| !this.val2.equals("")) {
                    g2.drawLine(0, 0, w, h);
                    drawStringCentered(g2, this.val1, w / 6, h * 5 / 6, 17); //Val1 vertical
                    drawStringCentered(g2, this.val2, w * 5 / 6, h/6, 17); //Val2 horitzontal
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
}
