/**
 * @author Nicholas Garcia  JHED: ngarcia5
 * @author Ashwin Bhat  JHED: abhat4
 * Project 1. 
 * Interface for the CookingItem Class.
 */
public interface CookingItemInterface {
    /** Implements a simulation of one minute of time for this item by
     *  decrementing cooking time by one minute.
     */
    void tick(); 

    /** Get the time remaining for cooking this dish.
     *  @return the time in minutes
     */
    int timeRemaining();

    /** Calculate the penalty if this dish were removed now.
     *  @return the penalty
     */
    int penalty(); 
    
}