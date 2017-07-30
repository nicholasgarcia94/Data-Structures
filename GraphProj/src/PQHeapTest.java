import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertFalse;
import org.junit.Test;
/**
 * JUnit tests for PQHeap.java.
 * Nicholas Garcia, ngarcia5
 *  Ashwin Bhat, abhat4
 *  Lousanna Cai, lcai10
 *  Shain Bannowsky, sbannow1
 *  Data Structures 600.226.02
 *  Project 4
 *
 */
public class PQHeapTest {
    
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
            return t2.compareTo(t1);
        }
    }
    
    /**String array.*/
    static String[] sArray = {"a", "b", "c", "d", "e"};
    /**Integer array.*/
    Integer[] iArray = {1, 2, 3, 4, 5};
    /**Integer priority queue.*/
    PQHeap<Integer> iPQ = new PQHeap<Integer>();
    /**String priority queue.*/
    PQHeap<String> sPQ = new PQHeap<String>();
    /**The maxPQHeap comparator.*/
    DefaultComparator<String> comparator = new DefaultComparator<String>();
    /**The maxPQHeap.*/
    PQHeap<String> maxPQ = new PQHeap<String>(2, this.comparator);
    
    
    /**
     * Tests if the size when empty is 0.
     */
    @Test
    public void testSizeOnEmpty() {
        assertEquals("The size of integer PQ is: ",
                0, this.iPQ.size());
        assertEquals("The size of string PQ is: ",
                0, this.sPQ.size());
    }
    
    /**
     * Tests if isEmpty returns true on empty heap. 
     */
    @Test
    public void testIsEmptyOnEmpty() {
        assertTrue("The integer PQHeap is empty: ", 
                this.iPQ.isEmpty());
        assertTrue("The string PQHeap is empty: ", 
                this.sPQ.isEmpty());
    }

    /**
     * Tests clear on empty pqHeap, makes sure its still empty.
     */
    @Test
    public void testClearOnEmpty() {
        this.iPQ.clear();
        assertTrue("Clear on empty is empty: ",
                this.iPQ.isEmpty());
        this.sPQ.clear();
        assertTrue("Clear on empty is empty: ",
                this.sPQ.isEmpty());
    }
    
    /**
     * Makes sure QueueEmptyException is thrown when remove
     * is called on an empty PQHeap.
     */
    @Test (expected = QueueEmptyException.class)
    public void testRemoveMaxOnEmpty() {
        this.iPQ.remove();
        this.sPQ.remove();
    }

    /**
     * Makes sure QueueEmptyException is thrown when peek
     * is called on an empty PQHeap.
     */
    @Test (expected = QueueEmptyException.class)
    public void testPeekOnEmpty() {
        this.iPQ.peek();
        this.sPQ.peek();
    }
    
    /**
     * Tests the isEmpty function on a non-empty PQHeap.
     */
    @Test
    public void testIsEmptyOnNonEmpty() {
        this.iPQ.insert(1);
        assertFalse("The integer PQHeap is empty: ",
                this.iPQ.isEmpty());
        this.sPQ.insert("a");
        assertFalse("The string PQHeap is empty: ",
                this.iPQ.isEmpty());
    }
    
    /**
     * Tests insert and size by calling insert and checking size is
     * one afterwards. 
     */
    @Test
    public void testInsertAndSize() {
        this.iPQ.insert(1);
        assertEquals("Integer PQHeap size is: ",
                1, this.iPQ.size());
    }
    
    /**
     * Tests the peek method.
     */
    @Test
    public void testPeekOnNonEmpty() {
        
        this.iPQ.insert(5);
        assertEquals(this.iArray[4], this.iPQ.peek());
        this.iPQ.insert(4);
        assertEquals(this.iArray[3], this.iPQ.peek());
        this.iPQ.insert(3);
        assertEquals(this.iArray[2], this.iPQ.peek());
        this.iPQ.insert(2);
        assertEquals(this.iArray[1], this.iPQ.peek());
        this.iPQ.insert(1);
        assertEquals(this.iArray[0], this.iPQ.peek());
       
        
        this.sPQ.insert("e");
        assertEquals("e", this.sPQ.peek());
        this.sPQ.insert("d");
        assertEquals("d", this.sPQ.peek());
        this.sPQ.insert("c");
        assertEquals("c", this.sPQ.peek());
        this.sPQ.insert("b");
        assertEquals("b", this.sPQ.peek());
        this.sPQ.insert("a");
        assertEquals("a", this.sPQ.peek());        
        assertEquals(5, this.sPQ.size());

    }
    
    /**
     * Tests the remove method.
     */
    @Test
    public void testRemoveAndClearOnNonEmpty() {
        
        this.iPQ.insert(1);
        this.iPQ.insert(2);
        this.iPQ.insert(3);
        this.iPQ.insert(4);
        this.iPQ.insert(5);
        
        assertEquals(5, this.iPQ.size());
        assertEquals(this.iArray[0], this.iPQ.peek());
        assertEquals(this.iArray[0], this.iPQ.remove());
        assertEquals(4, this.iPQ.size());
        assertEquals(this.iArray[1], this.iPQ.remove());
        assertTrue(this.iPQ.size() == 3);
        this.iPQ.clear();
        assertEquals(0, this.iPQ.size());
        
        this.sPQ.insert("a");
        this.sPQ.insert("b");
        this.sPQ.insert("c");
        this.sPQ.insert("d");
        this.sPQ.insert("e");
        
        assertEquals(sArray.length, this.sPQ.size());
        assertEquals(sArray[0], this.sPQ.peek());
        assertEquals(sArray[0], this.sPQ.remove());
        assertEquals(sArray.length - 1, this.sPQ.size());
        assertEquals(sArray[1], this.sPQ.remove());
        assertEquals(sArray.length - 2, this.sPQ.size());
        
    }


    /**
     * Tests the init method.
     */
    @Test
    public void testInitThenClear() {        
        ArrayList<Integer> intList = new ArrayList<Integer>();
        for (int i = 0; i < this.iArray.length; i++) {
            intList.add(this.iArray[i]);
        }
        
        this.iPQ.init(intList);        
        assertEquals(intList.size(), this.iPQ.size());
        assertEquals((Integer) 1, this.iPQ.remove());
        assertTrue(this.iPQ.size() == intList.size() - 1);
        
        //now make sure clear works
        this.iPQ.clear();
        assertEquals(this.iPQ.size(), 0);
        assertTrue(this.iPQ.isEmpty());
        
        ArrayList<String> slist = new ArrayList<String>();
        for (int i = 0; i < sArray.length; i++) {
            slist.add(sArray[i]);
        }
        
        //Test init
        this.sPQ.init(slist);
        assertEquals(slist.size(), this.sPQ.size());
        assertEquals(sArray[0], this.sPQ.remove());
        assertEquals(slist.size() - 1, this.sPQ.size());
        
        //Test clear after init.
        this.sPQ.clear();
        assertEquals(0, this.sPQ.size());
        assertTrue(this.sPQ.isEmpty());
 
    }
    
    /**
     * Tests the peek method.
     */
    @Test
    public void testPeekOnMaxPQ() {
        
        this.maxPQ.insert("a");
        assertEquals("a", this.maxPQ.peek());
        this.maxPQ.insert("b");
        assertEquals("b", this.maxPQ.peek());
        this.maxPQ.insert("c");
        assertEquals("c", this.maxPQ.peek());
        this.maxPQ.insert("d");
        assertEquals("d", this.maxPQ.peek());
        this.maxPQ.insert("e");
        assertEquals("e", this.maxPQ.peek());       
        assertEquals(5, this.maxPQ.size());
        
    }
    
    /**
     * Tests the remove method.
     */
    @Test
    public void testRemoveAndClearOnMaxPQ() {
        
        this.maxPQ.insert("a");
        this.maxPQ.insert("b");
        this.maxPQ.insert("c");
        this.maxPQ.insert("d");
        this.maxPQ.insert("e");
        
        assertEquals(sArray.length, this.maxPQ.size());
        assertEquals(sArray[4], this.maxPQ.peek());
        assertEquals(sArray[4], this.maxPQ.remove());
        assertEquals(sArray.length - 1, this.maxPQ.size());
        assertEquals(sArray[3], this.maxPQ.remove());
        assertEquals(sArray.length - 2, this.maxPQ.size());
    }
    
    /**
     * Tests the PQHeap iterator.
     */
    @Test
    public void testIterator() {
        this.sPQ.insert("a");
        this.sPQ.insert("b");
        this.sPQ.insert("c");
        this.sPQ.insert("d");
        this.sPQ.insert("e");

        
        Iterator<String> iter = this.sPQ.iterator();
        assertEquals("a", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("b", iter.next());
        assertEquals("c", iter.next());
        assertEquals("d", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("e", iter.next());
        assertFalse(iter.hasNext());
    }
    
    /**
     * Tests PQHeap iterator when empty.
     */
    @Test (expected = NoSuchElementException.class)
    public void testIteratorOnEmpty() {
        Iterator<String> iter = this.sPQ.iterator();
        iter.next();
    }
    
    
    
    
    
}

