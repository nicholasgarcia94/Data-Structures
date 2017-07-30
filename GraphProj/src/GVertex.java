/**
 * Extension of Vertex class that uses Generics instead of Objects. 
 * @param <T> the generic type 
 * 
 *  Authors, JHED
 *  Nicholas Garcia, ngarcia5
 *  Ashwin Bhat, abhat4
 *  Lousanna Cai, lcai10
 *  Shain Bannowsky, sbannow1
 *  Data Structures 600.226.02
 *  Project 4
 *  
 *
 */
public class GVertex<T> extends Vertex {
    /**
     * Data.
     */
    private T data;

    /**
     * Constructor the GVertex class.
     * @param d the data to store in the node
     *  @param id the unique id of the node
     */
    public GVertex(T d, int id) {
        super(d, id);
        this.data = d;
    }
    
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof GVertex<?>) {
            return this.id() == ((GVertex<?>) other).id();
        } 
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /** Method to return data.
     * 
     * @return T data value.
     */
    public T data() {
        return this.data;
    }

    /** Method to compare the IDs of two generic types.
     * 
     * @param other the GVertex being compared to this
     * @return negative if this < other, 0 if equal, positive if this > other
     */
    public int compareTo(GVertex<T> other) {
        return super.compareTo(other);
    }   

}