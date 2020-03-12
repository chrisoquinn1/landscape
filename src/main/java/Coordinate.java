import java.util.Objects;

public class Coordinate {
    private final int position;
    private final int height;

    Coordinate(int position, int height) {
        this.position = position;
        this.height = height;
    }

    int getPosition() {
        return position;
    }

    int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return position == that.position &&
                height == that.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, height);
    }

    @Override
    public String toString() {
        return "LandscapePosition{" +
                "position=" + position +
                ", height=" + height +
                '}';
    }
}
