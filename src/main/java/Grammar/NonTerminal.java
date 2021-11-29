package Grammar;

import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
public class NonTerminal implements Symbol{
    @NonNull String symbol;
    List<List<Symbol>> productions = new ArrayList<>();

    public String toString(){
        return symbol;
    }
}
