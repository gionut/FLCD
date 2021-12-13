package Parser;

import Grammar.NonTerminal;
import Grammar.Symbol;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Table {
    Map<Integer, Row> rows;

    public void constructTable(StackWrapper workingStack) {
        this.rows = new HashMap<>();
        int index = 1;
        Row initialState = new Row();
        initialState.index = index;
        initialState.info = workingStack.firstElement().symbol;
        initialState.parent = 0;
        initialState.sibling = 0;
        this.rows.put(0, initialState);

        StackWrapper stack = new StackWrapper();
        for(var elem: workingStack){
            if(elem.isNonTerminal())
                stack.add(0, elem);
        }
        Stack<Integer> parents = new Stack<>();
        parents.add(1);
        index = 2;
        while(!stack.isEmpty()){
            Element s = stack.pop();
            int parent = parents.pop();
            int sibling = 0;
            for(Symbol elem: s.getProductionAtIndex()){
                Row row = new Row();
                row.index = index;
                row.info = elem;
                row.parent = parent;
                row.sibling = sibling;
                this.rows.put(index, row);
                sibling = index;
                if(elem instanceof NonTerminal)
                    parents.add(0, index);
                index++;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(var row: rows.values()){
            sb.append(row).append("\n");
        }
        return sb.toString();
    }
}
