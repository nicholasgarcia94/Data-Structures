/** 
 * Implementation of an edge class (for graphs), could be directed or not.
 * Nicholas Garcia, ngarcia5
 *  Ashwin Bhat, abhat4
 *  Lousanna Cai, lcai10
 *  Shain Bannowsky, sbannow1
 *  Data Structures 600.226.02
 *  Project 4
 * @param <T> generic type
 */
public class WEdge<T> implements Comparable<WEdge<T>> {

    /** Starting vertex of an edge. */
    private GVertex<T> source;
    /** Ending vertex of an edge. */
    private GVertex<T> end;
    /** Weight of the edge. */
    private double weight;

    /** Create an undirected edge.
     *  @param u the start
     *  @param v the end
     */
    public WEdge(GVertex<T> u, GVertex<T> v) {
        if (!u.equals(v)) {
            this.source = u;
            this.end = v;
            this.weight = 0;
        } 
    }

    /** Create an edge.
     *  @param u the start
     *  @param v the end
     *  @param wt true if directed, false otherwise
     *  @throws NullPointerException used to ensure WEdge isn't null
     */
    public WEdge(GVertex<T> u, GVertex<T> v, double wt) 
            throws NullPointerException {
        if (!u.equals(v)) {
            this.source = u;
            this.end = v;
            this.weight = wt;
        } 
    }

    /** Returns the weight of the edge.
     *  @return double with the weight
     */
    public double weight() {
        return this.weight;
    }

    /** Is a vertex incident to this edge.
     *  @param v the vertex
     *  @return true if source or end, false otherwise
     */
    public boolean isIncident(GVertex<T> v) {
        return this.source.equals(v) || this.end.equals(v);
    }

    /** Get the starting endpoint vertex.
     *  @return the vertex
     */
    public GVertex<T> source() {
        return this.source;
    }

    /** Get the ending endpoint vertex.
     *  @return the vertex
     */
    public GVertex<T> end() {
        return this.end;
    }

    /** Create a string representation of the edge.
     *  @return the string as (source,end)
     */
    public String toString() {
        return "(" + this.source + ", " + this.end + ", " + this.weight + ")";
    }

    /** Check if two edges are the same.
     *  @param other the edge to compare to this
     *  @return true if weight and endpoints match, false otherwise
     */
    public boolean equals(Object other) {
        if (other instanceof WEdge) {
            WEdge<T> e = (WEdge<T>) other;
            if (this.weight != e.weight) {
                return false;
            } else {
                return this.source.equals(e.source)
                    && this.end.equals(e.end)
                    || this.source.equals(e.end)
                    && this.end.equals(e.source);
            }
        }
        return false;
    }

    /** Make a hashCode based on the toString.
     *  @return the hashCode
     */
    public int hashCode() {
        return this.toString().hashCode();
    }
    
    @Override
    public int compareTo(WEdge<T> cedge) {
        return (int) (this.weight - cedge.weight);
    }

}
