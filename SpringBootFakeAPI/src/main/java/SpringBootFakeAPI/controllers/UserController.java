package SpringBootFakeAPI.controllers;
import SpringBootFakeAPI.dtos.common.ErrorApi;
import SpringBootFakeAPI.dtos.request.UpdateUserDTO;
import SpringBootFakeAPI.dtos.response.DeleteDTO;
import SpringBootFakeAPI.dtos.response.GetUserDTO;
import SpringBootFakeAPI.dtos.request.PostUserDTO;
import SpringBootFakeAPI.dtos.response.UpdateDTO;
import SpringBootFakeAPI.models.User;
import SpringBootFakeAPI.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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

    @Operation(
            summary = "Get a user",
            description = "Return the user that matches both the username or email and password"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation, user obtained",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetUserDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request due to missing required data or incorrect format",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorApi.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorApi.class)
                    )
            )
    })
    @GetMapping("")
    public ResponseEntity<GetUserDTO> getUser(@RequestParam(required = false) @Email String email,
                                              @RequestParam(required = false) String userName,
                                              @RequestParam(required = true) String password){
        if (email == null && userName == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username and Email can't be null");
        }
        User user = userService.getUserByUserNameOrEmailAndPassword(email,userName,password);
        GetUserDTO userResponseDTO =
                new GetUserDTO(user.getId(),user.getUserName(),user.getActive(),user.getLastLoginDate(),user.getUpdateAt());
        return ResponseEntity.ok(userResponseDTO);
    }

    @Operation(
            summary = "Get a user by ID",
            description = "Return the user that matches with ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation, user obtained",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetUserDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request as the specified user does not exist",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorApi.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorApi.class)
                    )
            )
    })
    @GetMapping("{id}")
    public ResponseEntity<GetUserDTO> getUserById(@PathVariable Long id){
        User user = userService.getUserById(id);
        GetUserDTO userResponseDTO = new GetUserDTO(user.getId(),user.getUserName(),user.getActive(),user.getLastLoginDate(),user.getUpdateAt());
        return ResponseEntity.ok(userResponseDTO);
    }

    @Operation(
            summary = "Create an User",
            description = "The user must be created with a non-existing username, " +
                          "a valid email, and a password that includes 1 uppercase letter, " +
                          "1 number, 8-30 characters, and 1 special character."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation, User created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PostUserDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorApi.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorApi.class)
                    )
            )
    })
    @PostMapping()
    public ResponseEntity<GetUserDTO> saveUser(@RequestBody @Valid PostUserDTO postUserDTO){

        User user = userService.saveUser(postUserDTO);
        if(Objects.isNull(user))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or Email already exists");
        else{
            GetUserDTO newUser = new GetUserDTO(user.getId(),user.getUserName(),user.getActive(),user.getLastLoginDate(),user.getUpdateAt());
            return ResponseEntity.ok(newUser);
        }
    }

    @Operation(
            summary = "Update an User",
            description = "Enables update user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation, User updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateUserDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorApi.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorApi.class)
                    )
            )
    })
    @PutMapping("{id}")
    public ResponseEntity<GetUserDTO> updateUser(@PathVariable Long id,
                                                 @RequestBody UpdateUserDTO userUpdated){
        User user = userService.updateUser(id,userUpdated);
        if(Objects.isNull(user))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user ID doesn't exist");
        else{
            GetUserDTO newUser = new GetUserDTO(user.getId(),user.getUserName(),user.getActive(),user.getLastLoginDate(),user.getUpdateAt());
            return ResponseEntity.ok(newUser);
        }
    }

    @Operation(
            summary = "Update the status of a user",
            description = "A user can be deactivated or activated by sending the ID and the state 'true' or 'false,' respectively"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation, User updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorApi.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorApi.class)
                    )
            )
    })
    @PutMapping("/state/{id},{state}")
    public ResponseEntity<UpdateDTO> changeUserState(@PathVariable Long id,
                                                     @PathVariable Boolean state){
        UpdateDTO userState = userService.changeStateUser(id,state);
        return ResponseEntity.ok(userState);
    }


    @Operation(
            summary = "Delete a user",
            description = "Allows deleting a user by providing their ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation, User deleted",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorApi.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorApi.class)
                    )
            )
    })
    @DeleteMapping("{id}")
    public ResponseEntity<DeleteDTO> deleteUser(@PathVariable Long id){
        DeleteDTO deleteUser = userService.deleteUser(id);
        return ResponseEntity.ok(deleteUser);
    }
    

}
