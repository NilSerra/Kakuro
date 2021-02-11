package GestorFitxers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class GestorFitxers {
    public GestorFitxers() {

    }

    public String llegir(String path){
        String res = "";
        Path fp = Path.of(path);
        if (!Files.exists(fp)) return "";
        try {
            res = Files.readString(fp);
        }catch (IOException e){
            e.printStackTrace();
        }
        return res;
    }
    public void escriure(String aux, String path){
        try {
            File myObj = new File(path);
            FileWriter fileWriter = new FileWriter(myObj.getPath());
            fileWriter.write(aux);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error:");
            e.printStackTrace();
        }
    }
    public void esborrar(String path){
        File myObj = new File(path);
        try{
            if (!myObj.delete())System.out.println("Failed to delete the file.");
        }
        catch (SecurityException e){
            System.out.println("Error:");
            e.printStackTrace();
        }
    }


}
