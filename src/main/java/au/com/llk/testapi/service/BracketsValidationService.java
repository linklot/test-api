package au.com.llk.testapi.service;

import au.com.llk.testapi.controller.payload.AccessTodoItemResponse;
import au.com.llk.testapi.controller.payload.BracketsValidationResponse;
import javax.validation.constraints.NotNull;

public interface BracketsValidationService {

    BracketsValidationResponse validateBrackets(@NotNull String input);

}
