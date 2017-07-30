import java.util.ArrayList;
import java.util.TreeMap;

import java.util.List;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
/**
 * Graph class.
 * @author Ashwin Bhat, abhat4
 * @author Nicholas Garcia, ngarcia5
 * @author Shain Bannowsky, sbannow1
 * @author Lousanna Cai, lcai10
 * 
 * @param <VT> the generic type argument
 */
public class WGraphP4<VT> implements WGraph<VT> {
    /**This is just a default size.*/
    private static final int DEFAULT = 100;
    /**Used to sequentially generate vertex IDs>.*/
    private int nextID;
    
    /**Vertices.*/
    private ArrayList<GVertex<VT>> verts;
    /**Edges counted once.*/
    private ArrayList<WEdge<VT>> masterEdgeList;
    /**Adjacency/edge incidence list.*/
    private TreeMap<GVertex<VT>, HashSet<WEdge<VT>>> adjList;
    /**Sorted Queue of Edges.*/
    private PQHeap<WEdge<VT>> sortedEdges;
    /**The number of edges.*/
    private int numEdges;
    
    /**Default constructor.*/
    public WGraphP4() {
        this(DEFAULT);
    }
    
    /**
     * Constructor for graph.
     * @param maxVerts max number of vertices
     */
    public WGraphP4(int maxVerts) {
        this.nextID = 0;
        this.numEdges = 0;
        this.verts = new ArrayList<GVertex<VT>>(maxVerts);
        this.adjList = new TreeMap<GVertex<VT>, HashSet<WEdge<VT>>>();
        this.masterEdgeList = new ArrayList<WEdge<VT>>();
        this.sortedEdges = new PQHeap<WEdge<VT>>();
    }
    
    @Override
    public int numEdges() {
        return this.numEdges;
    }

    @Override
    public int numVerts() {
        return this.verts.size();
    }

    @Override
    public int nextID() {
        return this.nextID;
    }

    @Override
    public boolean addVertex(VT d) {
        if (d != null) {
            GVertex<VT> vt = new GVertex<VT>(d, this.nextID++);
            this.verts.add(vt);
            HashSet<WEdge<VT>> set = new HashSet<WEdge<VT>>();
            this.adjList.put(vt, set);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addVertex(GVertex<VT> v) {
        if (v != null && v.data() != null) {
            v.setID(this.nextID++); 
            this.verts.add(v);
            HashSet<WEdge<VT>> set = new HashSet<WEdge<VT>>();
            this.adjList.put(v, set);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addEdge(WEdge<VT> e) {
        if (e == null) {
            return false;
        }
        return this.addEdge((GVertex<VT>) e.source(), e.end(), e.weight());
    }

    @Override
    public boolean addEdge(GVertex<VT> v, GVertex<VT> u, double weight) {
        if (u.equals(null) || u.equals(null)) {
            return false;
        }
        boolean success = true;
        if (v.equals(u)) {
            success = false;
        }
        if (!this.verts.contains(v)) {
            success = this.addVertex(v);
        }
        if (success && !this.verts.contains(u)) {
            success = this.addVertex(u);
        }
        if (!success) {
            return false;
        }
        WEdge<VT> edge1 = new WEdge<VT>(v, u, weight);
        WEdge<VT> edge2 = new WEdge<VT>(u, v, weight);
        if (!this.adjList.get(v).contains(edge1)) {
            this.adjList.get(v).add(edge1);
            this.adjList.get(u).add(edge2); //adds the reverse
            this.masterEdgeList.add(edge1); //unsorted edges
            this.sortedEdges.insert(edge1); //(used for Kruskals later)
            this.numEdges++;
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteEdge(GVertex<VT> v, GVertex<VT> u) {
        if (this.adjList.containsKey(v) && this.adjList.containsKey(u)) {
            Iterator<WEdge<VT>> it = this.adjList.get(v).iterator();
            WEdge<VT> edge = null;
            WEdge<VT> testEdge = null;
            while (it.hasNext()) {
                testEdge = it.next();
                if (this.areIncident(testEdge, u)) {
                    edge = testEdge;
                    this.adjList.get(v).remove(edge);
                    this.numEdges--;
                    return true;
                }
            } 
        }
        return false;
    }

    @Override
    public boolean areAdjacent(GVertex<VT> v, GVertex<VT> u) {
        if (this.adjList.containsKey(v) && this.adjList.containsKey(u) 
                && this.numEdges > 0) {
            List<GVertex<VT>> list = this.neighbors(v);
            if (list.contains(u)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<GVertex<VT>> neighbors(GVertex<VT> v) {
        ArrayList<GVertex<VT>> nbs = new ArrayList<GVertex<VT>>();
        Iterator<WEdge<VT>> edges = this.adjList.get(v).iterator();
        WEdge<VT> curr = null;
        //takes each edge that v is source of and adds the end
        while (edges.hasNext()) {
            curr = edges.next();
            nbs.add(curr.end());
        }
        return nbs;
    }

    @Override
    public int degree(GVertex<VT> v) {
        return this.neighbors(v).size();
    }

    @Override
    public boolean areIncident(WEdge<VT> e, GVertex<VT> v) {
        return e.source().equals(v) || e.end().equals(v);
    }

    @Override
    public List<WEdge<VT>> allEdges() {
        return this.masterEdgeList;
    }

    @Override
    public List<GVertex<VT>> allVertices() {
        return this.verts;
    }

    @Override
    public List<GVertex<VT>> depthFirst(GVertex<VT> v) {
        ArrayList<GVertex<VT>> reaches = new ArrayList<GVertex<VT>>();
        LinkedList<GVertex<VT>> stack = new LinkedList<GVertex<VT>>();
        boolean[] visited = new boolean[this.numVerts()];
        stack.addFirst(v);
        visited[v.id()] = true;
        while (!stack.isEmpty()) {
            v = stack.removeFirst();
            reaches.add(v);
            for (GVertex<VT> u: this.neighbors(v)) {
                if (!visited[u.id()]) {
                    visited[u.id()] = true;
                    stack.addFirst(u);
                }
            }
        }
        return reaches;
    }

    @Override
    public List<WEdge<VT>> incidentEdges(GVertex<VT> v) {
        ArrayList<WEdge<VT>> incidents = new ArrayList<WEdge<VT>>();
        Iterator<WEdge<VT>> edges = this.adjList.get(v).iterator();
        if (edges != null) {
            WEdge<VT> curr = null;
            //takes each edge that v is source of and adds the end
            while (edges.hasNext()) {
                curr = edges.next();
                incidents.add(curr);
            }
        }
        return incidents;
    }

    @Override
    public List<WEdge<VT>> kruskals() {
        Iterator<WEdge<VT>> edgerator = this.sortedEdges.iterator();
        Partition part = new Partition(this.verts.size());
        WEdge<VT> tempEdge;
        List<WEdge<VT>> output = new LinkedList<WEdge<VT>>();
        int sourceID;
        int endID;

        // Iterating through all edges in graph
        while (edgerator.hasNext()) {
            tempEdge = edgerator.next();
            sourceID = (tempEdge.source()).id();
            endID = (tempEdge.end()).id();

            // If vertices aren't connected in spanning tree...
            if (part.find(sourceID) != part.find(endID)) {
                part.union(sourceID, endID);
                output.add(tempEdge);
            }
        }
        return output;
    }
    
    /**
     * Returns the arraylist of all vertices.
     * @return arraylist of vertices
     */
    public ArrayList<GVertex<VT>> getVerts() {
        return this.verts;
    }
    
    /**
     * Returns the PQHeap of all edges.
     * @return PQHeap of edges
     */
    public PQHeap<WEdge<VT>> getSortedEdges() {
        return this.sortedEdges;
    }

}
