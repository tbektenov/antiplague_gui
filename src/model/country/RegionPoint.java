package model.country;

public class RegionPoint {
    private double x;
    private double y;

    public RegionPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getAbsoluteX(int panelWidth) {
        return (int) (x * panelWidth);
    }

    public int getAbsoluteY(int panelHeight) {
        return (int) (y * panelHeight);
    }

}
