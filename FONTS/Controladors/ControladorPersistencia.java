package Controladors;

import GestorFitxers.GestorFitxers;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

public class ControladorPersistencia {

    GestorFitxers gestorFitxers;

    public ControladorPersistencia() {
        gestorFitxers = new GestorFitxers();
        File f = new File("Repositori");
        if(!f.exists()) {
            if(f.mkdir()) System.out.println("Repositori creat");
            else System.out.println("No s'ha pogut crear el Repositori");
        }
        f = new File("Partides");
        if(!f.exists()) {
            if(f.mkdir()) System.out.println("Partides creat");
            else System.out.println("No s'ha pogut crear Partides");
        }
        f = new File("Ranking");
        if(!f.exists())
        {
            if(f.mkdir()) System.out.println("Ranking creat");
            else System.out.println("No s'ha pogut crear Ranking");
        }
    }

    public Map<String, String> carregarRepositori() {
        Map<String, String> repositoris = new HashMap<>();
        File dir = new File("Repositori\\");

        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");
            }
        });
        for(int i = 0; i< files.length; ++i){
            repositoris.put(files[i].getName().replaceFirst(".txt", ""), gestorFitxers.llegir("Repositori\\"+files[i].getName()));
        }
        return repositoris;
    }

    public void guardarPartida(String usuari, String partida){
        File f = new File("Partides\\"+usuari);
        if(!f.exists()) {
            if(!f.mkdir()) System.out.println("No s'ha pogut crear la Partida");
        }
        gestorFitxers.escriure(partida, "Partides\\"+usuari+"\\partida.kakuro");
    }
    public String carregarPartida(String usuari){
        return gestorFitxers.llegir("Partides\\"+usuari+"\\partida.kakuro");
    }
    public void guardarRanking(String ranking){
        gestorFitxers.escriure(ranking, "Ranking\\ranking.txt");
    }
    public String carregarRanking(){
        return gestorFitxers.llegir("Ranking\\ranking.txt");
    }

    public void borrarPartida(String usuari) {
        gestorFitxers.esborrar("Partides\\"+usuari+"\\partida.kakuro");
        gestorFitxers.esborrar("Partides\\"+usuari);
    }

    public void guardarRepositori(String fileName, String partida) {
        gestorFitxers.escriure(partida, "Repositori\\"+fileName+".txt");
    }

    public void borrarRepositori(String repositori) {
        gestorFitxers.esborrar("Repositori\\"+repositori+".txt");
    }
}
