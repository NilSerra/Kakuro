package Celes;

public class CelaBlanca extends Cela{
    int value;

    public CelaBlanca(){
        value = 0;
    }
    @Override
    public boolean type() {
        return true;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        if(value >= 0 && value < 10) {
            this.value = value;
        }
        else System.out.println("Error, el valor introduit no esta dins el rang acceptat en celaBlanca");
    }

    @Override
    public int getMaxSumV() {
        return -1;
    }

    @Override
    public void setMaxSumV(int value) {
        return;
    }

    @Override
    public int getMaxSumH() {
        return -1;
    }

    @Override
    public void setMaxSumH(int value) {
        return;
    }
}

