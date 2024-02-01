package SpringBootFakeAPI.services;

import SpringBootFakeAPI.dtos.response.PostUserDTO;
import SpringBootFakeAPI.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User saveUser(PostUserDTO user);
    User updateUser(long id, User user);
    User deleteUser(long id);
    User getUserByUserNameAndPassword(String userName, String password);
    User getUserByEmailAndPassword(String email, String password);
    User getUserByUserNameOrEmailAndPassword(String identity, String password);

}
