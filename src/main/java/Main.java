import Grammar.Grammar;
import Parser.RecursiveDescendant;

public class Main {
    public  static void main(String[] args){
        Grammar grammar = new Grammar();
        grammar.readFromFile("src/main/java/g1");
        RecursiveDescendant.recursiveDescendant(grammar, "aacbaca");
    }
}
