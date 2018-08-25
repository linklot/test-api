package au.com.llk.testapi.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ValidationErrorDetails {

    private String location;
    private String param;
    private String msg;
    private String value;

}
