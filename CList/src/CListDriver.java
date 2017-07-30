/**
 * Ashwin Bhat, abhat4.
 * Nicholas Garcia, ngarcia5.
 * This is a class with a main for testing. 
 */
public final class CListDriver {
    /**Empty constructor.*/
    private CListDriver() {
        
    }
    
    /**
     * The main. 
     * @param args the argument
     */
    public static void main(String[] args) {
        CList<Integer> test = new CList<Integer>();
        
        
        
        //testMoveToPoS
        for (int i = 0; i < 5; i++) {
            test.append(i);
        }
        test.moveToPos(4);
        System.out.println("The currPos is " + test.currPos());
        test.moveToPos(2);
        System.out.println("The currPos is " + test.currPos());
        //3 1 X0
        /*test.clear();
        test.moveToStart();
        System.out.println("The currPos is " + test.currPos());
        test.moveToEnd();
        System.out.println("The pos is " + test.currPos());
        System.out.println("The length is " + test.length());
        test.next();
        System.out.println("The pos after next is " + test.currPos());
        test.prev();
        System.out.println("The pos is " + test.currPos());
        test.moveToPos(0);
        System.out.println("The pos is " + test.currPos());*/
        /*System.out.println(test.toString());
        test.append(0);
        System.out.println(test.toString());
        test.append(1);
        System.out.println(test.toString());
        test.append(2);
        System.out.println(test.toString());
        test.append(3);
        System.out.println(test.toString());
        
        test.insert(0);
        test.insert(1);
        test.insert(2);
        test.insert(3);
        System.out.println(test.toString());
        test.moveToPos(0);
        System.out.println("removed " + test.remove());
        System.out.println(test.toString());
        test.moveToPos(3);
        System.out.println("removed " + test.remove());
        if (test.isAtEnd()) {
            System.out.println("this is fun for me");
        }
        System.out.println(test.toString());
        test.moveToPos(1);
        System.out.println("removed " + test.remove());
        System.out.println(test.toString());
        System.out.println("removed " + test.remove());
        
        System.out.println("The length is " + test.length());
        test.clear();
        System.out.println("The pos is " + test.currPos());
        System.out.println(test.toString());
        test.clear();
        test.clear();
        System.out.println("The pos is " + test.currPos());*/
        
        
        
   
        
        /*
        test.insert(0);
        test.insert(1);
        test.moveToEnd();
        System.out.println("The currsor is at " + test.currPos());
        System.out.println("The tail value is " + test.getValue());
        test.moveToStart();
        System.out.println("The currsor is at " + test.currPos());
        test.insert(2);
        test.moveToEnd();
        System.out.println("The currsor is at " + test.currPos());
        System.out.println("The tail value is " + test.getValue());
        test.moveToStart();
        System.out.println("The currsor is at " + test.currPos());
        test.insert(3);
        test.moveToEnd();
        System.out.println("The currsor is at " + test.currPos());
        System.out.println("The tail value is " + test.getValue());
        test.moveToStart();
        System.out.println("The currsor is at " + test.currPos());
        test.insert(4);
        test.moveToEnd();
        System.out.println("The currsor is at " + test.currPos());
        System.out.println("The tail value is " + test.getValue());
        //System.out.println(test.toString());
           

        */
    }
}