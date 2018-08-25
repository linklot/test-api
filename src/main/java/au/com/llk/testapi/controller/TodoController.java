package au.com.llk.testapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import au.com.llk.testapi.controller.payload.AccessTodoItemResponse;
import au.com.llk.testapi.controller.payload.CreateTodoRequest;
import au.com.llk.testapi.controller.payload.ModifyTodoRequest;
import au.com.llk.testapi.service.TodoListService;
import javax.validation.constraints.Size;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
@Validated
public class TodoController {

    private TodoListService todoListService;

    @Autowired
    public TodoController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public AccessTodoItemResponse createTodoItem(@Validated @RequestBody final CreateTodoRequest createTodoRequest) {
        return todoListService.createTodoItem(createTodoRequest.text);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public AccessTodoItemResponse retrieveTodoItem(
            @PathVariable @Size(min = 1, max = 50, message = "Must be between 1 and 50 chars long") final String id) {
        validateId(id);

        val idValue = Long.parseLong(id);
        return todoListService.retrieveTodoItem(idValue);
    }

    @PatchMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public AccessTodoItemResponse modifyTodoItem(
            @PathVariable @Size(min = 1, max = 50, message = "Must be between 1 and 50 chars long") final String id,
            @RequestBody @Validated final ModifyTodoRequest modifyTodoRequest) {
        validateId(id);

        val idValue = Long.parseLong(id);
        return todoListService.modifyTodoItem(idValue, modifyTodoRequest);
    }

    private void validateId(final String id) {
        val valid = id.matches("^[0-9]*");
        if (!valid) {
            throw new IllegalArgumentException("id must contain only digits");
        }
    }

}
