import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
/** Binary Search Tree Map implementation with inner Node class.
 *  @param <K> the base type of the keys in the entries
 *  @param <V> the base type of the values
 */
public class BSTMap<K extends Comparable<? super K>, V>
    implements MapJHU<K, V>, Iterable<Map.Entry<K, V>> {
    /** Inner node class.  Do not make this static because you want
        the K to be the same K as in the BSTMap header.
    */
    private class BNode {
        /** The key of the entry (null if sentinel node). */
        private K key;
        /** The value of the entry (null if sentinel node). */
        private V value;
        /** The left child of this node. */
        private BNode left;
        /** The right child of this node. */
        private BNode right;
        /** Create a new node with a particular key and value.
         *  @param k the key for the new node
         *  @param v the value for the new node
         */
        BNode(K k, V v) {
            this.key = k;
            this.value = v;
            this.left = null;
            this.right = null;
        }
        /** Check whether this node is a leaf sentinel, based on key.
         *  @return true if leaf, false otherwise
         */
        public boolean isLeaf() {
            return this.key == null;  // this is a sentinel-based implementation
        }
        
        /**
         * Sets the left child of the node.
         * @param leftChild the node to make the left child
         */
        public void setLeft(BNode leftChild) {
            this.left = leftChild;
        }
        
        /**
         * Sets the right child of the node.
         * @param rightChild the node to make the right child
         */
        public void setRight(BNode rightChild) {
            this.right = rightChild;
        }
        
        /**
         * Set the key of a node.
         * @param nodeKey the key to set
         */
        public void setKey(K nodeKey) {
            this.key = nodeKey;
        }
        
        /**
         * Set the value of a node.
         * @param nodeValue the key to set
         */
        public void setValue(V nodeValue) {
            this.value = nodeValue;
        }
    }
    /** The root of this tree. */
    private BNode root;
    /** The number of entries in this map (== non-sentinel nodes). */
    private int size;
    /** Create an empty tree with a sentinel root node.
     */
    public BSTMap() {
        // empty tree is a sentinel for the root
        this.root = new BNode(null, null);
        this.size = 0;
    }
    @Override()
    public int size() {
        return this.size;
    }
    @Override()
    public void clear() {
        this.root = new BNode(null, null);
        this.size = 0;
    }
    @Override()
    public boolean isEmpty() {
        return this.size() == 0;
    }
    @Override()
    public boolean hasKey(K key) {
        return this.hasKey(key, this.root);
    }
    /** See if a key is in an entry in a subtree.
     *  @param key the key to search for
     *  @param curr the root of the subtree being searched
     *  @return true if found, false otherwise
     */
    public boolean hasKey(K key, BNode curr) {
        if (curr == null) {
            return false;           
        }
        if (key.compareTo(curr.key) < 0) {
            this.hasKey(key, curr.left);
        }
        if (key.compareTo(curr.key) > 0) {
            this.hasKey(key, curr.right);
        }
        return true;
    }
    
    @Override()
    public boolean hasValue(V value) {
        Iterable<Map.Entry<K, V>> allEntries =  this.inOrder(this.root);
        Iterator<Map.Entry<K, V>> it = allEntries.iterator();       

        while (it.hasNext()) {
            Map.Entry<K, V> curr = it.next();
            if (curr.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
    
    @Override()
    public V get(K key) {
        return this.get(key, this.root);
    }
    
    /** Get the value associated with key from subtree with given root node.
     *  @param key the key of the entry
     *  @param curr the root of the subtree from which to get the entry
     *  @return the value associated with the key, or null if not found
     */
    public V get(K key, BNode curr) {
        if (curr == null) {
            return null;            
        }
        if (key.compareTo(curr.key) < 0) {
            this.hasKey(key, curr.left);
        }
        if (key.compareTo(curr.key) > 0) {
            this.hasKey(key, curr.right);
        }
        return curr.value;
    }
    
    @Override()
    public V put(K key, V val) {
        if (this.hasKey(key)) {
            V old = this.get(key);
            BNode node = this.searchKey(key, this.root);
            node.setValue(val);
            return old;
        } else {
            this.put(key, val, this.root);
            return null;
        }
        
    }

    /** Put entry with key/value from subtree with given root node.
     *  @param key the key of the entry to put, if there
     *  @param val the value of the entry to put
     *  @param curr the root of the subtree from which to put the entry
     *  @return the node associated with the removed key, or null if not found
     */
    private V put(K key, V val, BNode curr) {
        //recursive method
        //if youve reached a leaf insert the key-value pair
        if (curr.isLeaf()) {
            curr.key = key;
            curr.value = val;
            return null;
        }
        
        //if you havent reached a leaf, go left or right
        if (!curr.isLeaf()) {
            if (key.compareTo(curr.key) < 0) {
                this.put(key, val, curr.left);
            }
            if (key.compareTo(curr.key) > 0) {
                this.put(key, val, curr.right);
            }
        }
        
        return null;
    }
    @Override()
    public V remove(K key) {
        if (!this.hasKey(key)) {
            return null;
        }
        BNode temp = this.remove(key, this.root);
        this.size--;                
        return temp.value;
    }
    
    /** Remove entry with specified key from subtree with given root node.
     *  @param key the key of the entry to remove, if there
     *  @param curr the root of the subtree from which to remove the entry
     *  @return the node associated with the removed key, or null if not found
     */
    public BNode remove(K key, BNode curr) {
        if (curr == null) {
            return null;
        }
        
        if (curr.key.compareTo(key) > 0) {
            curr.setLeft(this.remove(key, curr.left));
        } else if (curr.key.compareTo(key) < 0) {
            curr.setRight(this.remove(key, curr.right));
        } else {
            if (curr.left == null && curr.right == null) {
                return null;
            } else if (curr.left == null) {
                return curr.right;
            } else if (curr.right == null) {
                return curr.left;
            } else {
                BNode temp = this.maxNode(curr.left);
                curr.setKey(temp.key);
                curr.setValue(temp.value);
                curr.setLeft(this.remove(key, curr.left));
            }
        }
        return curr;
    }
    
    /**
     * Returns the node associated with a key if there.
     * @param key the key to search for
     * @param curr the root of the subtree 
     * @return the node if found, null otherwise 
     */
    public BNode searchKey(K key, BNode curr) {
        if (curr == null) {
            return null;           
        }
        if (key.compareTo(curr.key) < 0) {
            this.searchKey(key, curr.left);
        }
        if (key.compareTo(curr.key) > 0) {
            this.searchKey(key, curr.right);
        }
        return curr;
    }
    
    /** Get the smallest node in a subtree.
     *  @param curr the root of the subtree to search
     *  @return the min node
     */
    public BNode minNode(BNode curr) {
        if (curr == null) {
            return null;
        }
        while (!curr.isLeaf()) {
            curr = curr.left;
        }
        return curr;
    }
    
    /** Get the biggest node in a subtree.
     *  @param curr the root of the subtree to search
     *  @return the max node
     */
    public BNode maxNode(BNode curr) {
        if (curr == null) {
            return null;
        }
        while (!curr.isLeaf()) {
            curr = curr.right;
        }
        return curr;
    }
    
    @Override()
    public Set<Map.Entry<K, V>> entries() {
        BSTMapIterator mapIterator = new BSTMapIterator();
        Set<Map.Entry<K, V>> entriesSet = new HashSet<Map.Entry<K, V>>();
        
        while (mapIterator.hasNext()) {
            entriesSet.add(mapIterator.next());
        }
        
        return entriesSet;
    }
    @Override()
    public Set<K> keys() {
        BSTMapIterator mapIterator = new BSTMapIterator();
        Set<K> keySet = new HashSet<K>();
        
        while (mapIterator.hasNext()) {
            keySet.add(mapIterator.next().getKey());
        }       
        return keySet;
    }
    @Override()
    public Collection<V> values() {
        BSTMapIterator mapIterator = new BSTMapIterator();
        Collection<V> valueCollection = new ArrayList<V>();
        
        while (mapIterator.hasNext()) {
            valueCollection.add(mapIterator.next().getValue());
        }       
        return valueCollection;
    }
    /* -----   BSTMap-specific functions   ----- */
    /** Get the smallest key in a subtree.
     *  @param curr the root of the subtree to search
     *  @return the min key
     */
    public K firstKey(BNode curr) {
        if (curr == null) {
            return null;
        }
        while (!curr.isLeaf()) {
            curr = curr.left;
        }
        return curr.key;
    }
    /** Get the biggest key in a subtree.
     *  @param curr the root of the subtree to search
     *  @return the max key
     */
    public K lastKey(BNode curr) {
        if (curr == null) {
            return null;
        }
        while (!curr.isLeaf()) {
            curr = curr.right;
        }
        return curr.key;
    }
    
    /** Inorder traversal that produces an iterator over key-value pairs.
     *  @return an iterable list of entries ordered by keys
     */
    public Iterable<Map.Entry<K, V>> inOrder() {
        return this.inOrder(this.root);
    }
    
    /** Inorder traversal produces an iterator over entries in a subtree.
     *  @param curr the root of the subtree to iterate over
     *  @return an iterable list of entries ordered by keys
     */
    private Collection<Map.Entry<K, V>> inOrder(BNode curr) {
        LinkedList<Map.Entry<K, V>> ordered = new LinkedList<Map.Entry<K, V>>();
        if (curr.left != null) {
            ordered.addAll(this.inOrder(curr.left));
        }
        
        if (curr != null) {
            Map.Entry<K, V> entry = new 
                    AbstractMap.SimpleEntry<K, V>(curr.key, curr.value);
            ordered.addLast(entry);
        }
        
        if (curr.right != null) {
            ordered.addAll(this.inOrder(curr.right));
        }        
        return ordered;
    }
    
    /** Returns a copy of the portion of this map whose keys are in a range.
     *  @param fromKey the starting key of the range, inclusive if found
     *  @param toKey the ending key of the range, inclusive if found
     *  @return the resulting submap
     */
    public BSTMap<K, V> subMap(K fromKey, K toKey) { 
        Iterable<Map.Entry<K, V>> allEntries =  this.inOrder(this.root);
        Iterator<Map.Entry<K, V>> it = allEntries.iterator();       
        BSTMap<K, V> sub = new BSTMap<K, V>(); 
        
        while (it.hasNext()) {
            Map.Entry<K, V> curr = it.next();
            if (curr.getKey().compareTo(fromKey) >= 0) {
                if (curr.getKey().compareTo(toKey) <= 0) {
                    sub.put(curr.getKey(), curr.getValue());
                }
            }
        }
        return sub;
    }

    /* ---------- from Iterable ---------- */

    @Override
    public Iterator<Map.Entry<K, V>> iterator() 
            throws ConcurrentModificationException {
        return new BSTMapIterator();
    }

    @Override
    public void forEach(Consumer<? super Map.Entry<K, V>> action) {
        // you do not have to implement this
    }

    @Override
    public Spliterator<Map.Entry<K, V>> spliterator() {
        // you do not have to implement this
        return null;
    }

    /* -----  insert the BSTMapIterator inner class here ----- */
    /**The inner iterator class for the BSTMap.*/
    private final class BSTMapIterator implements Iterator<Map.Entry<K, V>> {
        /**The iterator for BSTMap iterator.*/
        private Iterator<Map.Entry<K, V>> mapIt;
        /**Constructor.*/
        private BSTMapIterator() {
            this.mapIt = 
                    BSTMap.this.inOrder().iterator();
            
        }
        
        @Override
        public boolean hasNext() {
            if (this.mapIt.hasNext()) {    
                return true;
            }
            return false;
        }

        @Override
        public Entry<K, V> next() {
            this.mapIt.next();
            return null;
        }
        
        @Override
        public void remove() {
            
        } 
    }
}