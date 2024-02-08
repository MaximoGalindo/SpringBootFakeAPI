package SpringBootFakeAPI.dtos.request;

import SpringBootFakeAPI.utils.validatios.password.ValidPassword;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserDTO {

    @NotNull(message = "userName can't by null")
    private String userName;
    @NotNull(message = "password can't by null")
    @ValidPassword
    private String password;
}
