package au.com.llk.testapi.dao;

import au.com.llk.testapi.model.TodoItem;
import java.util.Optional;
import javax.validation.constraints.NotNull;

public interface TodoListDao {

    TodoItem createTodoItem(@NotNull String text);

    Optional<TodoItem> getTodoItemById(long id);

    Optional<TodoItem> modifyTodoItem(long id, String text, Boolean isCompleted);

}
