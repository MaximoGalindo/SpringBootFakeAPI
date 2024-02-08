package SpringBootFakeAPI.services;

import SpringBootFakeAPI.dtos.request.PostUserDTO;
import SpringBootFakeAPI.dtos.request.UpdateUserDTO;
import SpringBootFakeAPI.dtos.response.DeleteDTO;
import SpringBootFakeAPI.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User saveUser(PostUserDTO user);
    User updateUser(Long id, UpdateUserDTO updateUser);
    DeleteDTO deleteUser(Long id);
    User getUserByUserNameAndPassword(String userName, String password);
    User getUserByEmailAndPassword(String email, String password);
    User getUserById(Long id);
    User getUserByUserNameOrEmailAndPassword(String identity, String password);

}
