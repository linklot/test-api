package au.com.llk.testapi.controller.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AccessTodoItemResponse {

    @JsonProperty("id")
    public final int id;
    @JsonProperty("text")
    public final String text;
    @JsonProperty("isCompleted")
    public final boolean isCompleted;
    @JsonProperty("createdAt")
    public final String createdAt;

    @JsonCreator
    public AccessTodoItemResponse(@JsonProperty("id") int id, @JsonProperty("text") String text,
            @JsonProperty("isCompleted") boolean isCompleted, @JsonProperty("createdAt") String createdAt) {
        this.id = id;
        this.text = text;
        this.isCompleted = isCompleted;
        this.createdAt = createdAt;
    }

}
