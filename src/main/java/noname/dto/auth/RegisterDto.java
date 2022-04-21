package noname.dto.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterDto {
    private String firstname;
    private String lastName;
    private String username;
    private String password;
}
