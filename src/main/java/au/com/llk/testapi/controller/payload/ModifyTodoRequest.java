package au.com.llk.testapi.controller.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ModifyTodoRequest {

    @JsonProperty("text")
    private final String text;
    @JsonProperty("isCompleted")
    private final Boolean isCompleted;

    public ModifyTodoRequest(@JsonProperty("text") String text, @JsonProperty("isCompleted") Boolean isCompleted) {
        this.text = text;
        this.isCompleted = isCompleted;
    }

}
