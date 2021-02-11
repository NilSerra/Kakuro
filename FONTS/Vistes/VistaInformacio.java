package Vistes;

import Controladors.ControladorPresentacio;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class VistaInformacio {
    private ControladorPresentacio controladorPresentacio;
    private JPanel panel;
    private int width, height;
    private JLabel labelInformacio;
    private JTextPane text;
    public VistaInformacio(ControladorPresentacio controladorPresentacio) {
        this.controladorPresentacio = controladorPresentacio;
        inicialitzarComponents();
    }
    private void inicialitzarComponents(){
        panel = new JPanel(null);
        panel.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        panel.setBackground(new Color(39,54,90));
        width = 930;
        height = 685;
        crearLabels();

        panel.setMaximumSize(new Dimension(width, height));
        panel.setMinimumSize(new Dimension(width, height));
        panel.setPreferredSize(new Dimension(width, height));
        panel.add(labelInformacio);
        panel.add(text);
    }
    private void crearLabels(){
        labelInformacio = new JLabel("Informaci\u00f3");
        int widthLabel = 400;
        int heightLabel = 60;
        Font font = new Font("Arial", Font.BOLD, 40);
        labelInformacio.setFont(font);
        labelInformacio.setForeground(new Color(230,230,230));
        labelInformacio.setBackground(new Color(39,54,90));
        labelInformacio.setHorizontalAlignment(SwingConstants.CENTER);
        labelInformacio.setMaximumSize(new Dimension(widthLabel, heightLabel));
        labelInformacio.setMinimumSize(new Dimension(widthLabel, heightLabel));
        labelInformacio.setPreferredSize(new Dimension(widthLabel, heightLabel));
        labelInformacio.setBounds(width/2-widthLabel/2, heightLabel/2, widthLabel, heightLabel);
        widthLabel = 550;
        heightLabel = 450;
        text = new JTextPane();
        font = new Font("Arial", Font.BOLD, 20);
        text.setText("Programa creat per:\n\n"+
                "Arnau Font\n"+
                "Nil Serra\n"+
                "Llu\u00eds Mendoza");
        text.setFont(font);
        text.setForeground(new Color(230,230,230));
        text.setBackground(new Color(0,0,0, 0));

        text.setOpaque(false);
        text.setEditable(false);
        StyledDocument doc = text.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        text.setMaximumSize(new Dimension(widthLabel, heightLabel));
        text.setMinimumSize(new Dimension(widthLabel, heightLabel));
        text.setPreferredSize(new Dimension(widthLabel, heightLabel));
        text.setBounds(width/2-widthLabel/2, height/2-heightLabel/2, widthLabel, heightLabel);
    }
    public JPanel getContent(){
        return panel;
    }
}
