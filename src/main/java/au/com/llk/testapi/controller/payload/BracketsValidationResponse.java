package au.com.llk.testapi.controller.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class BracketsValidationResponse {

    @JsonProperty("input")
    public final String input;
    @JsonProperty("isBalanced")
    public final boolean isBalanced;

    @JsonCreator
    public BracketsValidationResponse(@JsonProperty("input") String input,
            @JsonProperty("isBalanced") boolean isBalanced) {
        this.input = input;
        this.isBalanced = isBalanced;
    }

}
