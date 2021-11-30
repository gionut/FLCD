package Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StackWrapper extends Stack<Element> {
    public void popK(int k) {
        while(k > 0) {
            this.pop();
            k--;
        }
    }

    public void addAllReverse(List<Element> elements) {
        List<Element> reverseList = new ArrayList<>();
        for(int i = elements.size()-1; i >= 0; i--){
            reverseList.add(elements.get(i));
        }
        this.addAll(reverseList);
    }
}
