package Resoldre;

import Taulell.Kakuro;

public class Resoldre {
    private Kakuro kakuro;
    private long tempsResoldre;

    public Resoldre() {
        kakuro = new Kakuro();
    }
    public void llegir(String kakuroText){
        kakuro.llegirKakuroText(kakuroText);
    }
    public void resoldre() {
        long tempsInicial = System.currentTimeMillis();
        kakuro.resoldreKakuro();
        tempsResoldre = System.currentTimeMillis()-tempsInicial;
    }

    public long getTempsResoldre() {
        return tempsResoldre;
    }

    public String getKakuroResolt() {
        return kakuro.getKakuroResolt();
    }
}
