package Vistes;

import Controladors.ControladorPresentacio;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicListUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class VistaRanking {
    private ControladorPresentacio controladorPresentacio;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JList list;
    private JLabel labelRanking;
    private int width, height;
    private int heightCell;

    public VistaRanking(ControladorPresentacio controladorPresentacio) {
        this.controladorPresentacio = controladorPresentacio;
        inicialitzarComponents();
    }

    private void inicialitzarComponents(){
        panel = new JPanel(null);
        width = 930;
        height = 685;
        panel.setMaximumSize(new Dimension(width, height));
        panel.setMinimumSize(new Dimension(width, height));
        panel.setPreferredSize(new Dimension(width, height));
        panel.setBackground(new Color(39,54,90));

        crearRanking();
        crearLabel();
        panel.add(labelRanking);
        panel.add(scrollPane);
    }
    private void crearLabel(){
        labelRanking = new JLabel("Ranking");
        int widthLabel = 400;
        int heightLabel = 60;
        Font font = new Font("Arial", Font.BOLD, 40);
        labelRanking.setFont(font);
        labelRanking.setForeground(new Color(230,230,230));
        labelRanking.setBackground(new Color(39,54,90));
        labelRanking.setHorizontalAlignment(SwingConstants.CENTER);
        labelRanking.setMaximumSize(new Dimension(widthLabel, heightLabel));
        labelRanking.setMinimumSize(new Dimension(widthLabel, heightLabel));
        labelRanking.setPreferredSize(new Dimension(widthLabel, heightLabel));
        labelRanking.setBounds(width/2-widthLabel/2, heightLabel/2, widthLabel, heightLabel);
    }

    private void crearRanking(){
        String[] files = controladorPresentacio.getRanking();
        //Create the list and put it in a scroll pane.
        int widthList = 500;
        heightCell = 80;
        int heightList = heightCell*files.length;
        list = new JList(files){
            @Override
            public void updateUI() {
                setUI(new CustomListUI());
            }
        };
        list.setMaximumSize(new Dimension(widthList, heightList));
        list.setMinimumSize(new Dimension(widthList, heightList));
        list.setPreferredSize(new Dimension(widthList, heightList));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFixedCellHeight(heightCell);
        list.setBackground(new Color(39,54,90));
        list.setEnabled(false);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }
        });
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

            @Override
            public Color getBackground() {
                return new Color(39,54,90);
            }
        });
        scrollPane.setViewportView(list);
        int widthScroll = widthList+50;
        int heightScroll = 500;
        scrollPane.setMaximumSize(new Dimension(widthScroll, heightScroll));
        scrollPane.setMinimumSize(new Dimension(widthScroll, heightScroll));
        scrollPane.setPreferredSize(new Dimension(widthScroll, heightScroll));
        scrollPane.setBounds(width/2-widthScroll/2, height/2-heightScroll/2, widthScroll, heightScroll);
    }
    public void actualitzarRanking(){
        String[] files = controladorPresentacio.getRanking();
        DefaultListModel model = new DefaultListModel<>();
        for(int i = 0; i< files.length; ++i) model.addElement(files[i]);
        list.setModel(model);
        scrollPane.revalidate();
        scrollPane.repaint();
    }
    public JPanel getContent(){
        return panel;
    }

    public void setIndex(int i) {
        if (i == -1){
            list.setSelectedIndex(i);
        }
        else {
            list.setSelectedIndex(i);
            scrollPane.getVerticalScrollBar().setValue(list.getSelectedIndex() * heightCell);
        }
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
}
