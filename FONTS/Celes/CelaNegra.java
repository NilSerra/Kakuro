package Celes;
import java.util.HashSet;
import java.util.Set;

public class CelaNegra extends Cela{
    int maxSumV, maxSumH;

    public CelaNegra(){
        maxSumH = maxSumV = -1;
    }
    @Override
    public boolean type() {
        return false;
    }

    @Override
    public int getValue() {
        return -1;
    }

    @Override
    public void setValue(int value) {
        return;
    }

    @Override
    public int getMaxSumV() {
        return maxSumV;
    }

    @Override
    public void setMaxSumV(int value) {
        if(value == -1 || (3 <= value && value <= 45)) {
            this.maxSumV = value;
        }
    }

    @Override
    public int getMaxSumH() {
        return maxSumH;
    }

    @Override
    public void setMaxSumH(int value) {
        if(value == -1 || (1 <= value && value <= 45)) {
            this.maxSumH = value;
        }
    }
}
