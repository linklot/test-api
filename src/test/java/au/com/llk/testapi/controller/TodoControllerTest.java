package au.com.llk.testapi.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import au.com.llk.testapi.controller.payload.AccessTodoItemResponse;
import au.com.llk.testapi.controller.payload.ModifyTodoRequest;
import au.com.llk.testapi.exception.GlobalControllerExceptionHandler;
import au.com.llk.testapi.exception.TodoItemNotFoundException;
import au.com.llk.testapi.service.TodoListService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class TodoControllerTest extends AbstractControllerTest {

    private final String TEST_INPUT = "test input";

    @Mock
    private TodoListService todoListService;

    @Before
    public void setUp() {
        initMocks(this);
        val testTarget = new TodoController(todoListService);
        mockMvc = MockMvcBuilders.standaloneSetup(testTarget)
                .setControllerAdvice(new GlobalControllerExceptionHandler()).build();
    }

    @Test
    public void createTodoItemShouldReturnCreatedItem() throws Exception {
        val mockResp = givenCreateItemResp();
        when(todoListService.createTodoItem(TEST_INPUT)).thenReturn(mockResp);

        val requestBuilder = MockMvcRequestBuilders.post("/todo").contentType("application/json")
                .content(givenCreateItemContent());
        val mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        val resp = mvcResult.getResponse().getContentAsString();

        val mapper = new ObjectMapper();
        val jsonResp = mapper.readValue(resp, AccessTodoItemResponse.class);
        assertEquals(mockResp, jsonResp);
    }

    @Test
    public void retrieveTodoItemShouldReturn() throws Exception {
        val mockResp = givenCreateItemResp();
        when(todoListService.retrieveTodoItem(1)).thenReturn(mockResp);

        val requestBuilder = MockMvcRequestBuilders.get("/todo/1");
        val mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        val resp = mvcResult.getResponse().getContentAsString();

        val mapper = new ObjectMapper();
        val jsonResp = mapper.readValue(resp, AccessTodoItemResponse.class);
        assertEquals(mockResp, jsonResp);
    }

    @Test
    public void retrieveTodoItemShouldThrowException() throws Exception {
        doThrow(new TodoItemNotFoundException("msg")).when(todoListService).retrieveTodoItem(1);
        val requestBuilder = MockMvcRequestBuilders.get("/todo/1");
        val mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isNotFound()).andReturn();
        val resp = mvcResult.getResponse().getContentAsString();

        assertEquals("{\"details\":[{\"message\":\"msg\"}],\"name\":\"NotFoundError\"}", resp);
    }

    @Test
    public void modifyTodoItemShouldSucceed() throws Exception {
        val mockResp = givenCreateItemResp();
        val modifyRequest = new ModifyTodoRequest("test text", true);
        when(todoListService.modifyTodoItem(1, modifyRequest)).thenReturn(mockResp);

        val requestBuilder = MockMvcRequestBuilders.patch("/todo/1").contentType("application/json")
                .content(giveModifyItemCotent());
        val mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        val resp = mvcResult.getResponse().getContentAsString();

        val mapper = new ObjectMapper();
        val jsonResp = mapper.readValue(resp, AccessTodoItemResponse.class);
        assertEquals(mockResp, jsonResp);
    }

    @Test
    public void modifyTodoItemShouldThrowException() throws Exception {
        doThrow(new TodoItemNotFoundException("msg")).when(todoListService)
                .modifyTodoItem(eq(1), any(ModifyTodoRequest.class));

        val requestBuilder = MockMvcRequestBuilders.patch("/todo/1").contentType("application/json")
                .content(giveModifyItemCotent());
        val mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isNotFound()).andReturn();
        val resp = mvcResult.getResponse().getContentAsString();

        assertEquals("{\"details\":[{\"message\":\"msg\"}],\"name\":\"NotFoundError\"}", resp);
    }

    private String givenCreateItemContent() {
        return "{\"text\":\"test input\"}";
    }

    private String giveModifyItemCotent() {
        return "{\"text\":\"test text\",\"isCompleted\":true}";
    }

    private AccessTodoItemResponse givenCreateItemResp() {
        return new AccessTodoItemResponse(1, TEST_INPUT, false, "createdAt");
    }
}