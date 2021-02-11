package Drivers;
import static org.junit.Assert.*;

import GestorFitxers.GestorFitxers;
import Taulell.Kakuro;
import org.junit.runner.JUnitCore;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class KakuroTest {

    public static void main(String[] args){
        JUnitCore.main(
                "KakuroTest");
    }
    @org.junit.Before
    public void setUp() throws Exception {
        System.out.println("Inici del test");
    }

    @org.junit.After
    public void tearDown() throws Exception {
        System.out.println("Final del test\n");
    }

    @org.junit.Test
    public void resoldreKakuro0sol() {
        System.out.println("Llegint kakuro de Repositori\\JUNIT\\ amb nom kakuro0sol.txt");
        Kakuro kakuro0 = new Kakuro();
        GestorFitxers gestorFitxers = new GestorFitxers();
        String absolutePath = new File("").getAbsolutePath();
        String[] aux = absolutePath.split("\\\\");
        if (aux[aux.length-1].equals("src")) absolutePath = absolutePath.replaceAll("\\\\src$", "");
        boolean b = kakuro0.llegirKakuroText(gestorFitxers.llegir(absolutePath+"\\Repositori\\JUNIT\\kakuro0sol.txt"));
        assertTrue("Kakuro no llegit correctament",b);
        int res = kakuro0.resoldreKakuro();
        assertEquals("Error en resoldre", 0, res);

        System.out.println("Llegint kakuro de Repositori\\JUNIT\\ amb nom kakuro0sol2.txt");
        kakuro0 = new Kakuro();
        b = kakuro0.llegirKakuroText(gestorFitxers.llegir(absolutePath+"\\Repositori\\JUNIT\\kakuro0sol2.txt"));
        assertTrue("Kakuro no llegit correctament",b);
        res = kakuro0.resoldreKakuro();
        assertEquals("Error en resoldre", 0, res);
    }

    @org.junit.Test
    public void resoldreKakuro1sol() {
        System.out.println("Llegint kakuro de Repositori\\JUNIT\\ amb nom kakuro1sol.txt");
        Kakuro kakuro0 = new Kakuro();
        GestorFitxers gestorFitxers = new GestorFitxers();
        String absolutePath = new File("").getAbsolutePath();
        String[] aux = absolutePath.split("\\\\");
        if (aux[aux.length-1].equals("src")) absolutePath = absolutePath.replaceAll("\\\\src$", "");
        boolean b = kakuro0.llegirKakuroText(gestorFitxers.llegir(absolutePath+"\\Repositori\\JUNIT\\kakuro1sol.txt"));
        assertTrue("Kakuro no llegit correctament",b);
        int res = kakuro0.resoldreKakuro();
        assertEquals("Error en resoldre", 1, res);

        System.out.println("Llegint kakuro de Repositori\\JUNIT\\ amb nom kakuro1sol2.txt");
        kakuro0 = new Kakuro();
        b = kakuro0.llegirKakuroText(gestorFitxers.llegir(absolutePath+"\\Repositori\\JUNIT\\kakuro1sol2.txt"));
        assertTrue("Kakuro no llegit correctament",b);
        res = kakuro0.resoldreKakuro();
        assertEquals("Error en resoldre", 1, res);
    }

    @org.junit.Test
    public void resoldreKakuro2sol() {
        System.out.println("Llegint kakuro de Repositori\\JUNIT\\ amb nom kakuro2sol.txt");
        Kakuro kakuro0 = new Kakuro();
        GestorFitxers gestorFitxers = new GestorFitxers();
        String absolutePath = new File("").getAbsolutePath();
        String[] aux = absolutePath.split("\\\\");
        if (aux[aux.length-1].equals("src")) absolutePath = absolutePath.replaceAll("\\\\src$", "");
        boolean b = kakuro0.llegirKakuroText(gestorFitxers.llegir(absolutePath+"\\Repositori\\JUNIT\\kakuro2sol.txt"));
        assertTrue("Kakuro no llegit correctament",b);
        int res = kakuro0.resoldreKakuro();
        assertEquals("Error en resoldre", 2, res);

        System.out.println("Llegint kakuro de Repositori\\JUNIT\\ amb nom kakuro2sol2.txt");
        kakuro0 = new Kakuro();
        b = kakuro0.llegirKakuroText(gestorFitxers.llegir(absolutePath+"\\Repositori\\JUNIT\\kakuro2sol2.txt"));
        assertTrue("Kakuro no llegit correctament",b);
        res = kakuro0.resoldreKakuro();
        assertEquals("Error en resoldre", 2, res);
    }

    @org.junit.Test
    //@org.junit.Ignore
    public void generarKakuro() {
        Kakuro kakuro = new Kakuro();
        kakuro.generarKakuro(5,5,15, 15,0);
        System.out.println("Creat Kakuro 5x5");
        kakuro = new Kakuro();
        kakuro.generarKakuro(6,6,23, 23,0);
        System.out.println("Creat Kakuro 6x6");
        kakuro = new Kakuro();
        kakuro.generarKakuro(7,7,35, 35,0);
        System.out.println("Creat Kakuro 7x7");
        kakuro = new Kakuro();
        kakuro.generarKakuro(8,8,30, 30,0);
        System.out.println("Creat Kakuro 8x8");
        kakuro = new Kakuro();
        kakuro.generarKakuro(9,9,44, 44,0);
        System.out.println("Creat Kakuro 9x9");

        kakuro = new Kakuro();
        kakuro.generarKakuro(5,9,28, 28,4);
        System.out.println("Creat Kakuro 5x9");
        kakuro = new Kakuro();
        kakuro.generarKakuro(9,5,30, 30,3);
        System.out.println("Creat Kakuro 9x5");
        kakuro = new Kakuro();
        kakuro.generarKakuro(6,7,26, 26,9);
        System.out.println("Creat Kakuro 6x7");
        kakuro = new Kakuro();
        kakuro.generarKakuro(8,9,40, 40,5);
        System.out.println("Creat Kakuro 8x9");
    }

}
