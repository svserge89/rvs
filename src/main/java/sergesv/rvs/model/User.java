package sergesv.rvs.model;

import lombok.*;
import org.hibernate.validator.constraints.SafeHtml;
import sergesv.rvs.model.security.Role;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

import static sergesv.rvs.util.ValidationUtil.*;

@Entity
@NamedEntityGraph(name = User.GRAPH_WITH_ROLES, attributeNodes = @NamedAttributeNode("roles"))
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements EntityWithId {
    public static final String GRAPH_WITH_ROLES = "User_graphWithRoles";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private long id;

    @NotEmpty
    @SafeHtml
    @Size(max = NICK_NAME_SIZE)
    @Column(name = "nick_name")
    private String nickName;

    @SafeHtml
    @Size(max = FIRST_NAME_SIZE)
    @Column(name = "first_name")
    private String firstName;

    @SafeHtml
    @Size(max = LAST_NAME_SIZE)
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @SafeHtml
    @Email(regexp = CHECK_EMAIL_REGEXP)
    @Size(max = EMAIL_SIZE)
    @Column(name = "email")
    private String email;

    @NotNull
    @Size(max = ENCRYPTED_PASSWORD_SIZE)
    @Column(name = "encrypted_password")
    private String encryptedPassword;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<Role> roles;
}
