package au.com.llk.testapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import au.com.llk.testapi.controller.payload.BracketsValidationResponse;
import au.com.llk.testapi.service.BracketsValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tasks/validateBrackets")
@Validated
public class BracketsValidatorController {

    private BracketsValidationService bracketsValidationService;

    @Autowired
    public BracketsValidatorController(BracketsValidationService bracketsValidationService) {
        this.bracketsValidationService = bracketsValidationService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public BracketsValidationResponse validateBrackets(@RequestParam final String input) {
        return bracketsValidationService.validateBrackets(input);
    }

}
