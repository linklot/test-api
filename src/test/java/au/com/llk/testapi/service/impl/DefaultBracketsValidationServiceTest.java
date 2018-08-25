package au.com.llk.testapi.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import au.com.llk.testapi.controller.payload.BracketsValidationResponse;
import au.com.llk.testapi.utils.BracketsValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class DefaultBracketsValidationServiceTest {

    private final String INPUT = "test input";

    private DefaultBracketsValidationService testTarget;

    @Mock
    private BracketsValidator bracketsValidator;

    @Before
    public void setUp() {
        initMocks(this);
        testTarget = new DefaultBracketsValidationService(bracketsValidator);
    }

    @Test
    public void validateBracketsShouldReturnBalancedResult() {
        when(bracketsValidator.validateBrackets(INPUT)).thenReturn(true);

        BracketsValidationResponse resp = testTarget.validateBrackets(INPUT);
        assertEquals(INPUT, resp.input);
        assertTrue(resp.isBalanced);
    }

    @Test
    public void validateBracketsShouldReturnUnBalancedResult() {
        when(bracketsValidator.validateBrackets(INPUT)).thenReturn(false);

        BracketsValidationResponse resp = testTarget.validateBrackets(INPUT);
        assertEquals(INPUT, resp.input);
        assertFalse(resp.isBalanced);
    }

}