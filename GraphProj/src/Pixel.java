import java.awt.Color;

/**
 * Pixel class.
 * Nicholas Garcia, ngarcia5
 *  Ashwin Bhat, abhat4
 *  Lousanna Cai, lcai10
 *  Shain Bannowsky, sbannow1
 *  Data Structures 600.226.02
 *  Project 4
 * 
 * P4 Part C
 * 600.226.02
 */
public class Pixel {

    /**
     * X represents the row.
     */
    private int x;
    /**
     * Y represents the column.
     */
    private int y;
    /**
     * Value represents the color of the pixel.
     */
    private Color value;

    /**
     * Constructor for Pixel.
     * @param row row
     * @param col col
     * @param v is color.
     */
    public Pixel(int row, int col, Color v) {

        this.x = row;
        this.y = col;
        this.value = v;
    }

    /**
     * Returns column.
     * @return int column.
     */
    public int col() {
        return this.y;
    }

    /**
     * Returns column.
     * @return int column.
     */
    public int row() {

        return this.x;
    }

    /**
     * Returns color value.
     * @return Color
     */
    public Color value() {
        return this.value;
    }

    /**
     * Returns red RGB value.
     * @return int red.
     */
    public int getRed() {

        return this.value.getRed();
    }

    /**
     * Returns green RGB value.
     * @return int green.
     */
    public int getGreen() {

        return this.value.getGreen();
    }

    /**
     * Returns blue RGB value.
     * @return int blue.
     */
    public int getBlue() {

        return this.value.getBlue();
    }
}
