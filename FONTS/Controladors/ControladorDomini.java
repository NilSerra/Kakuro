package Controladors;
import EditarKakuro.EditarKakuro;
import Partida.*;
import Ranking.Ranking;
import Repositori.Repositori;
import Resoldre.Resoldre;
import Usuari.Usuari;

import java.util.Arrays;


public class ControladorDomini {
    Ranking ranking;
    Partida partida;
    PartidaContinuar partidaContinuar;
    Repositori repositori;
    EditarKakuro editarKakuro;
    Resoldre resoldreKakuro;
    boolean continuemPartida, podemContinuarPartida;
    ControladorPersistencia controladorPersistencia;
    Usuari usuari;

    public ControladorDomini() {
        ranking = new Ranking();
        partida = new Partida();
        partidaContinuar = new PartidaContinuar();
        repositori = new Repositori();
        editarKakuro = new EditarKakuro();
        resoldreKakuro = new Resoldre();
        controladorPersistencia = new ControladorPersistencia();
        ranking.llegir(controladorPersistencia.carregarRanking());
        repositori.llegir(controladorPersistencia.carregarRepositori());
    }
    public void logIn(String nom) {
        usuari = new Usuari(nom);
        System.out.println("Login: "+nom);
        podemContinuarPartida = partidaContinuar.llegir(controladorPersistencia.carregarPartida(usuari.getNom()));
    }
    public void generarKakuro(int n, int m, int dif){
        partida.generarKakuro(n, m, dif);
    }

    public String getKakuroResolt(){
        if (continuemPartida) return partidaContinuar.getKakuroResolt();
        return partida.getKakuroResolt();
    }
    public String getKakuroActual(){
        if (continuemPartida) return partidaContinuar.getKakuroActual();
        return partida.getKakuroActual();
    }

    public void setKakuroNum(int i, int j, int value){
        if (continuemPartida){
            partidaContinuar.setKakuroNum(i, j, value);
            return;
        }
        partida.setKakuroNum(i, j, value);
    }

    public long getTemps(){
        if (continuemPartida) return partidaContinuar.getTemps();
        return partida.getTemps();
    }

    public void comensarPartida(){
        if (continuemPartida){
            partidaContinuar.comensarPartida();
            return;
        }
        partida.comensarPartida();
    }

    public void guardarPartida(){
        if (continuemPartida) {
            controladorPersistencia.guardarPartida(usuari.getNom(), partidaContinuar.getPartida());
            partidaContinuar.llegir(partidaContinuar.getPartida());
            return;
        }
        controladorPersistencia.guardarPartida(usuari.getNom(), partida.getPartida());
        partidaContinuar.llegir(partida.getPartida());
        podemContinuarPartida = true;
    }

    public int finalitzarPartida() {
        int index = 0;
        if (continuemPartida) index = ranking.afegirPuntuacio(partidaContinuar.calcularPuntuacio(usuari));
        else index = ranking.afegirPuntuacio(partida.calcularPuntuacio(usuari));

        controladorPersistencia.borrarPartida(usuari.getNom());
        if (continuemPartida) partidaContinuar.acabarPartida();
        else partida.acabarPartida();
        controladorPersistencia.guardarRanking(ranking.getRankingString());
        podemContinuarPartida = false;
        return index;
    }

    public void getPistaKakuro() {
        if (continuemPartida){
            partidaContinuar.getPista();
            return;
        }
        partida.getPista();
    }

    public boolean podemContinuarPartida(){
        return this.podemContinuarPartida;
    }

    public void carregarRepositori(String rep){
        continuemPartida = false;
        partida.llegirRepositori(repositori.getRepositori(rep));
    }

    public String[] getRepositoris(){
        return repositori.getRepositoris();
    }

    public String[] getRanking() {
        return ranking.getRanking();
    }

    public boolean[] getKakuroValorsPossibles(Integer posY, Integer posX) {
        if (continuemPartida) return partidaContinuar.getValorsPossibles(posY, posX);
        return partida.getValorsPossibles(posY, posX);
    }

    public boolean[] getKakuroValorsPossiblesPista(Integer posY, Integer posX) {
        if (continuemPartida) return partidaContinuar.getValorsPossiblesPista(posY, posX);
        return partida.getValorsPossiblesPista(posY, posX);
    }



    public void editarCelaKakuro(int celaY, int celaX, boolean esBlanc, String val1, String val2) {
        editarKakuro.editarCela(celaY, celaX, esBlanc, val1, val2);
    }

    public void setKakuroEditat(String kakuroEditat) {
        editarKakuro.setKakuroEditat(kakuroEditat);
    }

    public boolean verificarKakuroEditat() {
        return editarKakuro.verificarKakuroEditat();
    }

    public void guardarKakuroARepositori(String fileText) {
        repositori.afegirRepositori(fileText, editarKakuro.getKakuro());
        controladorPersistencia.guardarRepositori(fileText, editarKakuro.getKakuro());
    }

    public String getRepositori(String repositori) {
        return this.repositori.getRepositori(repositori);
    }

    public void borrarRespositori(String repositori) {
        this.repositori.borrarRepositori(repositori);
        controladorPersistencia.borrarRepositori(repositori);
    }

    public String resoldreKakuro(String repositori) {
        resoldreKakuro.llegir(this.repositori.getRepositori(repositori));
        resoldreKakuro.resoldre();
        return resoldreKakuro.getKakuroResolt();
    }

    public long getTempsResoldre() {
        return resoldreKakuro.getTempsResoldre();
    }

    public String getNumPistes() {
        if (continuemPartida) return partidaContinuar.getNumPistes();
        return partida.getNumPistes();
    }

    public void setContinuarPartida(boolean continuemPartida) {
        this.continuemPartida = continuemPartida;
    }

    public boolean getAjudaActivada() {
        return partidaContinuar.getAjudaActivada();
    }
    public void setAjudaActivada(boolean ajuda){
        if (continuemPartida)partidaContinuar.setAjudaActivada(ajuda);
        else partida.setAjudaActivada(ajuda);

    }
}

