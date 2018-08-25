package au.com.llk.testapi.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import au.com.llk.testapi.controller.payload.ModifyTodoRequest;
import au.com.llk.testapi.dao.TodoListDao;
import au.com.llk.testapi.exception.TodoItemNotFoundException;
import au.com.llk.testapi.model.TodoItem;
import java.util.Optional;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class DefaultTodoListServiceTest {

    private final String TEXT = "text text";

    @Mock
    private TodoListDao todoListDao;

    private DefaultTodoListService testTarget;

    @Before
    public void setUp() {
        initMocks(this);
        testTarget = new DefaultTodoListService(todoListDao);
    }

    @Test
    public void createTodoItemShouldReturnResult() {
        when(todoListDao.createTodoItem(TEXT)).thenReturn(givenTodoItem());

        val result = testTarget.createTodoItem(TEXT);
        assertEquals(1, result.id);
        assertEquals(TEXT, result.text);
        assertFalse(result.isCompleted);
        assertTrue(result.createdAt.length() > 0);
    }

    @Test
    public void retrieveTodoItemShouldReturnWhenExists() {
        when(todoListDao.getTodoItemById(1)).thenReturn(Optional.of(givenTodoItem()));

        val resp = testTarget.retrieveTodoItem(1);
        assertEquals(1, resp.id);
        assertEquals(TEXT, resp.text);
        assertFalse(resp.isCompleted);
        assertEquals("createdAt", resp.createdAt);
    }

    @Test(expected = TodoItemNotFoundException.class)
    public void retrieveTodoItemShouldThrowExceptionWhenNotExist() {
        when(todoListDao.getTodoItemById(1)).thenReturn(Optional.empty());
        testTarget.retrieveTodoItem(1);
    }

    @Test
    public void modifyTodoItemShouldSucceed() {
        val mockTodoItem = givenTodoItem();
        when(todoListDao.modifyTodoItem(1, "test text", true)).thenReturn(Optional.of(mockTodoItem));

        val modifyReq = new ModifyTodoRequest("test text", true);
        val resp = testTarget.modifyTodoItem(1, modifyReq);

        assertEquals(1, resp.id);
        assertEquals(TEXT, resp.text);
        assertFalse(resp.isCompleted);
        assertEquals("createdAt", resp.createdAt);
    }

    @Test(expected = TodoItemNotFoundException.class)
    public void modifyTodoItemShouldThrowException() {
        when(todoListDao.modifyTodoItem(1, "test text", true)).thenReturn(Optional.empty());

        val modifyReq = new ModifyTodoRequest("test text", true);
        testTarget.modifyTodoItem(1, modifyReq);
    }

    private TodoItem givenTodoItem() {
        return TodoItem.builder().id(1).text(TEXT).isCompleted(false).createdAt("createdAt").build();
    }
}