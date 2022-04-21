package noname.entity.auth;

import lombok.*;
import noname.entity.Auditable;
import noname.entity.role.Role;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUser extends Auditable implements GrantedAuthority {
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String username;
    private String password;
    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public String getAuthority() {
        return role.getName();
    }
}
