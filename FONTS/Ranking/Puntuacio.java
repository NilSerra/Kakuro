package Ranking;

import Usuari.Usuari;

import java.util.Comparator;

public class Puntuacio {
    Usuari usuari;
    Integer puntuacio;

    public Puntuacio(Usuari usuari) {
        this.usuari = usuari;
    }
    public void calcularPuntuacio(int n, int m, int nBlanques, long temps, int pistes, boolean ajuda){
        puntuacio = Math.max(n*m*(nBlanques-pistes-(ajuda?nBlanques/2:0))-(int)temps, 0);
    }
    public Integer getPuntuacio() {
        return puntuacio;
    }

    public Usuari getUsuari() {
        return usuari;
    }


    public void setPuntuacio(int puntuacio) {
        this.puntuacio = puntuacio;
    }
}
