/**
 * @author Nicholas Garcia
 *
 */
public class CookingItem implements CookingItemInterface {
    
    /**The name of the item.*/
    private String name;
    /**The cooking time.*/
    private int cookingTime;
    /**The underdone penalty.*/
    private int underPen;
    /**The overdone penalty.*/
    private int overPen;
    
    /**The constructor for CookingItem.
     * 
     * @param n name
     * @param c cookingTime
     * @param u underdone penalty
     * @param o overdone penalty
     */
    public CookingItem(String n, int c, int u, int o) {
        this.name = n;
        this.cookingTime = c;
        this.underPen = u;
        this.overPen = o;
    }
    
    /**
     * Subtracts a minute from cooking time. 
     */
    public void tick() {
        this.cookingTime--;
    }

    /** 
     * @return timeRemaining the time left for cooking
     */
    public int timeRemaining() {
        return this.cookingTime;
    }

    /** 
     * @return penalty  gives the penalty for cooking 
     */
    public int penalty() {
        if (this.cookingTime < 0) {
            return (-(this.cookingTime) * this.overPen);
        } else if (this.cookingTime > 0) {
            return (this.cookingTime * this.underPen);
        }
        return 0;
    }
    /**Gets the description of the item.
     * @return String   the name and cooking time
     */
    public String toString() {
        String descrip = " (" + this.name + " " + this.cookingTime + ") ";
        return descrip;
    }

}
