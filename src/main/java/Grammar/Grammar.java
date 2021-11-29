package Grammar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grammar {
    Map<String, Terminal> terminals = new HashMap<>();
    Map<String, NonTerminal> nonTerminals = new HashMap<>();
    NonTerminal start;
    boolean isCFG = true;

    public boolean CFGCheck() {
        return this.isCFG;
    }

    public void readFromFile(String path) {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line = bufferedReader.readLine();

            this.start = new NonTerminal(line.strip());
            this.nonTerminals.put(this.start.symbol, this.start);

            int state = 0; // READ NON TERMINALS
            while(line != null) {
                switch (state){
                    case 0 -> { // NON TERMINALS
                        String name = line.strip();
                        this.nonTerminals.put(name, new NonTerminal(name));
                    }
                    case 1 -> { // TERMINALS
                        String name = line.strip();
                        this.terminals.put(name, new Terminal(name));
                    }
                    case 2 -> { // PRODUCTIONS
                        String[] splitLine = line.split("->");
                        if(splitLine[0].split(" ").length > 1)
                        {
                            this.isCFG = false;
                            return;
                        }

                        String nonTermName = splitLine[0].strip();
                        splitLine = splitLine[1].strip().split(" ");

                        List<Symbol> production = new ArrayList<>();

                        for (String symbolName : splitLine) {
                            Symbol symbol = null;
                            if (symbolName.equals("eps"))
                                symbol = new Epsilon();
                            if (symbol == null) {
                                symbol = this.nonTerminals.get(symbolName);
                                if (symbol == null)
                                    symbol = this.terminals.get(symbolName);
                            }
                            production.add(symbol);
                        }
                        this.nonTerminals.get(nonTermName).productions.add(production);
                    }
                    default -> { break; }
                }
                line = bufferedReader.readLine();
                if(line != null && line.equals("")) {
                    state++;
                    line = bufferedReader.readLine();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(NonTerminal nonTerm : this.nonTerminals.values()) {
            for (List<Symbol> production : nonTerm.productions) {
                sb.append(nonTerm).append(" -> ").append(production).append("\n");
            }
        }
        sb.append("\nnon terminals:\n");
        for (var term : this.nonTerminals.keySet()) {
            sb.append(this.nonTerminals.get(term)).append("\n");
        }
        sb.append("\nterminals:\n");
        for (var term : this.terminals.keySet()) {
            sb.append(this.terminals.get(term)).append("\n");
        }
        sb.append("\nstart: ").append(this.start);
        sb.append("\nis cfg: ").append(this.isCFG);
        return sb.toString();
    }
}
