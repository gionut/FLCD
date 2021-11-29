package Grammar;

import lombok.Data;
import lombok.NonNull;

@Data
public class Terminal implements Symbol {
    @NonNull String symbol;

    public String toString(){
        return symbol;
    }
}
