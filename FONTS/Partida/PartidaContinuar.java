package Partida;

import Ranking.Puntuacio;
import Taulell.Kakuro;
import Usuari.Usuari;

public class PartidaContinuar {
    Kakuro kakuro;
    int tempsInicialFitxer;
    long tempsInicial, tempsFinal;
    Puntuacio puntuacio;
    int numPistes;
    private boolean ajuda;

    public PartidaContinuar() {
        kakuro = new Kakuro();
    }
    public boolean llegir(String kakuroLlegit){
        if (kakuroLlegit.equals("")){
            return false;
        }
        String[] kakuroInterpretat = kakuroLlegit.split("\n",4);

        try {
            tempsInicialFitxer = Integer.parseInt(kakuroInterpretat[0].split(":")[1]);
        }
        catch(NumberFormatException e){
            System.out.println("Error al llegir la partida");
            return false;
        }
        try {
            numPistes = Integer.parseInt(kakuroInterpretat[1].split(":")[1]);
        }
        catch(NumberFormatException e){
            System.out.println("Error al llegir la partida");
            return false;
        }
        ajuda = Boolean.parseBoolean(kakuroInterpretat[2].split(":")[1]);
        String[] aux = kakuroInterpretat[3].split("Solucio:\n");
        String kakuroActual = aux[0].replace("Actual:\n", "");
        String kakuroResolt = aux[1];
        kakuro.llegirKakuroText(kakuroActual);
        kakuro.setKakuroResolt(kakuroResolt);
        return true;
    }

    public String getPartida(){
        Long temps = (System.currentTimeMillis()/1000)-tempsInicial;
        String value = "";
        value = value.concat("Temps:"+Long.toString(temps)+"\n");
        value = value.concat("Pistes:"+numPistes+"\n");
        value = value.concat("Ajuda:"+ajuda+"\n");
        value = value.concat("Actual:\n");
        value = value.concat(kakuro.getKakuroActual());
        value = value.concat("\n");
        value = value.concat("Solucio:\n");
        value = value.concat(kakuro.getKakuroResolt());
        return value;
    }

    public long getTemps(){
        return (System.currentTimeMillis()/1000)-tempsInicial;
    }

    public void getPista() {
        ++numPistes;
        kakuro.getPistaKakuro();
    }

    public Puntuacio calcularPuntuacio(Usuari usuari) {
        puntuacio = new Puntuacio(usuari);
        puntuacio.calcularPuntuacio(kakuro.getN(), kakuro.getM(), kakuro.getNumBlanques(), tempsFinal, numPistes, ajuda);
        return puntuacio;
    }

    public void comensarPartida() {
        tempsInicial = (System.currentTimeMillis()/1000)-tempsInicialFitxer;
    }

    public void acabarPartida(){
        tempsFinal = (System.currentTimeMillis()/1000)-tempsInicial;
    }

    public boolean[] getValorsPossibles(Integer posY, Integer posX) {
        return kakuro.getValorsPossibles(posY, posX);
    }

    public String getKakuroResolt() {
        return kakuro.getKakuroResolt();
    }

    public String getKakuroActual() {
        return kakuro.getKakuroActual();
    }

    public void setKakuroNum(int i, int j, int value) {
        kakuro.set_num(i, j, value);
    }

    public boolean[] getValorsPossiblesPista(Integer posY, Integer posX) {
        this.ajuda = true;
        return kakuro.getValorsPossiblesPista(posY, posX);
    }

    public String getNumPistes() {
        return String.valueOf(numPistes);
    }

    public boolean getAjudaActivada() {
        return this.ajuda;
    }
    public void setAjudaActivada(boolean ajuda){
        this.ajuda = ajuda;
    }
}
