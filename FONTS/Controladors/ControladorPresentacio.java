package Controladors;


import Vistes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ControladorPresentacio{
    private ControladorDomini controladorDomini;
    private VistaMain vistaMain;
    private VistaKakuro vistaKakuro;
    private VistaJugar vistaJugar;
    private VistaRanking vistaRanking;
    private VistaLogIn vistaLogIn;
    private VistaEditar vistaEditar;
    private VistaRepositori vistaRepositori;
    private VistaResoldre vistaResoldre;
    private VistaComJugar vistaComJugar;
    private VistaInformacio vistaInformacio;

    public static void main(String[] args) {
        ControladorPresentacio controladorPresentacio = new ControladorPresentacio();
    }


    public ControladorPresentacio(){
        controladorDomini = new ControladorDomini();
        vistaLogIn = new VistaLogIn(this);
        vistaMain = new VistaMain(this);
        vistaKakuro = new VistaKakuro(this);
        vistaJugar = new VistaJugar(this);
        vistaRanking = new VistaRanking(this);
        vistaEditar = new VistaEditar(this);
        vistaRepositori = new VistaRepositori(this);
        vistaResoldre = new VistaResoldre(this);
        vistaComJugar = new VistaComJugar(this);
        vistaInformacio = new VistaInformacio(this);
        vistaLogIn.hacerVisible();
    }
    public void canviMenuJugar(){
        vistaMain.activar();
        vistaMain.setContent(vistaJugar.getContent());
    }
    public void canviMenuRanking(){
        vistaRanking.setIndex(-1);
        vistaMain.activar();
        vistaMain.setContent(vistaRanking.getContent());
    }
    public void canviMenuEditar(){
        vistaMain.activar();
        vistaMain.setContent(vistaEditar.getContent());
    }
    public void canviJugarKakuro(int n, int m, int dif){
        controladorDomini.setContinuarPartida(false);
        controladorDomini.generarKakuro(n, m, dif);
        vistaKakuro.setKakuro(controladorDomini.getKakuroActual());
        controladorDomini.comensarPartida();
        vistaJugar.reset();
        TimerTask timerTask = new TimerTask()
        {
            public void run()
            {
                vistaKakuro.setTemps(controladorDomini.getTemps());
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        vistaMain.setContent(vistaKakuro.getContent());
    }
    public void canviJugarKakuro(){
        controladorDomini.setContinuarPartida(true);
        vistaKakuro.setKakuro(controladorDomini.getKakuroActual());
        controladorDomini.comensarPartida();
        TimerTask timerTask = new TimerTask()
        {
            public void run()
            {
                vistaKakuro.setTemps(controladorDomini.getTemps());
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        vistaMain.setContent(vistaKakuro.getContent());
        vistaKakuro.setAjuda(controladorDomini.getAjudaActivada());
    }
    public void canviMenuRepositori() {
        vistaMain.activar();
        vistaMain.setContent(vistaRepositori.getContent());
    }
    public void canviLogInMenu(String text) {
        vistaMain = new VistaMain(this);
        vistaKakuro = new VistaKakuro(this);
        vistaJugar = new VistaJugar(this);
        vistaRanking = new VistaRanking(this);
        vistaEditar = new VistaEditar(this);
        vistaRepositori = new VistaRepositori(this);
        vistaResoldre = new VistaResoldre(this);
        vistaComJugar = new VistaComJugar(this);
        vistaInformacio = new VistaInformacio(this);
        controladorDomini.logIn(text);
        vistaMain.hacerVisible();
        vistaMain.setName(text);
        vistaMain.setContent(vistaJugar.getContent());
        vistaMain.setOptionMenu(0, false);
    }
    public void canviMenuResoldre(){
        vistaMain.activar();
        vistaMain.setContent(vistaResoldre.getContent());
    }
    public void canviMenuLogIn() {
        vistaLogIn = new VistaLogIn(this);
        vistaLogIn.hacerVisible();
    }

    public void canviMenuHelp() {
        vistaMain.activar();
        vistaMain.setContent(vistaComJugar.getContent());
    }

    public void canviMenuInfo() {
        vistaMain.activar();
        vistaMain.setContent(vistaInformacio.getContent());
    }

    public String getKakuroActual(){
        return controladorDomini.getKakuroActual();
    }

    public String getKakuroResolt(){

        return controladorDomini.getKakuroResolt();
    }

    public void setKakuroNum(int i, int j, int value){
        controladorDomini.setKakuroNum(i, j, value);
    }

    public void guardarKakuro(){
        controladorDomini.guardarPartida();
        vistaMain.setContent(vistaJugar.getContent());
    }

    public String[] getRepositoris(){
        return controladorDomini.getRepositoris();
    }

    public void escollirRepositoriKakuro(){
        vistaMain.desactivar();
        FileChooser fileChooser = new FileChooser(this);
        fileChooser.hacerVisible();
    }
    public void repositoriKakuroEscollit(String repositori){
        controladorDomini.carregarRepositori(repositori);
        vistaKakuro.setKakuro(controladorDomini.getKakuroActual());
        controladorDomini.comensarPartida();

        TimerTask timerTask = new TimerTask()
        {
            public void run()
            {
                vistaKakuro.setTemps(controladorDomini.getTemps());
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        vistaMain.setContent(vistaKakuro.getContent());
        vistaMain.activar();
    }

    public void kakuroAcabat() {
        int index = controladorDomini.finalitzarPartida();
        vistaRanking.actualitzarRanking();
        canviKakuroRanking(index);
    }

    private void canviKakuroRanking(int index) {
        vistaRanking.setIndex(index);
        vistaMain.activar();
        vistaMain.setContent(vistaRanking.getContent());
        vistaMain.setOptionMenu(1, false);
    }

    public void getPistaKakuro() {
        controladorDomini.getPistaKakuro();
    }

    public boolean podemContinuarPartida() {
        return controladorDomini.podemContinuarPartida();
    }

    public boolean[] getKakuroValorsPossibles(Integer posY, Integer posX) {
        return controladorDomini.getKakuroValorsPossibles(posY, posX);
    }

    public String[] getRanking() {
        return controladorDomini.getRanking();
    }


    public void editarCelaKakuro(int celaY, int celaX, boolean esBlanc, String val1, String val2) {
        controladorDomini.editarCelaKakuro(celaY, celaX, esBlanc, val1, val2);
    }

    public void setKakuroEditat(String kakuroEditat) {
        controladorDomini.setKakuroEditat(kakuroEditat);
    }

    public boolean verificarKakuroEditat() {
        return controladorDomini.verificarKakuroEditat();
    }

    public void guardarKakuroARepositori(String fileText) {
        controladorDomini.guardarKakuroARepositori(fileText);
        vistaRepositori.actualitzarRepositori();
    }

    public String getRepositori(String repositori) {
        return controladorDomini.getRepositori(repositori);
    }

    public void canviRepositoriEditar(String repositori) {
        vistaMain.activar();
        vistaMain.setOptionMenu(2, false);
        vistaEditar.setKakuro(getRepositori(repositori));
        vistaMain.setContent(vistaEditar.getContent());
    }

    public void borrarRepositori(String repositori) {
        controladorDomini.borrarRespositori(repositori);
    }

    public boolean[] getKakuroValorsPossiblesPista(Integer posY, Integer posX) {
        return controladorDomini.getKakuroValorsPossiblesPista(posY, posX);
    }

    public String resoldreKakuro(String repositoris) {
        return controladorDomini.resoldreKakuro(repositoris);
    }

    public long getTempsResoldre() {
        return controladorDomini.getTempsResoldre();
    }


    public String getNumPistes() {
        return controladorDomini.getNumPistes();
    }

    public void setAjudaPartida(boolean b) {
        controladorDomini.setAjudaActivada(b);
    }

}
