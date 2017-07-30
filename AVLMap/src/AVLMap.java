import java.util.AbstractMap;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
//import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.Set;
import java.util.Spliterator;

/**
 * The AVLMap class that was modified from the BSTMap implementation.
 * @author Ashwin Bhat, abhat4
 * @author Nicholas Garcia, ngarcia5
 * @param <K>
 * @param <V>
 */
public class AVLMap<K extends Comparable<? super K>, V>
    implements MapJHU<K, V>, Iterable<Map.Entry<K, V>> {
    
    
    /** Inner node class.  Do not make this static because you want
     * the K to be the same K as in the BSTMap header.
     */
    protected class BNode {
    
        /** The key of the entry (null if sentinel node). */
        protected K key;
        /** The value of the entry (null if sentinel node). */
        protected V value;
        /** The left child of this node. */
        protected BNode left;
        /** The right child of this node. */
        protected BNode right;
        /** THe height of this node.*/
        protected int height;
      
    
        /** Create a new node with a particular key and value.
         *  @param k the key for the new node
         *  @param v the value for the new node
         */
        BNode(K k, V v) {
            this.key = k;
            this.value = v;
            this.left = null;
            this.right = null;
            this.height = 0;
        }
    
        /** Check whether this node is a leaf sentinel, based on key.
         *  @return true if leaf, false otherwise
         */
        public boolean isLeaf() {
            return this.key == null;  // this is a sentinel-based implementation
        }        
    }
    
    /** The root of this tree. */
    protected BNode root;
    /** The number of entries in this map (== non-sentinel nodes). */
    protected int size;
    /** The state of the map. */
    protected int state;

    /** 
     * Create an empty tree with a sentinel root node.
     */
    public AVLMap() {
        this.state = -1;
        this.clear();
    }
    
    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.root = new BNode(null, null);
        this.size = 0;
        this.state++;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /** Find a key in a particular subtree.
     *  @param key the key to find
     *  @param curr the root of the tree to search
     *  @return the node containing the key, or a sentinel leaf if not found
     */
    protected BNode findKey(K key, BNode curr) {
        if (curr.isLeaf()) {
            return curr; //curr has key of null
        }
        int comparison = key.compareTo(curr.key);
        if (comparison < 0) {
            return this.findKey(key, curr.left);
        } else if (comparison > 0) {
            return this.findKey(key, curr.right);
        } else {
            return curr;
        }
    }
    
    @Override
    public boolean hasKey(K key) {
        return this.hasKey(key, this.root);
    }
    
    /** See if a key is in an entry in a subtree.
     *  @param key the key to search for
     *  @param curr the root of the subtree being searched
     *  @return true if found, false otherwise
     */
    public boolean hasKey(K key, BNode curr) {
        return !this.findKey(key, curr).isLeaf();
    }

    @Override
    public boolean hasValue(V value) {
        return this.values().contains(value);
    }

    @Override
    public V get(K key) {
        return this.get(key, this.root);
    }
    
    /** Get the value associated with key from subtree with given root node.
     *  @param key the key of the entry
     *  @param curr the root of the subtree from which to get the entry
     *  @return the value associated with the key, or null if not found
     */
    public V get(K key, BNode curr) {
        return this.findKey(key, curr).value;
    }
    
    /**
     * Finds the minimum node in the subtree.
     * @param curr the root of the subtree
     * @return the smallest key'ed node. 
     */
    private BNode findMin(BNode curr) {
        if (curr.isLeaf()) {
            return curr;
        }
        while (!curr.left.isLeaf()) {
            curr = curr.left;
        }
        return curr;
    }
            
    /**
     * Finds the maximum node in the subtree.
     * @param curr the root of the subtree
     * @return the node with the largest key
     */
    private BNode findMax(BNode curr) {
        if (curr.isLeaf()) {
            return curr;
        }
        while (!curr.right.isLeaf()) {
            curr = curr.right;
        }
        return curr;
    }
    
    @Override
    public V put(K key, V value) {
        BNode temp = this.findKey(key, this.root);
        this.root = this.put(key,  value, this.root);
        return temp.value;
    }
    
    /**
     * Put the key/value entry into subtree with given root. 
     * @param key the key 
     * @param val the value
     * @param curr the root of the subtree given
     * @return returns the new node 
     */
    private BNode put(K key, V val, BNode curr) {
        if (curr.isLeaf()) {
            curr.key = key;
            curr.value = val;
            curr.left = new BNode(null, null);
            curr.right = new BNode(null, null);
            this.size++;
            this.state++;
            return curr;
        }
        int compare = key.compareTo(curr.key);
        if (compare < 0) {
            curr.left = this.put(key, val, curr.left);
        } else if (compare > 0) {
            curr.right = this.put(key, val, curr.right);
        } else {
            curr.value = val;
        }
        return this.rebalance(curr);
    }

    @Override
    public V remove(K key) {
        BNode temp = this.findKey(key, this.root);
        if (temp.isLeaf()) {
            return null;
        } else {
            this.size--;
            this.state++;
            this.root = this.remove(key, this.root);
            return temp.value;
        }
        
    }
    
    /**
     * Remove entry with specified key from subtree.
     * @param key the key of the node to be removed
     * @param node the root of the subtree
     * @return the root of the new subtree
     */
    private BNode remove(K key, BNode node) {
        if (node.isLeaf()) {
            return node;
        }
        int compare = key.compareTo(node.key);
        if (compare < 0) {
            node.left = this.remove(key, node.left);
        } else if (compare > 0) {
            node.right = this.remove(key, node.right);
        } else if (!node.left.isLeaf() && !node.right.isLeaf()) {
            node.key = this.findMin(node.right).key;
            node.right = this.remove(node.key, node.right);
        } else {
            if (!node.left.isLeaf()) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        this.rebalance(this.root);   
        return node;
    }
    
    
    /**
     * Rebalances the subtree with given node as root. 
     * @param curr root of the subtree
     * @return the root of the rebalanced subtree
     */
    private BNode rebalance(BNode curr) {
        if (curr.isLeaf()) {
            return curr;
        }
        if ((curr.left.height - curr.right.height) > 1) {
            if (curr.left.left.height >= curr.left.right.height) {
                curr = this.rotateLeft(curr);
            } else {
                curr = this.doubleLeft(curr);
            }
        } else if ((curr.right.height - curr.left.height) > 1) {
            if (curr.right.right.height >= curr.right.left.height) {
                curr = this.rotateRight(curr);
            } else {
                curr = this.doubleRight(curr);
            }
        }
        curr.height = Math.max(curr.left.height, curr.right.height) + 1;
        return curr;
    }
    /**
     * Rotates about the left child of the root.
     * @param node the root of the given subtree
     * @return the balanced subtree
     */
    private BNode rotateLeft(BNode node) {
        BNode temp = node.left;
        node.left = temp.right;
        temp.right = node;
        node.height = Math.max(node.left.height, node.right.height) + 1;
        temp.height = Math.max(temp.left.height, node.height) + 1;
        return temp;
    }
    /**
     * Rotates about the right child of the root.
     * @param node the root of the given subtree
     * @return the root of the balanced subtree
     */
    private BNode rotateRight(BNode node) {
        BNode temp = node.right;
        node.right = temp.left;
        temp.left = node;
        node.height = Math.max(node.left.height, node.right.height) + 1;
        temp.height = Math.max(temp.right.height, node.height) + 1;
        return temp;
    }
    /**
     * Rotates about the right then left for a double rotate.
     * @param node the root of the subtree to be balanced
     * @return the root of the balanced subtree
     */
    private BNode doubleLeft(BNode node) {
        node.left = this.rotateRight(node.left);
        return this.rotateLeft(node);
    }
    
    /**
     * Rotates about the let then right for a double rotate.
     * @param node the root of the subtree to be balanced
     * @return the root of the balanced subtree
     */
    private BNode doubleRight(BNode node) {
        node.right = this.rotateLeft(node.right);
        return this.rotateRight(node);
    }
    
    @Override()
    public Set<Map.Entry<K, V>> entries() {
        return new HashSet<Map.Entry<K, V>>(this.inOrder(this.root));
    }

    @Override()
    public Set<K> keys() {
        Set<K> toReturn = new HashSet<>();
        for (Map.Entry<K, V> entry : this.inOrder()) {
            toReturn.add(entry.getKey());
        }
        return toReturn;
    }

    @Override()
    public Collection<V> values() {
        Collection<V> toReturn = new LinkedList<>();
        for (Map.Entry<K, V> entry : this.inOrder()) {
            toReturn.add(entry.getValue());
        }
        return toReturn;
    }

    /* -----   BSTMap-specific functions   ----- */

    /** Get the smallest key in a subtree.
     *  @param curr the root of the subtree to search
     *  @return the min key
     */
    public K firstKey(BNode curr) {
        if (curr == null || curr.isLeaf()) {
            return null;
        }
        if (curr.left.isLeaf()) {
            return curr.key;
        } else {
            return this.firstKey(curr.left);
        }
    }

    /** Get the largest key in a subtree.
     *  @param curr the root of the subtree to search
     *  @return the max key
     */
    public K lastKey(BNode curr) {
        if (curr == null || curr.isLeaf()) {
            return null;
        }
        if (curr.right.isLeaf()) {
            return curr.key;
        } else {
            return this.lastKey(curr.right);
        }
    }
    
    /** Preorder traversal that produces an iterator over key-value pairs.
     *  @return an iterable list of entries ordered by keys
     */
    public Iterable<Map.Entry<K, V>> preOrder() {
        return this.preOrder(this.root);
    }
    
    /** PreOrder traversal produces an iterator over entries in a subtree.
     *  @param curr the root of the subtree to iterate over
     *  @return an iterable list of entries ordered by keys
     */
    private Collection<Map.Entry<K, V>> preOrder(BNode curr) {
        Collection<Map.Entry<K, V>> toReturn = new LinkedList<>();
        if (curr.isLeaf()) {
            return toReturn;
        }
        toReturn.add(new AbstractMap.SimpleEntry(curr.key, curr.value));
        toReturn.addAll(this.preOrder(curr.left));
        toReturn.addAll(this.preOrder(curr.right));
        return toReturn;
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
        Collection<Map.Entry<K, V>> toReturn = new LinkedList<>();
        if (curr.isLeaf()) {
            return toReturn;
        }
        toReturn.addAll(this.inOrder(curr.left));
        toReturn.add(new AbstractMap.SimpleEntry(curr.key, curr.value));
        toReturn.addAll(this.inOrder(curr.right));
        return toReturn;
    }
    
    /** Returns a copy of the portion of this map whose keys are in a range.
     *  @param fromKey the starting key of the range, inclusive if found
     *  @param toKey the ending key of the range, inclusive if found
     *  @return the resulting submap
     */
    public AVLMap<K, V> subMap(K fromKey, K toKey) {
        AVLMap<K, V> toReturn = new AVLMap<>();
        for (Map.Entry<K, V> entry : this.inOrder()) {
            if (entry.getKey().compareTo(fromKey) >= 0
                && entry.getKey().compareTo(toKey) <= 0) {
                toReturn.put(entry.getKey(), entry.getValue());
            }
        }
        return toReturn;
    }
    
    /* ---------- from Iterable ---------- */
    /**
     * The inner BSTMapIterator class. 
     */
    private class AVLMapIterator implements Iterator<Map.Entry<K, V>> {
        /**The iterator used for the class.*/
        private Iterator<Map.Entry<K, V>> iter;
        /**The state of the iterator.*/
        private int state;
        
        /**The BSTMapIterator constructor.*/
        AVLMapIterator() {
            this.iter = AVLMap.this.inOrder().iterator();
            this.state = AVLMap.this.state;
        }
        /**
         * Finds the next entry.
         * @return the next map entry
         */
        public Map.Entry<K, V> next() {
            if (AVLMap.this.state != this.state) {
                throw new ConcurrentModificationException();
            }
            return this.iter.next();
        }
        /**Checks if there are remaining entries.
         * @return a boolean indicating hasNext
         */
        public boolean hasNext() {
            if (AVLMap.this.state != this.state) {
                throw new ConcurrentModificationException();
            }
            return this.iter.hasNext();
        }
        /**Unimplemented remove function.*/
        public void remove() {
            return;
        }
    }
    
    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return this.inOrder().iterator();
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
    /**
     * Special print function.
     * @param iter the iterator used in printing
     * @param <PK> extension of key
     * @param <PV> extension of value
     */
    public static <PK extends Comparable<? super PK>, PV> void
        print(Iterable<Map.Entry<PK, PV>> iter) {
        for (Map.Entry<PK, PV> n : iter) {
            System.out.print(n.getKey() + ": " + n.getValue() + "; ");
        }
        System.out.println();
    }
    
    
}


