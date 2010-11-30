import java.util.Enumeration;
import java.util.Vector;




public class T3 {
	public static void main(String[] args) {
        Vector v = new Vector();
        v.add("one");
        v.add("two");
        v.add("three");
        v.add("four");
        Enumeration enume = v.elements();
        while (enume.hasMoreElements()){
            String s = (String) enume.nextElement();
            if (s.equals("two"))
                v.remove("two");
            else{
                System.out.println(s);
            }
        }
        System.out.println("What's really there...");
        enume = v.elements();
        while (enume.hasMoreElements()){
            String s = (String) enume.nextElement();
            System.out.println(s);
        }
    }

}
