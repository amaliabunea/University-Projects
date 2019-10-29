package Domain;

public class Error {
    private int line;
    private String word;

    public Error(int line, String word) {
        this.line = line;
        this.word = word;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
