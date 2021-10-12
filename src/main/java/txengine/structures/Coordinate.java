package txengine.structures;

public class Coordinate {
    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x ;
        this.y = y;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
