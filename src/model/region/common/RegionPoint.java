package model.region.common;

public class RegionPoint {
    private float x;
    private float y;

    public RegionPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getTrueX(int panelWidth) {
        return (int) (x * panelWidth);
    }

    public int getTrueY(int panelHeight) {
        return (int) (y * panelHeight);
    }
}
