package au.com.llk.testapi.dao;

import au.com.llk.testapi.model.TodoItem;
import java.util.Optional;
import javax.validation.constraints.NotNull;

public interface TodoListDao {

    TodoItem createTodoItem(@NotNull String text);

    Optional<TodoItem> getTodoItemById(int id);

    Optional<TodoItem> modifyTodoItem(int id, @NotNull String text, boolean isCompleted);

}
