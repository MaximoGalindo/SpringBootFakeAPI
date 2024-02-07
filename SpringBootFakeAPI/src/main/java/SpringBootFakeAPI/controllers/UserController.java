package SpringBootFakeAPI.controllers;
import SpringBootFakeAPI.dtos.response.GetUserDTO;
import SpringBootFakeAPI.dtos.response.PostUserDTO;
import SpringBootFakeAPI.models.User;
import SpringBootFakeAPI.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestController("UserController")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = {"http://localhost:42008", "https://springbootfakeapi.onrender.com/"})
    @GetMapping("/getUser")
    public ResponseEntity<GetUserDTO> getUser(@RequestParam String emailOrUserName,
                                              @RequestParam String password,
                                              HttpServletResponse response){

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        User user = userService.getUserByUserNameOrEmailAndPassword(emailOrUserName,password);
        GetUserDTO userResponseDTO = new GetUserDTO(user.getUserName(),user.getLastLoginDate());
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/create-user")
    public ResponseEntity<GetUserDTO> saveUser(@RequestBody PostUserDTO postUserDTO){
        User user = userService.saveUser(postUserDTO);
        GetUserDTO newUser = new GetUserDTO(user.getUserName(),user.getLastLoginDate());
        if(Objects.isNull(postUserDTO)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or Email already exists");
        }
        else
            return ResponseEntity.ok(newUser);
    }

    

}
