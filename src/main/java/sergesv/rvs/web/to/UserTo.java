package sergesv.rvs.web.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class UserTo {
    private final long id;

    private final String nickName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String firstName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String lastName;

    private final String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String password;

    public final boolean admin;
}
