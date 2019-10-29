import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        CodingScheme cs = new CodingScheme("codingScheme.txt");
        Analizor a = new Analizor(new File("suma.txt"), cs, 10);
        a.analyze();
        writeResults(a);
    }

    /**
     * write FIP and TS tables and print the errors
     * @param a : analizor
     */
    private static void writeResults(Analizor a) {
        if (a.getErrors().equals("")) {
            System.out.println("Your source code is lexically correct! :) ");
            try {
                a.writeFIP("fip.txt");
                a.writeTS(a.getTsIdentifiers(), "tsID.txt");
                a.writeTS(a.getTsConstants(), "tsCONST.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println(a.getErrors());
        }
    }
}
