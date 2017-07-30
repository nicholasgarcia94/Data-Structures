import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.Color;
import java.util.List;

import java.util.TreeMap;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
/**
 * P4C Class.
 * 
 * Nicholas Garcia, ngarcia5
 *  Ashwin Bhat, abhat4
 *  Lousanna Cai, lcai10
 *  Shain Bannowsky, sbannow1
 *  Data Structures 600.226.02
 *  Project 4
 * 
 * Images as Graphs.
 */
public final class P4C {
 
    /**
     * Constant.
     */
    private static final int CONSTANT = 3;
    /**
     * Constructor.
     */
    private P4C() {

    }

    /** Convert an image to a graph of Pixels with edges between
     *  north, south, east and west neighboring pixels.
     *  @param image the image to convert
     *  @param pd the distance object for pixels
     *  @return the graph that was created
     */
    static WGraphP4<Pixel> imageToGraph(BufferedImage image, 
        Distance<Pixel> pd) {

        int width = image.getWidth();
        int height = image.getHeight();

        TreeMap<Integer, GVertex<Pixel>> map = 
                  new TreeMap<Integer, GVertex<Pixel>>();

        WGraphP4<Pixel> toReturn = new WGraphP4<Pixel>(width * height);

        //adding vertices to map for easy retrieval
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                Color curr = new Color(image.getRGB(row, col));
                Pixel toAdd = new Pixel(row, col, curr);
                int id = row * height + col;
                GVertex<Pixel> addToGraph = new GVertex<Pixel>(toAdd, id);
                map.put(id, addToGraph);
                toReturn.addVertex(addToGraph);
            }
        }

        //creating actual graph
        for (int row = 0; row < width; row++) {

            for (int col = 0; col < height; col++) {

                int id = row * height + col;
                GVertex<Pixel> toAdd = map.get(id);

                int[] direction = {(row + 1) * height + col, 
                                   row * height + (col + 1)};

                for (Integer e : direction) {

                    if (e >= 0 && e <= width * width + height) {
                        GVertex<Pixel> other = map.get(e);
                        if (other != null) {
                            toReturn.addEdge(toAdd, other, 
                                    pd.distance(toAdd.data(), other.data()));
                            toReturn.addEdge(other, toAdd, 
                                    pd.distance(other.data(), toAdd.data()));
                        }
                    }
                }
            }
        }
        return toReturn;
    }

    /** Return a list of edges in a minimum spanning forest by
     *  implementing Kruskal's algorithm using fast union/finds.
     *  @param g the graph to segment
     *  @param kvalue the value to use for k in the merge test
     *  @return a list of the edges in the minimum spanning forest
     */
    static List<WEdge<Pixel>> segmenter(WGraphP4<Pixel> g, double kvalue) {
        Iterator<WEdge<Pixel>> edgerator = g.getSortedEdges().iterator();
        Partition part = new Partition(g.getVerts().size());
        // for arrays[][], first dimension contains vertex id
        // second dimension contains min/max RGB value
        int[][] vertMax = new int[g.getVerts().size()][CONSTANT];
        int[][] vertMin = new int[g.getVerts().size()][CONSTANT];
        // vertSize would've been unecessary if the weights in Partition
        // could be accessed
        int[] vertSize = new int[g.getVerts().size()];
        int[] uniDiff = new int[CONSTANT];
        int[] miniDiff = new int[CONSTANT];
        double addition;
        int sourceID;
        int endID;
        WEdge<Pixel> tempEdge;
        List<WEdge<Pixel>> output = new LinkedList<WEdge<Pixel>>();


        populateArrays(g, vertMax, vertMin, vertSize);
        // Iterating through all edges in graph
        while (edgerator.hasNext()) {
            tempEdge = edgerator.next();
            sourceID = (tempEdge.source()).id();
            endID = (tempEdge.end()).id();

            for (int i = 0; i < CONSTANT; i++) {
                // Union difference population
                uniDiff[i] = Math.max(vertMax[sourceID][i],
                                               vertMax[endID][i]);
                uniDiff[i] -= Math.min(vertMin[sourceID][i],
                                               vertMin[endID][i]);

                // Minimum difference population
                // Populated with source set difference
                miniDiff[i] = vertMax[sourceID][i];

                // Comparing with end set difference
                miniDiff[i] = Math.min(miniDiff[i],
                                vertMax[endID][i]);

                addition = (kvalue
                                    / (((double) vertSize[sourceID])
                                    + ((double) vertSize[endID])));
                    // Checking condition, if condition fails...
                if (uniDiff[i] <= (miniDiff[i] + addition)) {
                    
                    vertSize[sourceID] += vertSize[endID];
                    vertSize[endID] = vertSize[sourceID];

                    // Updating partition, adding edge to output list
                    part.union(sourceID, endID);
                    output.add(tempEdge);
                }
            }

        }

        return output;
        
    }

    /** Helper function, populates Max, and Min arrays. 
     * @param g is the graph.
     * @param argMax is the max.
     * @param argMin is the min.
     * @param argSizes is the sizes.
     */
    static void populateArrays(WGraphP4<Pixel> g,
                int[][] argMax, int[][] argMin, int[] argSizes) {
        Iterator<GVertex<Pixel>> verterator = g.getVerts().iterator();
        GVertex<Pixel> tempPixel;
        for (int i = 0; i < g.getVerts().size(); i++) {
            // Assuming pixel has get functions for colors
            if (verterator.hasNext()) {
                tempPixel = verterator.next();
                argMax[i][0] = tempPixel.data().getRed();
                argMax[i][1] = tempPixel.data().getGreen();
                argMax[i][2] = tempPixel.data().getBlue();
                argMin[i][0] = tempPixel.data().getRed();
                argMin[i][1] = tempPixel.data().getGreen();
                argMin[i][2] = tempPixel.data().getBlue();
                argSizes[i] = 1;
            }
        }
        return;
    }

    /** Returns connected trees. 
     * @param g graph to read from.
     * @param res list of edges read from segmenter.
     * @return list of trees
     */
    static ArrayList<List<GVertex<Pixel>>> getTrees(WGraph<Pixel> g, 
                             List<WEdge<Pixel>> res) {

        ArrayList<List<GVertex<Pixel>>> toReturn = 
                     new ArrayList<List<GVertex<Pixel>>>();
        WGraphP4<Pixel> kruskals = new WGraphP4<Pixel>();

        for (int a = 0; a < res.size(); a++) {
            GVertex<Pixel> s = res.get(a).source();
            GVertex<Pixel> e = res.get(a).end();
            if (!kruskals.allVertices().contains(s)) {
                kruskals.addVertex(s);
            }
            if (!kruskals.allVertices().contains(e)) {
                kruskals.addVertex(e);
            }
            kruskals.addEdge(s, e, res.get(a).weight());
        }

        HashSet<GVertex<Pixel>> vertices = new HashSet<GVertex<Pixel>>();

        for (int a = 0; a < res.size(); a++) {
            WEdge<Pixel> curr = res.get(a);
            
            if (!vertices.contains(curr.source())) {
                List<GVertex<Pixel>> depth = 
                        kruskals.depthFirst(curr.source());
                if (!vertices.containsAll(depth)) {
                    toReturn.add(depth);
                    vertices.addAll(depth);
                }
            }
        }
        
        return toReturn;
    }

    /**
     * Reads in image and makes segmented image.
     * @param args from command line. args[0] is file and args[1] is kvalue.
     */
    public static void main(String[] args) {

        final int gray = 0x0DCDCDC;

        try {
          // the line that reads the image file

            String input = args[0];
            BufferedImage image = ImageIO.read(new File(input));
            WGraphP4<Pixel> g = imageToGraph(image, new PixelDistance());

            List<WEdge<Pixel>> res = segmenter(g, Double.parseDouble(args[1]));

            System.out.print("result =  " + res.size() + "\n");

            ArrayList<List<GVertex<Pixel>>> forest = getTrees(g, res);
            System.out.print("NSegments =  "
                    + (forest.size()) + "\n");
            int curr = 0;

            for (List<GVertex<Pixel>> x: forest) {

                // make a background image to put a segment into
                for (int i = 0; i < image.getHeight(); i++) {
                    for (int j = 0; j < image.getWidth(); j++) {
                        image.setRGB(j, i, gray);
                    }
                }

                // After you have a spanning tree connected component x, 
                // you can generate an output image like this:
                for (GVertex<Pixel> i: x)  {
                    Pixel d = i.data();
                    image.setRGB(d.row(), d.col(), d.value().getRGB());
                }
                curr++;

                String filename = input.substring(0, 
                        input.length() - (CONSTANT + 1)) + curr + ".png";
                File f = new File(filename);
                ImageIO.write(image, "png", f);

                // You'll need to do that for each connected component,
                // writing each one to a different file, clearing the
                // image buffer first
            }
            

        } catch (IOException e) {
            System.out.print("Missing File!\n");

            // log the exception
            // re-throw if desired
        }
        
    }
}
