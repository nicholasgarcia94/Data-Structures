/**
 * @author Ashwin Bhat  JHED: abhat4
 * @author Nicholas Garcia  JHED: ngarcia5
 * The cutthroat kitchen class for CutthroatKitchen sim.  
 */
public class CutthroatKitchen extends CList<CookingStation> {
    /**The total penalties of all stations.*/
    private int totalPenalty;
    
    /** Put a new station at the end of the kitchen.
     *  @param stat the station to add
     */
    public void addStation(CookingStation stat) {
        this.append(stat);
    }
    /** Simulate one minute time passing for all stations.
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
     * Gets number of items in kitchen.
     * @return int the number of items
     */
    
    
    /**
     * Calculates number of penalties in kitchen.
     * @return int the number of penalties
     */
    public int calculatePenalty() {
        for (int i = 0; i < this.length(); i++) {
            this.totalPenalty += this.getValue().getPenalty();
        }
        return this.totalPenalty;
    }
    
    /**
     * The toString method to obtain a 
     * string of the whole kitchen.
     * @return string description
     */
    public String toString() {
        String descrip = "[ ";
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


