/**
 * Name: Ashwin Bhat, abhat4.
 * Name: Nicholas Garcia, ngarcia5.
 * 
 * 
 * Doubly Linked List implementation of the List interface.
 * Uses sentinel nodes at the head & tail, and an inner Node class.
 * This is only a partial implementation, loosely based on OpenDSA version.
 *
 * This version differs notably from the DSA version in that the curr 
 * data member refers to the node *before* the cursor, whereas in OpenDSA
 * the current data member refers to the node *after* the cursor.
 *
 * @author Joanne Selinski
 * @param <T> the type of the List
 */
public class CList<T> implements List<T> {
    /**
     * Inner doubly linked Node class for convenience.
     * Note that the generic type is implied since we are within DLList<T>.
     */
    public class Node {
        /** The data in the element. */
        private T data;
        /** The left neighbor node. */
        private Node prev;
        /** The right neighbor node. */
        private Node next;
        /**
         * Make a node.
         * @param item the data to put in it
         * @param p the link to the previous node
         * @param n the link to the next node
         */
        public Node(T item, Node p, Node n) {
            this.data = item;
            this.prev = p;
            this.next = n;
        }
    }
    /** Head sentinel node. */
    private Node head;
    /** Tail sentinel node. */
    private Node tail;
    /** Number of actual data nodes in list. */
    private int size;
    /** Current node (think of as a cursor between nodes). */
    private Node curr;
    
    /**
     * Create an empty list with sentinels.
     */
    public CList() {
        this.clear();  // code reuse!
    }
    
    /**
     * Remove all contents from the list, so it is once again empty.
     */
    public void clear() {
        this.size = 0;
        this.head = new Node(null, null, null);
        this.tail = new Node(null, this.head, this.head);
        this.head.next = this.tail;
        this.head.prev = this.tail;
        this.moveToStart();        // because insert will insert after curr
    }
    /**
     * Insert a value at (after) the current location.
     * The client must ensure that the list's capacity is not exceeded.
     * @param t the value to insert
     * @return true if successfully inserted, false otherwise
     */
    public boolean insert(T t) {
        
        //ensure that the list capacity is not exceeded
        //not sure what this means 
        
        //inserting on an empty list
       //insert data into head 
        if (this.size == 0) {
            this.head.data = t;
            this.size++;
            return true; 
        }
        //inserting when size = 1
        if (this.size == 1) {
            this.tail.data = this.head.data;
            this.head.data = t;
            this.curr = this.head;
            this.size++;
            return true; 
        }
        //inserting in the middle 
        //code reuse from DLList 
        // Alternate if (this.curr != this.head && this.curr != this.tail) 
        if (this.currPos() > 0 && this.currPos() < (this.length() - 1)) {
            //Here is the tricky part. 
            //It crashes if we have (t, this.curr.prev, this.curr)
            Node n = new Node(t, this.curr.prev, this.curr);
            n.prev.next = n;   // connect left neighbor
            n.next.prev = n;   // connect right neighbor
            this.curr = n;
            this.size++;
            return true;
        } 
        // inserting at the tail 
        if (this.curr == this.tail) {
            Node n = new Node(t, this.curr.prev, this.curr);
            n.prev.next = n;
            n.next.prev = n;
            this.curr = n;
            this.size++;
            return true; 
        }
        //inserting at the head 
        //Alternate if (this.curr == this.head) 
        if (this.currPos() == 0) {
            Node n = new Node(t, this.curr.prev, this.curr);
            n.prev.next = n;
            n.next.prev = n;
            this.head = n;
            this.curr = this.head; 
            this.size++;
            return true; 
        }
        return true; 
    }
    
    /**
     * Append a value at the end of the list.
     * The client must ensure that the list's capacity is not exceeded.
     * @param t the value to append
     * @return true if successfully appended, false otherwise
     */
    public boolean append(T t) {
        if (this.size == 0) {
            this.tail.data = t;
            this.size++;
            return true;
        } else if (this.size == 1) {
            this.head.data = this.tail.data;
            this.tail.data = t;
            this.size++;
            return true;
        } else {
            Node n = new Node(t, this.tail, this.tail.next);
            n.prev.next = n;
            n.next.prev = n;         
            this.tail = n;
            this.size++;
        }           
        return true;
    }
    /**
     * Remove and return the current element (one to right of cursor). 
     * @return the value of the element removed, null if list is empty
     */
    public T remove() {
        if (this.size == 0) {
            return null;
        } else if (this.size == 1) {
            T val = this.head.data;
            this.head.data = null;
            this.size--;
            return val;        
        } else if (this.size == 2) {
            if (this.curr == this.tail) {
                T val = this.tail.data;
                this.tail.data = null;
                this.size--;
                return val;
            } else if (this.curr == this.head) {
                T val = this.head.data;
                this.head.data = this.tail.data;
                this.tail.data = null;
                this.size--;
                return val;
            } 
        } 
        if (this.currPos() == 0) {
            T val = this.curr.data;
            this.head = this.head.next;
            this.curr = this.head;
            this.tail.next = this.head;
            this.head.prev = this.tail;
            this.size--;
            return val;
        }
        T val = this.curr.data;
        this.curr = this.curr.next;
        this.curr.prev.prev.next = this.curr;
        this.curr.prev = this.curr.prev.prev;
        this.size--;
        return val;
    }
    /**
     * Return the current element (data to right of cursor).
     * @return the value of the current element, null if none
     */
    public T getValue() {
        if (this.size == 0) {
            return null;
        }
        return this.curr.data;
    }
    /**
     * Return the number of elements in the list.
     * @return the length of the list
     */
    public int length() {
        return this.size;
    }
    /* ---------- METHODS BELOW THIS LINE ARE NOT IMPLEMENTED ------------ */
    /**
     * Set the current position to the start of the list.
     */
    public void moveToStart() {
        this.curr = this.head;
    }
    /**
     * Set the current position to the end of the list.
     */
    public void moveToEnd() {   
        if (this.size >= 1) {
            this.curr = this.tail;
        }
    }
    /**
     * Move the current position one step left,
     * no change if already at beginning.
     */
    public void prev() {
        if (this.curr != this.head) {
            this.curr = this.curr.prev;
        }
    }
    /**
     * Move the current position one step right, no change if already at end.
     */
    public void next() {    
        if (this.curr != this.tail && this.size > 0) {
            this.curr = this.curr.next;
        }
    }
    /**
     * Return the position of the current element.
     * @return the current position in the list
     */
    public int currPos() {
        Node temp = this.curr;
        this.moveToStart();
        int count = 0;
        while (this.curr != temp) {
            this.curr = this.curr.next;
            count++;
        }
        return count;
    }
    /**
     * Set the current position.
     * @param pos the value to set the position to
     * @return true if successfully changed position, false otherwise
     */
    public boolean moveToPos(int pos) {
        if (pos >= this.length() || pos < 0) {
            System.out.println(this.currPos());
            return false;
        }
        
        while (this.currPos() != pos) {
            if (this.curr == this.tail) {
                this.curr = this.head;
            } else {
                this.next();
            }
        }
        return true;
    }
      
    /**
     * Return true if current position is at end of the list.
     * @return true if the current position is the end of the list
     */
    public boolean isAtEnd() {
        return this.currPos() == (this.length() - 1);
    }
    /**
     * A toString method. 
     * @return String a string of the stuff
     */ 
    public String toString() {
        String printOut = "[";
        Node temp = this.curr;
        this.moveToStart();
        while (this.curr != this.tail) {
            printOut += (" " + this.toString());
            this.next();
        }
        printOut += (" " + this.tail.data + " ]");
        this.curr = temp;
        return printOut;
    }
    
    /**
     * Set the data of the current element (data to right of cursor).
     * @param t the data to be set on curr node
     */
    public void setData(T t) {
        this.curr.data = t;
    }
}