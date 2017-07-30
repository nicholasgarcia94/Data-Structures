/**
 * Calculates the distance between two pixels.
 * Nicholas Garcia, ngarcia5
 *  Ashwin Bhat, abhat4
 *  Lousanna Cai, lcai10
 *  Shain Bannowsky, sbannow1
 *  Data Structures 600.226.02
 *  Project 4
 *
 */
public class PixelDistance implements Distance<Pixel> {
    /**The max value for RGB.*/
    static final int RGB = 255;
    /**Used to get the alpha bits.*/
    static final int ALPHA = 24;
    /**Used to get the red bits.*/
    static final int RED = 16;
    /**Used to get the green bits.*/
    static final int GREEN = 8;

    
    @Override
    public double distance(Pixel one, Pixel two) {
        int oneARGB = one.value().getRGB();
        int oneAlpha = (oneARGB >>  ALPHA) & RGB;
        int oneRed = one.getRed();
        int oneGreen = one.getGreen();
        int oneBlue = one.getBlue();
        
        int twoARGB = two.value().getRGB();
        int twoAlpha = (twoARGB >>  ALPHA) & RGB;
        int twoRed = two.getRed();
        int twoGreen = two.getGreen();
        int twoBlue = two.getBlue();
        
        double diffAlpha = (double) oneAlpha - twoAlpha;
        double diffRed = (double) oneRed - twoRed;
        double diffGreen = (double) oneGreen - twoGreen;
        double diffBlue = (double) oneBlue - twoBlue;
        
        double dist = Math.pow(diffAlpha, 2) + Math.pow(diffRed, 2) 
            + Math.pow(diffGreen, 2) + Math.pow(diffBlue, 2);
        
        return dist;
    }

}
