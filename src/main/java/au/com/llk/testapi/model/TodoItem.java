package au.com.llk.testapi.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@EqualsAndHashCode
@Getter
public class TodoItem {

    private final long id;
    private final String createdAt;
    @Setter
    private String text;
    @Setter
    private boolean isCompleted;

}
