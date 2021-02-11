package Repositori;

import Taulell.Kakuro;

import java.util.HashMap;
import java.util.Map;

public class Repositori {
    Map<String, String> kakuros;

    public Repositori() {

    }
    public void llegir(Map<String, String> kakuros){
        this.kakuros = new HashMap();
        for (Map.Entry<String, String> entry : kakuros.entrySet()) {
            Kakuro kakuroAux = new Kakuro();
            if (kakuroAux.llegirKakuroText(entry.getValue())) this.kakuros.put(entry.getKey(), entry.getValue());
        }
    }
    public String getRepositori(String repositori){
        return kakuros.get(repositori);
    }

    public void afegirRepositori(String fileName, String repositori){
        kakuros.put(fileName, repositori);
    }
    public String[] getRepositoris() {
        String[] repositoris = new String[kakuros.size()];
        int index = 0;
        for ( Map.Entry<String, String> repositori : kakuros.entrySet()) {
            repositoris[index] = repositori.getKey();
            ++index;
        }
        return repositoris;
    }

    public void borrarRepositori(String repositori) {
        this.kakuros.remove(repositori);
    }
}
