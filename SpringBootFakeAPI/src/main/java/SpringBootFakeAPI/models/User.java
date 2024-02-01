package SpringBootFakeAPI.models;

import SpringBootFakeAPI.utils.validatios.password.ValidPassword;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class User {

    private Long id;
    @NotNull(message = "userName can't by null")
    private String userName;
    @NotNull(message = "password can't by null")
    @ValidPassword
    private String password;
    @NotNull(message = "Email can't by null")
    @Email(message = "The email need to be valid email")
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime lastLoginDate;
}
