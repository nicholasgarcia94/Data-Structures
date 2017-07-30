
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
// import java.util.ArrayIndexOutOfBoundsException;
/**
 * The main class to run everything.
 * @author Ashwin Bhat, abhat4.
 * @author Nicholas Garcia, ngarcia5
 * 
 */
public final class P1 {
    /**Constant threshold test.*/
    public static final int PTHRESH = 10;
    /**Empty constructor.*/
    private P1() {
        
    }
    /**
     * The main. 
     * @param args The input file
     * @throws IOException input,output exception
     */
    public static void main(String[] args) throws IOException {

        Scanner infile = null;
        boolean inerror = false;
        try {
            //System.out.println("0 " + args[0] + " should be input filename");
            infile = new Scanner(new FileReader(args[0]));
        } catch (ArrayIndexOutOfBoundsException a) {
            System.err.println("must give input filename at command line");
            inerror = true;
        } catch (IOException f) {
            System.err.println("can't open that file, try again");
            inerror = true;
        }
        if (inerror) {
            System.err.println("exiting...");
            System.exit(1);
        }          
        Scanner inline;
        String line;
        String name, item;
        int time, under, over; 
        CutthroatKitchen cutK = new CutthroatKitchen();
        
        PrintWriter fileWriter = new PrintWriter(new FileWriter("sim0.txt"));

        while (infile.hasNext()) {
            name = infile.nextLine();
            CookingStation station = new CookingStation(name);
            line = infile.nextLine();
            while (!line.equals("")) {
                inline = new Scanner(line);
                item = inline.next();
                time = inline.nextInt();
                under = inline.nextInt();
                over = inline.nextInt();
                CookingItem foodItem = new CookingItem(item, time, under, over);
                station.addItem(foodItem);
                if (infile.hasNext()) {
                    line = infile.nextLine();
                } else {
                    line = "";
                }
            }
            cutK.addStation(station);
        }
        
        CutthroatKitchen temp = cutK;
        int rThresh = 1; 
        int pThresh = PTHRESH;
        
        //Simulation 1
        System.out.println(cutK.toString());  
        while (cutK.length() != 0) { 
            if (cutK.getValue().length() == 0) {
                cutK.remove();
            }
            if (cutK.length() == 0) {
                break;
            }
            cutK.tick();
            cutK.getValue().tend(rThresh, pThresh);
            if (cutK.isAtEnd()) {
                cutK.moveToStart();
            } else {
                cutK.next();
            }
            fileWriter.write(cutK.toString());   
            System.out.println(cutK.toString());   
            fileWriter.println();
            if (cutK.getValue().length() == 0) {
                cutK.remove();
            }
        }
        int penalty = cutK.calculatePenalty();
        System.out.println("The final penalty was: " + penalty);
        fileWriter.write("The final penalty was: " + penalty);
        fileWriter.println();
        fileWriter.close();        
    }
}    
    
 


