import Domain.Error;
import Domain.FIPElement;
import Domain.Validator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Analizor {
    private CodingScheme cs;
    private List<FIPElement> fip;
    private MyHashTable tsIdentifiers;
    private MyHashTable tsConstants;
    private Validator validator;
    private File source_file;

    public Analizor(File file, CodingScheme cs, int sizeTS) {
        this.cs = cs;
        this.source_file = file;
        this.fip = new ArrayList<>();
        this.tsConstants = new MyHashTable(sizeTS);
        this.tsIdentifiers = new MyHashTable(sizeTS);
        this.validator = new Validator();
    }

    /**
     * read codes from coding scheme
     */
    public void readCodes() {
        try {
            cs.readCodes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * get lines from source code
     * @return an array containing all the lines from source code
     * @throws IOException
     */
    public ArrayList<String> getLines() throws IOException {
        ArrayList<String> allLines = new ArrayList<>();
        FileInputStream fstream = new FileInputStream(source_file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;
        while ((strLine = br.readLine()) != null) {
            allLines.add(strLine.trim());
        }

        br.close();
        return allLines;
    }

    /**
     * @param word : name of the symbol from the source code
     * @return true, if the word exists in the coding scheme
     *         false, otherwise
     */
    public boolean isReservedWord(String word) {
        int index = cs.getIndexOf(word);
        return index != -1;
    }

    /**
     * main function
     * analyze the source code and create TS for ID, TS for CONST and FIP
     */
    public void analyze() {
        readCodes();
        try {
            ArrayList<String> lines = getLines();
            int index = 0;
            for (String line : lines) {
                String[] words = line.split(" ");   // split line by space
                for (String word : words) {
                    word = word.trim();     //remove leading and trailing spaces
                    if (isReservedWord(word)) {
                        FIPElement element = new FIPElement(cs.getIndexOf(word), -1);   //create new FIPElement with the corresponding code from coding scheme and -1 (convention - reserved word)
                        fip.add(element);
                    } else if (validator.validate(word, index)) {   //word is a valid ID or CONST
                        if (validator.isIdentifier(word)) {     //word is an ID
                            if (!tsIdentifiers.contains(word)) {
                                tsIdentifiers.add(word);    //add in TS if it doesn't exist
                            }
                            FIPElement element = new FIPElement(cs.getIndexOf("ID"), tsIdentifiers.getIndexOf(word));
                            fip.add(element);   //add in FIP

                        }
                        if (validator.isConstant(word)) {   //word is a CONST
                            if (!tsConstants.contains(word)) {
                                tsConstants.add(word);  //add in TS if it doesn't exist
                            }
                            FIPElement element = new FIPElement(cs.getIndexOf("CONST"), tsConstants.getIndexOf(word));
                            fip.add(element);   //add in FIP
                        }
                    }
                }
                index++;    //next line
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return errors of the source code
     */
    public String getErrors() {
        String res ="";
        for (Error e : validator.getErrors()) {
            res += "ERROR! Line: "+ e.getLine()+", word: "+e.getWord()+"\n";
        }
        return res;
    }

    /**
     * write FIP table to file
     * @param filename : name of the output file
     * @throws IOException
     */
    public void writeFIP(String filename) throws IOException {
        FileWriter fileWriter = new FileWriter(filename);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (FIPElement el : fip) {
            printWriter.println(el.getCodeAtom()+"  "+el.getCodeTS());
        }
        printWriter.close();
    }

    /**
     * write TS table to file
     * @param ts : table to write
     * @param filename : name of the output file
     * @throws IOException
     */
    public void writeTS(MyHashTable ts, String filename) throws IOException {
        FileWriter fileWriter = new FileWriter(filename);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(ts.toString());
        printWriter.close();
    }


    public List<FIPElement> getFip() {
        return fip;
    }

    public void setFip(List<FIPElement> fip) {
        this.fip = fip;
    }

    public MyHashTable getTsIdentifiers() {
        return tsIdentifiers;
    }

    public void setTsIdentifiers(MyHashTable tsIdentifiers) {
        this.tsIdentifiers = tsIdentifiers;
    }

    public MyHashTable getTsConstants() {
        return tsConstants;
    }

    public void setTsConstants(MyHashTable tsConstants) {
        this.tsConstants = tsConstants;
    }

    public Validator getValidator() {
        return validator;
    }
}
