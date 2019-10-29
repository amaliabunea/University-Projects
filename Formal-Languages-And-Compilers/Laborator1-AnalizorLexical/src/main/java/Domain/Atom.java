package Domain;

public class Atom {
    private String atom;
    private int code;

    public Atom(String atom, int code) {
        this.atom = atom;
        this.code = code;
    }

    public String getAtom() {
        return atom;
    }

    public void setAtom(String atom) {
        this.atom = atom;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
