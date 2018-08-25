package au.com.llk.testapi.exception;

import java.util.List;
import lombok.Getter;

@Getter
class ErrorResponse<T> {

    private final List<T> details;
    private final String name;

    ErrorResponse(List<T> details, String name) {
        this.details = details;
        this.name = name;
    }

}
