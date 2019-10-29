import Domain.Atom;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CodingScheme {
    private List<Atom> codes;
    private String filename;

    public CodingScheme(String filename) {
        this.filename = filename;
        codes = new ArrayList<>();
    }

    /**
     * read codes from coding scheme
     * @throws IOException
     */
    public void readCodes() throws IOException
    {
        File file = new File(filename);
        FileInputStream fstream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;
        while ((strLine = br.readLine()) != null)
        {
            String[] data = strLine.split(" ");
            if (data.length!=2)
            {
                System.err.println("CodingScheme not formatted correctly!");
                return;
            }
            else
            {
                codes.add(new Atom(data[0].trim(), Integer.parseInt(data[1])));
            }
        }
        br.close();
    }

    public List<Atom> getCodes() {
        return codes;
    }

    /**
     * @param atom : name of the symbol
     * @return the code of atom from coding scheme or -1 if it doesn't exist
     */
    public int getIndexOf(String atom) {
        int index = -1;
        int i=0;
        boolean gasit = false;
        while (i<codes.size() && !gasit) {
            Atom el = codes.get(i);
            if (el.getAtom().equals(atom)) {
                index = el.getCode();
                gasit = true;
            }
            i++;
        }
        if (!gasit)
            return -1;
        return index;
    }
}
