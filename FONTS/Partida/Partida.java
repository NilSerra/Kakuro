package Partida;

import Ranking.Puntuacio;
import Taulell.Kakuro;
import Usuari.Usuari;

public class Partida {
    Kakuro kakuro;
    long tempsInicial, tempsFinal;
    Puntuacio puntuacio;
    int numPistes;
    private boolean ajuda;
    final int FACIL = 0;
    final int NORMAL = 1;
    final int DIFICIL = 2;
    public Partida() {
        kakuro = new Kakuro();
    }

    public void llegirRepositori(String kakuroRepositori){
        numPistes = 0;
        kakuro.llegirKakuroText(kakuroRepositori);
        kakuro.resoldreKakuro();
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

    public void generarKakuro(int n, int m, int dif) {
        if (dif == FACIL){
            numPistes = 5;
            kakuro.generarKakuro(n,m,(n==5 && m== 5? (n+m-1)+(n*m)/4:(n+m-1)+(n*m)/3),  (n==5 && m== 5? (n+m-1)+(n*m)/4:(n+m-1)+2*(n*m)/5),numPistes) ;
        }
        else if (dif == NORMAL){
            numPistes = 3;
            kakuro.generarKakuro(n,m,(n==5 && m== 5? (n+m-1)+(n*m)/4:(n+m-1)+(n*m)/3), (n==5 && m== 5? (n+m-1)+(n*m)/4:(n+m-1)+2*(n*m)/5), numPistes);
        }
        else if(dif == DIFICIL){
            numPistes = 0;
            kakuro.generarKakuro(n,m, (n==5 && m== 5? (n+m-1)+(n*m)/4:(n+m-1)+2*(n*m)/7), (n==5 && m== 5? (n+m-1)+(n*m)/4:(n+m-1)+(n*m)/3), numPistes);
        }
        numPistes = 0;
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
        tempsInicial = System.currentTimeMillis()/1000;
        ajuda = false;
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

    public void setAjudaActivada(boolean ajuda) {
        this.ajuda = ajuda;
    }
}
