package au.com.llk.testapi.dao.impl;

import au.com.llk.testapi.dao.TodoListDao;
import au.com.llk.testapi.model.TodoItem;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import javax.validation.constraints.NotNull;
import lombok.val;
import org.springframework.stereotype.Repository;

@Repository
public class SimpleTodoListDao implements TodoListDao {

    final List<TodoItem> TODO_LIST = new LinkedList<>();
    private AtomicLong nextIndex = new AtomicLong(1);

    @Override
    public TodoItem createTodoItem(@NotNull String text) {
        val item = TodoItem.builder().id(nextIndex.getAndIncrement()).text(text).isCompleted(false)
                .createdAt(getLocalDateTimeAsString()).build();

        TODO_LIST.add(item);

        return item;
    }

    @Override
    public Optional<TodoItem> getTodoItemById(long id) {
        return TODO_LIST.stream().filter(item -> item.getId() == id).findFirst();
    }

    @Override
    public Optional<TodoItem> modifyTodoItem(long id, String text, Boolean isCompleted) {
        val opItem = TODO_LIST.stream().filter(item -> item.getId() == id).findFirst();

        if (!opItem.isPresent()) {
            return opItem;
        }

        val existingItem = opItem.get();
        if (text != null) {
            existingItem.setText(text);
        }

        if (isCompleted != null) {
            existingItem.setCompleted(isCompleted);
        }

        return Optional.of(existingItem);
    }

    private String getLocalDateTimeAsString() {
        val now = LocalDateTime.now();
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return now.format(formatter);
    }
}
