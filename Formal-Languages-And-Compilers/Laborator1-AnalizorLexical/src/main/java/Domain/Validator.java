package Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Validator {
    private List<Error> errors;

    public Validator() {
        this.errors = new ArrayList<>();
    }

    /**
     * @param id : the name of identifier
     * @return true if the identifier respects the rules, false otherwise
     */
    public boolean isIdentifier(String id) {
        String regex = "^[a-zA-Z][a-zA-Z0-9]*$";
        return (id.length() <= 250 && Pattern.matches(regex, id));
    }

    /**
     * @param constant : the name of constant
     * @return true if the constant can be converted to double, false otherwise
     */
    public boolean isConstant(String constant) {
        try {
            double nr = Double.parseDouble(constant);
            return true;
        }
        catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * @param word : the name of the symbol from source code
     * @param indexLine : the index of line in source code
     * @return true if the word is an identifier or a constant,
     *         false, otherwise and add error in error list
     */
    public boolean validate(String word, int indexLine) {
        if (!isIdentifier(word) && !isConstant(word)) {
            errors.add(new Error(indexLine, word));
            return false;
        }
        return true;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}
