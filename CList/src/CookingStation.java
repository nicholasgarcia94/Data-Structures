/**
 * @author Nicholas Garcia  JHED: ngarcia5
 * @author Ashwin Bhat JHED: abhat4
 *
 */
public class CookingStation extends CList<CookingItem> 
                            implements CookingStationInterface {
    
    /**Station name.*/
    private String name;
    /**The total penalty at this station.*/
    private int stationPenalty;
    
    /**The constructor for a cooking station.
     * @param n the name of the station
     */
    public CookingStation(String n) {
        this.name = n;
        this.stationPenalty = 0;
    }
    
    
    /** Put a new dish at the end of the station.
     *  @param it the dish to add
     */
    public void addItem(CookingItem it) {
        this.append(it);
    }

    /** Simulate one minute time passing for this station.
     */
    public void tick() {
        int curr = this.currPos();
        this.moveToStart();
        for (int i = 0; i < this.length(); i++) {
            this.moveToPos(i);
            this.getValue().tick();
            this.next();
        }
        this.moveToPos(curr);
    }

    /** 
     * Tend the current item.
     *  @param removeThreshold the number of minutes that may be used to 
     *            determine if an item should be removed from the station.
     *  @param penaltyThreshold the limit on the penalty value that may be
               used to determine if an item should be removed from the station.
     *  @return the item if you decide to remove it, or null otherwise
     */
    public CookingItem tend(int removeThreshold, int penaltyThreshold) {
        if (this.getValue().timeRemaining() > removeThreshold) {
            if (this.isAtEnd()) {
                this.moveToStart();
            } else {
                this.next();
            }
            return null;
        } else if (this.getValue().timeRemaining() == 0) {
            return this.remove();
        } else if (this.getValue().timeRemaining() <= removeThreshold) {
            if (penaltyThreshold >= this.getValue().penalty()) {
                this.stationPenalty += this.getValue().penalty();
                return this.remove();
            } else {
                this.stationPenalty += penaltyThreshold;
                return this.remove();
            }
        }
        
        return null;
    }
    /**Gets the penalty.
     * @return int the penalties
     */
    public int getPenalty() {
        return this.stationPenalty;
    }
    
    /**
     * The toString method to obtain a 
     * string of the whole station.
     * @return string description
     */
    public String toString() {
        String descrip = this.name + " [";
        int curr = this.currPos();
        this.moveToStart();
        for (int i = 0; i < this.length(); i++) {
            this.moveToPos(i);
            descrip += (this.getValue().toString());
            this.next();
        }
        this.moveToPos(curr);
        descrip += "] ";
        return descrip;
    }
}
