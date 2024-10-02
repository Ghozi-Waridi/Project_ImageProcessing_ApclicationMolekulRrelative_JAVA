package Util;

public class Senyawa {
    private String arSimbol;
    private double arValue;
    private double id;
    public Senyawa(String arSimbol, double arValue, double id){
        this.arSimbol = arSimbol;
        this.arValue = arValue;
        this.id = id;
    }

    public Senyawa() {
    }

    public String getArSimbol() {
        return arSimbol;
    }

    public void setArSimbol(String arSimbol) {
        this.arSimbol = arSimbol;
    }

    public double getArValue() {
        return arValue;
    }

    public void setArValue(double arValue) {
        this.arValue = arValue;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(arValue);
    }
}
