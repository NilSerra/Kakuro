package Vistes;

import Controladors.ControladorPresentacio;

import javax.swing.*;
import java.awt.*;

public class VistaComJugar {
    private ControladorPresentacio controladorPresentacio;
    private JPanel panel;
    private int width, height;
    private JLabel labelComJugar;
    private JTextArea text;

    public VistaComJugar(ControladorPresentacio controladorPresentacio) {
        this.controladorPresentacio = controladorPresentacio;
        inicialitzarComponents();
    }
    private void inicialitzarComponents(){
        panel = new JPanel(null);
        panel.setBackground(new Color(39,54,90));
        width = 930;
        height = 685;

        crearLabels();

        panel.setMaximumSize(new Dimension(width, height));
        panel.setMinimumSize(new Dimension(width, height));
        panel.setPreferredSize(new Dimension(width, height));
        panel.add(labelComJugar);
        panel.add(text);
    }
    private void crearLabels(){
        labelComJugar = new JLabel("Com jugar");
        int widthLabel = 400;
        int heightLabel = 60;
        Font font = new Font("Arial", Font.BOLD, 40);
        labelComJugar.setFont(font);
        labelComJugar.setForeground(new Color(230,230,230));
        labelComJugar.setBackground(new Color(0,0,0, 0));
        labelComJugar.setHorizontalAlignment(SwingConstants.CENTER);
        labelComJugar.setMaximumSize(new Dimension(widthLabel, heightLabel));
        labelComJugar.setMinimumSize(new Dimension(widthLabel, heightLabel));
        labelComJugar.setPreferredSize(new Dimension(widthLabel, heightLabel));
        labelComJugar.setBounds(width/2-widthLabel/2, heightLabel/2, widthLabel, heightLabel);
        widthLabel = 550;
        heightLabel = 450;
        text = new JTextArea();
        font = new Font("Arial", Font.BOLD, 15);
        text.setText("El Kakuro \u00e9s un tipus de mots encreuats derivat del Sudoku, encara que menys conegut.\n\n" +
                "El Kakuro consisteix en una graella amb cel\u00b7les blanques i negres. L'objectiu \u00e9s omplir cada cel\u00b7la blanca amb un d\u00edgit de l'1 al 9.\n\n" +
                "Les seves regles s\u00f3n similars a les del Sudoku i tamb\u00e9 a les dels mots encreuats.\n\n" +
                "Similars a les del Sudoku, ja que no es pot repetir el mateix nombre en cada una de les sumes i nom\u00e9s es poden utilitzar els nombres de l'1 al 9.\n\n" +
                "Tamb\u00e9 s\u00f3n semblants als mots encreuats perqu\u00e8 les fileres s'han d'omplir cap a la dreta i les columnes cap avall.\n\n" +
                "Les pistes s\u00f3n els nombres que apareixen a les cantonades de les caselles; el nombre de l'esquerre de la casella \u00e9s la suma d'aquella filera i el que apareix en la part superior \u00e9s la suma d'aquella columna.\n\n" +
                "En la resoluci\u00f3 dels Kakuros s'ha d'utilitzar m\u00e9s la l\u00f2gica de les xifres que el c\u00e0lcul aritm\u00e8tic, ja que les sumes s\u00f3n de poques xifres i no seran majors a 45.\n\n" +
                "En cas de voler veure exemples d'\u00fas del programa, podeu consultar el manual.");
        text.setFont(font);
        text.setForeground(new Color(230,230,230));
        text.setBackground(new Color(0,0,0, 0));
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setOpaque(false);
        text.setEditable(false);
        text.setMaximumSize(new Dimension(widthLabel, heightLabel));
        text.setMinimumSize(new Dimension(widthLabel, heightLabel));
        text.setPreferredSize(new Dimension(widthLabel, heightLabel));
        text.setBounds(width/2-widthLabel/2, height/2-heightLabel/2, widthLabel, heightLabel);
    }
    public JPanel getContent(){
        return panel;
    }
}
