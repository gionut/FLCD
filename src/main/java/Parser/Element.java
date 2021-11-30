package Parser;

import Grammar.NonTerminal;
import Grammar.Symbol;
import Grammar.Terminal;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class Element {
    Symbol symbol;
    int index;

    public Element(Symbol symbol, int index) {
        this.symbol = symbol;
        this.index = index;
    }

    public Element(Symbol symbol) {
        this.symbol = symbol;
    }

    public void incIndex() {
        this.index++;
    }

    public NonTerminal getNonTerminal() {
        return (NonTerminal) this.symbol;
    }

    public Terminal getTerminal() {
        return (Terminal) this.symbol;
    }

    public boolean isNonTerminal() {
        return this.symbol instanceof NonTerminal;
    }
    public boolean isTerminal() {
        return this.symbol instanceof Terminal;
    }

    public static List<Element> symbolsToElements(List<Symbol> symbols){
        return symbols.stream().map(s -> new Element(s, 0)).collect(Collectors.toList());
    }

    public List<Symbol> getProductionAtIndex() {
        return this.getNonTerminal().getProductions().get(this.index);
    }

}
