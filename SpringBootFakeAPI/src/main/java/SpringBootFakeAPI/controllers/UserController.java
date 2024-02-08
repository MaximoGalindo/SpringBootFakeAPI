package SpringBootFakeAPI.controllers;
import SpringBootFakeAPI.dtos.request.UpdateUserDTO;
import SpringBootFakeAPI.dtos.response.DeleteDTO;
import SpringBootFakeAPI.dtos.response.GetUserDTO;
import SpringBootFakeAPI.dtos.request.PostUserDTO;
import SpringBootFakeAPI.models.User;
import SpringBootFakeAPI.services.UserService;
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

    @GetMapping("{emailorusername},{password}")
    public ResponseEntity<GetUserDTO> getUser(@PathVariable String emailorusername,
                                              @PathVariable String password){
        User user = userService.getUserByUserNameOrEmailAndPassword(emailorusername,password);
        GetUserDTO userResponseDTO = new GetUserDTO(user.getId(),user.getUserName(),user.getLastLoginDate(),user.getUpdateAt());
        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetUserDTO> getUser(@PathVariable Long id){
        User user = userService.getUserById(id);
        GetUserDTO userResponseDTO = new GetUserDTO(user.getId(),user.getUserName(),user.getLastLoginDate(),user.getUpdateAt());
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<GetUserDTO> saveUser(@RequestBody PostUserDTO postUserDTO){
        User user = userService.saveUser(postUserDTO);
        if(Objects.isNull(user))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or Email already exists");
        else{
            GetUserDTO newUser = new GetUserDTO(user.getId(),user.getUserName(),user.getLastLoginDate(),user.getUpdateAt());
            return ResponseEntity.ok(newUser);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<GetUserDTO> updateUser(@PathVariable Long id,
                                                 @RequestBody UpdateUserDTO userUpdated){
        User user = userService.updateUser(id,userUpdated);
        if(Objects.isNull(user))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user ID doesn't exist");
        else{
            GetUserDTO newUser = new GetUserDTO(user.getId(),user.getUserName(),user.getLastLoginDate(),user.getUpdateAt());
            return ResponseEntity.ok(newUser);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DeleteDTO> deleteUser(@PathVariable Long id){
        DeleteDTO deleteUser = userService.deleteUser(id);
        return ResponseEntity.ok(deleteUser);
    }
    

}
