import Grammar.Grammar;

public class Main {
    public  static void main(String[] args){
        Grammar grammar = new Grammar();
        grammar.readFromFile("src/main/java/grammar.txt");
        System.out.println(grammar);
    }
}
