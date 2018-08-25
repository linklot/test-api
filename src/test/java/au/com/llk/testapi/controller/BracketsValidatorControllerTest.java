package au.com.llk.testapi.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import au.com.llk.testapi.controller.payload.BracketsValidationResponse;
import au.com.llk.testapi.exception.GlobalControllerExceptionHandler;
import au.com.llk.testapi.service.BracketsValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class BracketsValidatorControllerTest extends AbstractControllerTest {

    private final String TEST_INPUT = "test input";

    @Mock
    private BracketsValidationService bracketsValidationService;

    @Before
    public void setUp() {
        initMocks(this);
        val testTarget = new BracketsValidatorController(bracketsValidationService);
        mockMvc = MockMvcBuilders.standaloneSetup(testTarget)
                .setControllerAdvice(new GlobalControllerExceptionHandler()).build();
    }

    @Test
    public void validateBracketsShouldReturnBalancedResult() throws Exception {
        val mockResponse = givenBalancedResponse();
        when(bracketsValidationService.validateBrackets(TEST_INPUT)).thenReturn(mockResponse);

        val requestBuilder = MockMvcRequestBuilders.get("/tasks/validateBrackets?input=" + TEST_INPUT);
        val mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        val resp = mvcResult.getResponse().getContentAsString();

        val mapper = new ObjectMapper();
        val jsonResp = mapper.readValue(resp, BracketsValidationResponse.class);
        assertEquals(mockResponse, jsonResp);
    }

    @Test
    public void validateBracketsShouldReturnUnBalancedResult() throws Exception {
        val mockResponse = givenUnBalancedResponse();
        when(bracketsValidationService.validateBrackets(TEST_INPUT)).thenReturn(mockResponse);

        val requestBuilder = MockMvcRequestBuilders.get("/tasks/validateBrackets?input=" + TEST_INPUT);
        val mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        val resp = mvcResult.getResponse().getContentAsString();

        val mapper = new ObjectMapper();
        val jsonResp = mapper.readValue(resp, BracketsValidationResponse.class);
        assertEquals(mockResponse, jsonResp);
    }

    private BracketsValidationResponse givenBalancedResponse() {
        return new BracketsValidationResponse(TEST_INPUT, true);
    }

    private BracketsValidationResponse givenUnBalancedResponse() {
        return new BracketsValidationResponse(TEST_INPUT, false);
    }
}