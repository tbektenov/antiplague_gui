package model.country;

public class CountryPoint {
    private double x;
    private double y;

    public CountryPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getAbsoluteX(int panelWidth) {
        return (int) (x * panelWidth);
    }

    public int getAbsoluteY(int panelHeight) {
        return (int) (y * panelHeight);
    }
}
