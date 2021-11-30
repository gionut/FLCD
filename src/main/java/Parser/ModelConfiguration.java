package Parser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelConfiguration {
    enum StateType {
        q, b, f, e;
    }

    StateType state;
    Integer index;
    StackWrapper workingStack;
    StackWrapper inputStack;
}
