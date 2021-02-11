package Taulell;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import Celes.Cela;

public class TiraCeles {
    boolean orientacio; //True si es horitzontal, false si vertical
    int longitud;
    int suma;
    Cela celaNegra;
    int mida;
    boolean[] celesUniques;

    public TiraCeles(boolean orientacio, Cela celaNegra) {
        this.orientacio = orientacio;
        this.longitud = 0;
        this.celaNegra = celaNegra;
        this.suma = 0;
        celesUniques = new boolean[9];
        for(int i = 0; i<9; ++i) celesUniques[i] = true;
        mida = 0;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public void addCela( int k){
        if (!celesUniques[k]) return;
        suma+=(k+1);
        ++mida;
        celesUniques[k] = false;
    }

    public void removeCela(int k){
        if (celesUniques[k]) return;
        suma-=(k+1);
        --mida;
        celesUniques[k] = true;
    }
    public boolean celaUnica(int k){
        return celesUniques[k];
    }
    public int celesRestants(){
        return longitud-mida;
    }
    public int sumaRestant(){
        return (orientacio?celaNegra.getMaxSumH():celaNegra.getMaxSumV())-suma;
    }
    public void setMaxSum(int sum){
        if (orientacio) celaNegra.setMaxSumH(sum);
        else celaNegra.setMaxSumV(sum);
        System.out.println(sum);
    }
    public int getMaxSum(){
        if (orientacio) return celaNegra.getMaxSumH();
        return celaNegra.getMaxSumV();
    }

    public int getMida() {
        return mida;
    }

    public int getSuma() {
        return suma;
    }

    public int getLongitud() {
        return longitud;
    }
}