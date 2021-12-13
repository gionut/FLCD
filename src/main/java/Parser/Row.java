package Parser;

import Grammar.Symbol;

public class Row {
    Integer index;
    Symbol info;
    Integer parent;
    Integer sibling;

    @Override
    public String toString() {
        return index +" " + info + " " + parent + " " + sibling;
    }
}
