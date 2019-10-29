package Domain;

public class FIPElement {
    private int codeAtom;
    private int codeTS;

    public FIPElement(int codeAtom, int codeTS) {
        this.codeAtom = codeAtom;
        this.codeTS = codeTS;
    }

    public int getCodeAtom() {
        return codeAtom;
    }

    public void setCodeAtom(int codeAtom) {
        this.codeAtom = codeAtom;
    }

    public int getCodeTS() {
        return codeTS;
    }

    public void setCodeTS(int codeTS) {
        this.codeTS = codeTS;
    }
}
