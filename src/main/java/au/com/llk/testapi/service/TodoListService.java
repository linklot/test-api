package au.com.llk.testapi.service;

import au.com.llk.testapi.controller.payload.AccessTodoItemResponse;
import au.com.llk.testapi.controller.payload.ModifyTodoRequest;
import javax.validation.constraints.NotNull;

public interface TodoListService {

    AccessTodoItemResponse createTodoItem(@NotNull String text);

    AccessTodoItemResponse retrieveTodoItem(int id);

    AccessTodoItemResponse modifyTodoItem(int id, @NotNull ModifyTodoRequest modifyTodoRequest);

}
