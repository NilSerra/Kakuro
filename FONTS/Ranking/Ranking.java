package Ranking;

import Usuari.Usuari;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Ranking {
    PriorityQueue<Puntuacio> ranking;

    public Ranking() {
        ranking = new PriorityQueue<Puntuacio>( new Comparator<Puntuacio>() {
            public int compare(Puntuacio n1, Puntuacio n2) {
                if(n1.getPuntuacio() > n2.getPuntuacio()) return n1.getPuntuacio();
                return n2.getPuntuacio();
            }
        });
    }
    public void llegir(String ranking) {
        this.ranking = new PriorityQueue<Puntuacio>( new Comparator<Puntuacio>() {
            public int compare(Puntuacio n1, Puntuacio n2) {
                return (n2.getPuntuacio() - n1.getPuntuacio());
            }
        });
        Scanner myReader = new Scanner(ranking);
        while (myReader.hasNextLine()){
            String data = myReader.nextLine();
            String[] mida = data.split(",");
            Usuari u = new Usuari(mida[0]);
            Puntuacio p = new Puntuacio(u);
            p.setPuntuacio(Integer.parseInt(mida[1]));
            this.ranking.add(p);
        }
        myReader.close();
    }

    public int afegirPuntuacio(Puntuacio puntuacio){
        ranking.add(puntuacio);
        PriorityQueue<Puntuacio> auxRanking = new PriorityQueue(ranking);
        int index = 0;
        while (!auxRanking.isEmpty()) {
            Puntuacio p = auxRanking.remove();
            if (p.equals(puntuacio)) return index;
            index++;
        }
        return 0;
    }
    public String getRankingString(){
        String aux = "";
        PriorityQueue<Puntuacio> auxRanking = new PriorityQueue(ranking);
        while (!auxRanking.isEmpty()) {
            Puntuacio p = auxRanking.remove();
            aux += (p.getUsuari().getNom() +','+ p.getPuntuacio()+"\n");
        }
        return aux;
    }

    public String[] getRanking() {
        String[] aux = new String[ranking.size()];
        PriorityQueue<Puntuacio> auxRanking = new PriorityQueue(ranking);
        int index = 0;
        while (!auxRanking.isEmpty()) {
            Puntuacio p = auxRanking.remove();
            aux[index] = (p.getUsuari().getNom() +','+ p.getPuntuacio());
            index++;
        }
        return aux;
    }
}
