package au.com.llk.testapi.controller.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateTodoRequest {

    @JsonProperty("text")
    @NotNull
    @Size(min = 1, max = 50, message = "Must be between 1 and 50 chars long")
    public final String text;

    public CreateTodoRequest(@JsonProperty("text") String text) {
        this.text = text;
    }

}
