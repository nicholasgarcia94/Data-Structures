/**
 * Interface for a List data structure, based on the ADT from OpenDSA.
 * 
 * 2/13: updated to include more specifications relative to positions.
 * This provides a more consistent behavior than what the text
 * implements.  If the list is empty, the current position is 0,
 * otherwise the only valid positions are [0 to length()-1] inclusive.
 * [This differs from the text which considers the end of the list to
 * be a valid position of length().]
 *
 * @param <T> the type of the List
 */
public interface List<T> {

    /*
    NOTE THAT WE DON'T HAVE TO INCLUDE THE PUBLIC ACCESS MODIFIER
    SINCE ALL METHODS IN AN INTERFACE ARE PUBLIC BY DEFAULT.
    CHECKSTYLE WANTS US TO USE DEFAULT PERMISSIONS.
    */
    
    /**
     * Remove all contents from the list, so it is once again empty.
     * Cursor should be at position 0.
     */
    void clear();

    /**
     * Insert a value at the current position. The cursor should
     * remain at that position, meaning that the new value is the
     * current one (would be returned by getValue).  The client must
     * ensure that the list's capacity is not exceeded.
     * @param t the value to insert
     * @return true if successfully inserted, false otherwise
     */
    boolean insert(T t);

    /**
     * Append a value at the end of the list. Cursor does not move.
     * The client must ensure that the list's capacity is not exceeded.
     * @param t the value to append
     * @return true if successfully appended, false otherwise
     */
    boolean append(T t);

    /**
     * Remove and return the current element. Cursor does not move,
     * unless the last element was removed.  In that case the cursor
     * should be reset to the beginning of the list.
     * @return the value of the element removed, or null if there was
     * no current element.
     */
    T remove();

    /**
     * Set the current position to the start of the list (position 0).
     */
    void moveToStart();

    /**
     * Set the current position to the end of the list (position
     * length()-1 where the last node is).
     */
    void moveToEnd();

    /**
     * Move the current position one step left, no change if already
     * at beginning.
     */
    void prev();

    /**
     * Move the current position one step right, no change if already
     * at end (last node).
     */
    void next();

    /**
     * Return the number of elements in the list.
     * @return the length of the list
     */
    int length();

    /**
     * Return the position of the current element. (1st element is at
     * position 0, 2nd at position 1, etc.)
     * @return the current position in the list
     */
    int currPos();

    /**
     * Set the current position.
     * @param pos the value to set the position to, 0 through
     * length()-1 inclusive are valid values
     * @return true if successfully changed position, false otherwise
     */
    boolean moveToPos(int pos);

    /**
     * Return true if current position is at the end of the list,
     * meaning at the last node.
     * @return true if the current position is the last node, false
     * otherwise
     */
    boolean isAtEnd();

    /**
     * Return the data in the current element.
     * @return the value of the current element, null if there isn't one.
     */
    T getValue();
}