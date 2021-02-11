package EditarKakuro;

import Taulell.Kakuro;

public class EditarKakuro {
    private Kakuro kakuro;

    public EditarKakuro() {
    }

    public void setKakuroEditat(String kakuroEditat) {
        kakuro = new Kakuro();
        kakuro.llegirKakuroText(kakuroEditat);
    }

    public void editarCela(int celaY, int celaX, boolean esBlanc, String val1, String val2) {
        kakuro.editarCela(celaY, celaX, esBlanc, val1, val2);
    }

    public boolean verificarKakuroEditat() {
        kakuro.llegirKakuroText(kakuro.getKakuroActual());
        if (!kakuro.verificarFormat()){
            System.out.println("Error de format");
            return false;
        }
        try{
            return kakuro.resoldreKakuro()==1;
        }
        catch (Exception e){
            return false;
        }
    }

    public String getKakuro() {
        return kakuro.getKakuroActual();
    }
}
