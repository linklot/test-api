package au.com.llk.testapi.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
class ItemNotFoundErrorDetails {

    private String message;

}
