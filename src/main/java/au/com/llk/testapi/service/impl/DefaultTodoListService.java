package au.com.llk.testapi.service.impl;

import au.com.llk.testapi.controller.payload.AccessTodoItemResponse;
import au.com.llk.testapi.controller.payload.ModifyTodoRequest;
import au.com.llk.testapi.dao.TodoListDao;
import au.com.llk.testapi.exception.TodoItemNotFoundException;
import au.com.llk.testapi.service.TodoListService;
import javax.validation.constraints.NotNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultTodoListService implements TodoListService {

    private TodoListDao todoListDao;

    @Autowired
    public DefaultTodoListService(TodoListDao todoListDao) {
        this.todoListDao = todoListDao;
    }

    @Override
    public AccessTodoItemResponse createTodoItem(@NotNull String text) {
        val todoItem = todoListDao.createTodoItem(text);

        return new AccessTodoItemResponse(todoItem.getId(), todoItem.getText(), todoItem.isCompleted(),
                todoItem.getCreatedAt());
    }

    @Override
    public AccessTodoItemResponse retrieveTodoItem(int id) {
        val opItem = todoListDao.getTodoItemById(id);

        if(!opItem.isPresent()) {
            throwTodoItemNotFoundException(id);
        }

        val item = opItem.get();
        return new AccessTodoItemResponse(item.getId(), item.getText(), item.isCompleted(), item.getCreatedAt());
    }

    @Override
    public AccessTodoItemResponse modifyTodoItem(int id, @NotNull ModifyTodoRequest modifyTodoRequest) {
        val opItem = todoListDao.modifyTodoItem(id, modifyTodoRequest.getText(), modifyTodoRequest.isCompleted());

        if(!opItem.isPresent()) {
            throwTodoItemNotFoundException(id);
        }

        val item = opItem.get();
        return new AccessTodoItemResponse(item.getId(), item.getText(), item.isCompleted(), item.getCreatedAt());
    }

    private void throwTodoItemNotFoundException(int id) {
        String msg = String.format("Item with %s not found", id);
        throw new TodoItemNotFoundException(msg);
    }
}
