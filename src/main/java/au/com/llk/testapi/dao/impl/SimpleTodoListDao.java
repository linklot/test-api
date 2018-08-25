package au.com.llk.testapi.dao.impl;

import au.com.llk.testapi.dao.TodoListDao;
import au.com.llk.testapi.model.TodoItem;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
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
    public Optional<TodoItem> modifyTodoItem(long id, @NotNull String text, boolean isCompleted) {
        val opItem = TODO_LIST.stream().filter(item -> item.getId() == id).findFirst();

        if (!opItem.isPresent()) {
            return opItem;
        }

        val existingItem = opItem.get();
        val modifiedItem = TodoItem.builder().id(existingItem.getId()).text(text).isCompleted(isCompleted)
                .createdAt(existingItem.getCreatedAt()).build();

        int itemIdx = 0;
        for (int i = 0; i < TODO_LIST.size(); i++) {
            val todoItem = TODO_LIST.get(i);

            if (todoItem.getId() == modifiedItem.getId()) {
                itemIdx = i;
                break;
            }
        }

        TODO_LIST.remove(itemIdx);
        TODO_LIST.add(itemIdx, modifiedItem);

        return Optional.of(modifiedItem);
    }

    private String getLocalDateTimeAsString() {
        val now = LocalDateTime.now();
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return now.format(formatter);
    }
}
