package au.com.llk.testapi.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class BracketsValidatorTest {

    private BracketsValidator testTarget;

    @Before
    public void setUp() {
        testTarget = new BracketsValidator();
    }

    @Test
    public void validateBracketsShouldReturnTrue() {
        String input = "{[(test string)]}";
        assertTrue(testTarget.validateBrackets(input));

        input = "{[()]}";
        assertTrue(testTarget.validateBrackets(input));
    }

    @Test
    public void validateBracketsShouldReturnFalse() {
        String input = "{[(";
        assertFalse(testTarget.validateBrackets(input));

        input = "{[}]";
        assertFalse(testTarget.validateBrackets(input));

        input = "[{)]";
        assertFalse(testTarget.validateBrackets(input));

        input = "]{}[";
        assertFalse(testTarget.validateBrackets(input));
    }
}