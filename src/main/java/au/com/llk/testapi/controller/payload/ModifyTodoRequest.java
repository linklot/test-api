package au.com.llk.testapi.controller.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ModifyTodoRequest {

    @JsonProperty("text")
    @NotNull
    @Size(min = 1, max = 50, message = "Must be between 1 and 50 chars long")
    private final String text;
    private final boolean isCompleted;

    public ModifyTodoRequest(@JsonProperty("text") String text, @JsonProperty("isCompleted") boolean isCompleted) {
        this.text = text;
        this.isCompleted = isCompleted;
    }

}
