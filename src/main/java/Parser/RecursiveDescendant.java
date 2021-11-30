package Parser;

import Grammar.Grammar;
import Grammar.NonTerminal;
import Grammar.Symbol;

import java.util.List;

import static Parser.ModelConfiguration.*;

public class RecursiveDescendant {

    public static void recursiveDescendant(Grammar G, String w){
        Element S = new Element(G.getNonTerminals().get(G.getStart().getSymbol()), 0);
        StackWrapper workingStack = new StackWrapper();
        StackWrapper inputStack = new StackWrapper();
        inputStack.add(S);

        ModelConfiguration config =
                new ModelConfiguration(StateType.q, 0, workingStack, inputStack);

        while(config.getState() != StateType.f && config.getState() != StateType.e) {
            if(config.getState() == StateType.q) {
                if(config.getIndex() == w.length() && inputStack.isEmpty()) {
                    RecursiveDescendant.Success(config);
                }
                else {
                    if(!inputStack.isEmpty() && inputStack.peek().isNonTerminal()) {
                        RecursiveDescendant.Expand(config);
                    }
                    else if(!inputStack.isEmpty() && inputStack.peek().isTerminal() && w.length() > config.getIndex() &&
                            inputStack.peek().getTerminal().getSymbol().charAt(0) == w.charAt(config.getIndex())) {
                        RecursiveDescendant.Advance(config);
                    }
                    else RecursiveDescendant.MomentaryInsuccess(config);
                }
            }
            else if (config.getState() == StateType.b) {
                if(workingStack.peek().isTerminal()) {
                    RecursiveDescendant.Back(config);
                }
                else RecursiveDescendant.AnotherTry(config, S);
            }
        }
        if (config.getState() == StateType.e) {
            System.out.println("Parsing Error");
        }
        else {
            System.out.println("Sequence Accepted");
            BuildStringOfProductions(workingStack);
        }
    }

    private static void BuildStringOfProductions(StackWrapper workingStack) {
        StringBuilder sb = new StringBuilder("Productions String: ");
        for(var elem : workingStack) {
            if(elem.isNonTerminal())
                sb.append(elem.symbol).append(elem.index+1).append(" ");
        }
        System.out.println(sb);
    }

    static void Expand(ModelConfiguration config) {
        Element stackElement = config.getInputStack().pop();
        NonTerminal nonTerminal = stackElement.getNonTerminal();

        stackElement.setIndex(0);

        List<Element> firstProduction = Element.symbolsToElements(nonTerminal.getProductions().get(0));

        config.getWorkingStack().add(new Element(nonTerminal, 0));
        config.getInputStack().addAllReverse(firstProduction);
    }

    static  void Advance(ModelConfiguration config) {
        Element terminal = config.getInputStack().pop();
        config.index++;
        config.getWorkingStack().add(terminal);
    }

    static void MomentaryInsuccess(ModelConfiguration config) {
        config.state = StateType.b;
    }

    static void AnotherTry(ModelConfiguration config, Element start) {
        Element element = config.getWorkingStack().pop();
        NonTerminal nonTerm = element.getNonTerminal();

        List<Symbol> prod = element.getProductionAtIndex();
        boolean isLastProduction = nonTerm.getProductions().size() == element.getIndex() + 1;

        if (!isLastProduction){
            config.state = StateType.q;
            element.incIndex();

            List<Symbol> nextProd = element.getProductionAtIndex();

            config.getWorkingStack().add(element);
            config.getInputStack().popK(prod.size());
            config.getInputStack().addAllReverse(Element.symbolsToElements(nextProd));
        }
        else{
            config.getInputStack().popK(prod.size());
            config.getInputStack().add(new Element(nonTerm, 0));
        }
        if(config.index == 0 && nonTerm == start.getSymbol())
            config.state = StateType.e;
    }

    static void Back(ModelConfiguration config) {
        Element terminal =  config.getWorkingStack().pop();
        config.index--;
        config.getInputStack().add(terminal);
    }

    static void Success(ModelConfiguration config) {
        config.state = StateType.f;
    }
}
