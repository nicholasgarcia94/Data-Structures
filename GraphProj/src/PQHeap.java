import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * PQHeap class.
 *  Nicholas Garcia, ngarcia5
 *  Ashwin Bhat, abhat4
 *  Lousanna Cai, lcai10
 *  Shain Bannowsky, sbannow1
 *  Data Structures 600.226.02
 *  Project 4
 *
 * @param <T> generic type
 */
public class PQHeap<T extends Comparable<? super T>>
    implements PriorityQueue<T> {
    
    /**The starting capacity if unspecified.*/
    private static final int STARTING_CAP = 10;
    /**The number of items in the priority queue.*/
    private int numItems;
    /**The priority queue.*/
    private T[] pq;
    /**The comparator to be used in ordering items.*/
    private Comparator<? super T> comparator;
    /**The capacity of the PQHeap for internal expanding purposes.*/
    private int capacity;
    
    /**Default constructor.*/
    public PQHeap() {
        this(STARTING_CAP, new DefaultComparator<T>());
    }
    
    /**Constructor with specified size.
     * @param startCap starting capacity
     */
    public PQHeap(int startCap) {
        this(startCap, new DefaultComparator<T>());
    }
    /**Constructor with specified comparator.
     * @param comp comparator used for ordering
     */
    public PQHeap(Comparator<T> comp) {
        this(STARTING_CAP, comp);
    }
    /**Constructor with specified comparator.
     * @param comp comparator used for ordering
     * @param startCap the starting size
     */
    @SuppressWarnings("unchecked")
    public PQHeap(int startCap, Comparator<T> comp) {
        this.comparator = comp;
        this.capacity = startCap;
        this.pq = (T[]) new Comparable[startCap];
        this.numItems = 0;
    }
    
    
    
    @Override
    public void insert(T t) {
        if (this.numItems < this.capacity - 1) {
            this.pq[++this.numItems] = t;
            this.siftUp(this.numItems);
        } else {
            this.expand(this.capacity * 2);
            this.pq[++this.numItems] = t;
            this.siftUp(this.numItems);
        }
    }

    /**
     * Inserts t at proper position based on numItems and shifts around.
     * @param i the number of items
     */
    public void siftUp(int i) {
        while (i > 1 
                && (this.comparator.compare(this.pq[(i / 2)],
                        this.pq[i]) > 0)) {
            this.swap(i, (i / 2));
            i = i / 2;
        }
    }

    @Override
    public T remove() throws QueueEmptyException {
        if (this.numItems == 0) {
            throw new QueueEmptyException("Can't remove from empty PQ.");
        }
        this.swap(1, this.numItems);
        T temp = this.pq[this.numItems];
        this.numItems--;
        this.siftDown(1);
        this.pq[this.numItems + 1] = null;
        return temp;
    }
    /**
     * Moves items around to obtain proper positioning.
     * @param i position
     */
    public void siftDown(int i) {
        while (2 * i <= this.numItems) {
            int j = 2 * i;
            if (i < this.numItems 
                    && this.comparator.compare(this.pq[j], 
                            this.pq[j + 1]) > 0) {
                j++;
            }
            if (this.comparator.compare(this.pq[i], this.pq[j]) < 0) {
                break;
            }
            this.swap(i, j);
            i = j;
        }
    }

    @Override
    public T peek() throws QueueEmptyException {
        if (this.numItems == 0) {
            throw new QueueEmptyException("Nothing to look at in this PQ.");
        } else {
            return this.pq[1];
        }
    }

    @Override
    public boolean isEmpty() {
        return (this.numItems == 0);
    }

    @Override
    public int size() {
        return this.numItems;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        this.pq = (T[]) new Comparable[this.capacity];
        this.numItems = 0;
    }

    @Override
    public void init(Collection<T> values) throws NullPointerException {
        Iterator<T> it = values.iterator();
        if (values.size() == 0 || it == null) {
            throw new NullPointerException();
        }
        this.clear();
        this.comparator = new DefaultComparator<T>();
        while (it.hasNext()) {
            this.insert(it.next());
        }
    }
    
    /**
     * Expands underlying array of the PQ.
     * @param cap the new capacity desired
     */
    public void expand(int cap) {
        this.pq = Arrays.copyOf(this.pq, cap);
        this.capacity = cap;
    }
    
    /**
     * Swaps two elements. 
     * @param t1 first element index
     * @param t2  second element index
     */
    public void swap(int t1, int t2) {
        T temp = this.pq[t1];
        this.pq[t1] = this.pq[t2];
        this.pq[t2] = temp;
    }
    

    /**Iterator, pretty self-explanatory.
     * @return iterator
     */
    public Iterator<T> iterator() {
        Iterator<T> iter;
        ArrayList<T> list = new ArrayList<T>();
        for (int i = 1; i <= this.numItems; i++) {
            list.add(this.pq[i]);
        }
        iter = new HeapIterator(list);
        return iter;
    }
    
    /**
     * Internal class for default comparator.
     * @author Joanne Selinski
     * @param <T>
     */
    private static class DefaultComparator<T extends Comparable<? super T>> 
        implements Comparator<T> {
        /**The compare to method.
         * @param t1 the first element
         * @param t2 the second element
         * @return int the result of comparing the two elements
         */
        public int compare(T t1, T t2) {
            return t1.compareTo(t2);
        }
    }
    
    // Internal Class
    /** Innter iterator class. */
    private class HeapIterator implements Iterator<T> {
        
        /**The iterator used for this class.*/
        private Iterator<T> iter;
        /**The HeapIterator constructor.
         * @param list the list of elements 
         */
        HeapIterator(ArrayList<T> list) {
            this.iter = list.iterator();
        }
        
        /** Overriding hasNext().
         *  @return true if next entry is available
         */
        @Override
        public boolean hasNext() {
            return this.iter.hasNext();
        }

        /** Overriding next().
         *  @return next entry
         */
        @Override
        public T next() {
            return this.iter.next();
        }

        /** Dummy remove() method.
         */
        @Override
        public void remove() {
            // Nothing!
        }

        
    }
 
}
