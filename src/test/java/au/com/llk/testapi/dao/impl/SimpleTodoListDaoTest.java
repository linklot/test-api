package au.com.llk.testapi.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import lombok.val;
import org.junit.Before;
import org.junit.Test;

public class SimpleTodoListDaoTest {

    private final String TEXT = "test text";

    private SimpleTodoListDao testTarget;

    @Before
    public void setUp() {
        testTarget = new SimpleTodoListDao();
    }

    @Test
    public void createTodoItemShouldSuccess() {
        assertEquals(0, testTarget.TODO_LIST.size());

        val item = testTarget.createTodoItem(TEXT);

        assertEquals(1, testTarget.TODO_LIST.size());
        assertEquals(item, testTarget.TODO_LIST.get(0));

        assertEquals(1, item.getId());
        assertEquals(TEXT, item.getText());
        assertFalse(item.isCompleted());
        assertTrue(item.getCreatedAt().length() > 0);
    }

    @Test
    public void getTodoItemByIdShouldReturnResultWhenExist() {
        val createdItem = testTarget.createTodoItem(TEXT);
        val opItem = testTarget.getTodoItemById(1);
        assertTrue(opItem.isPresent());
        assertEquals(createdItem, opItem.get());
    }

    @Test
    public void getTodoItemByIdShouldReturnEmptyResultWhenNotExist() {
        val opItem = testTarget.getTodoItemById(1);
        assertFalse(opItem.isPresent());
    }

    @Test
    public void modifyTodoItemShouldReturnResultWhenExist() {
        testTarget.createTodoItem("first");
        testTarget.createTodoItem("second");
        testTarget.createTodoItem("third");

        val opItem = testTarget.modifyTodoItem(2, "modified second", true);
        assertTrue(opItem.isPresent());
        val item = opItem.get();
        assertEquals(2, item.getId());
        assertEquals("modified second", item.getText());
        assertTrue(item.isCompleted());
        assertTrue(item.getCreatedAt().length() > 0);

        assertEquals(3, testTarget.TODO_LIST.size());
        assertEquals(item, testTarget.TODO_LIST.get(1));
    }
}