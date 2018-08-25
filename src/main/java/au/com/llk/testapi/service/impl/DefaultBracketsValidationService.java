package au.com.llk.testapi.service.impl;

import au.com.llk.testapi.controller.payload.BracketsValidationResponse;
import au.com.llk.testapi.service.BracketsValidationService;
import au.com.llk.testapi.utils.BracketsValidator;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultBracketsValidationService implements BracketsValidationService {

    private BracketsValidator bracketsValidator;

    @Autowired
    public DefaultBracketsValidationService(BracketsValidator bracketsValidator) {
        this.bracketsValidator = bracketsValidator;
    }

    @Override
    public BracketsValidationResponse validateBrackets(@NotNull final String input) {
        boolean balanced = bracketsValidator.validateBrackets(input);

        return new BracketsValidationResponse(input, balanced);
    }

}
