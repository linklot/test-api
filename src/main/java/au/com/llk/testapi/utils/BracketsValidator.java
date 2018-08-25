package au.com.llk.testapi.utils;

import java.util.Stack;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class BracketsValidator {

    public boolean validateBrackets(@NotNull final String input) {
        Stack<Character> stack = new Stack<>();

        for (Character c : input.toCharArray()) {
            if (c == '{' || c == '[' || c == '(') {
                stack.push(c);
            } else if (c == '}') {
                if (stack.isEmpty() || stack.pop() != '{') {
                    return false;
                }
            } else if (c == ']') {
                if (stack.isEmpty() || stack.pop() != '[') {
                    return false;
                }
            } else if (c == ')') {
                if (stack.isEmpty() || stack.pop() != '(') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

}
