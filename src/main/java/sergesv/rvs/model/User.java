package sergesv.rvs.model;

import lombok.*;
import sergesv.rvs.model.security.Role;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

import static sergesv.rvs.util.ValidationUtil.CHECK_EMAIL_REGEXP;

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
    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Email(regexp = CHECK_EMAIL_REGEXP)
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "encrypted_password")
    private String encryptedPassword;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<Role> roles;
}
