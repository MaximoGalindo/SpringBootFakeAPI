package SpringBootFakeAPI.dtos.response;

import SpringBootFakeAPI.utils.validatios.password.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostUserDTO {

    @NotNull(message = "userName can't by null")
    private String userName;
    @NotNull(message = "password can't by null")
    @ValidPassword
    private String password;
    @NotNull(message = "Email can't by null")
    @Email(message = "The email need to be valid email")
    private String email;
}
