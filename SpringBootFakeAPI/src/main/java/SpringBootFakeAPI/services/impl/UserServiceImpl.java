package SpringBootFakeAPI.services.impl;

import SpringBootFakeAPI.dtos.response.PostUserDTO;
import SpringBootFakeAPI.entities.UserEntity;
import SpringBootFakeAPI.models.User;
import SpringBootFakeAPI.repositories.UserJpaRepository;
import SpringBootFakeAPI.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public User getUserId(User user) {
        return null;
    }

    @Override
    public User saveUser(PostUserDTO user) {
        Optional<UserEntity> playerEntityOptional = userJpaRepository.findByUserNameOrEmail
                (user.getUserName() ,user.getEmail());
        if (playerEntityOptional.isEmpty()){
            UserEntity userEntity = new UserEntity();
            userEntity.setUserName(user.getUserName());
            userEntity.setEmail(user.getEmail());
            userEntity.setPassword(user.getPassword());
            userEntity.setLastlogin(LocalDateTime.now());
            userEntity.setCreateAt(LocalDateTime.now());
            userEntity.setUpdateAt(null);
            userJpaRepository.save(userEntity);
            return modelMapper.map(userEntity,User.class);
        }
        else
            return null;
    }

    @Override
    public User updateUser(long id, User user) {
        return null;
    }

    @Override
    public User deleteUser(long id) {
        return null;
    }

    @Override
    public User getUserByUserNameAndPassword(String userName, String password) {
        return null;
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        return null;
    }

    @Override
    public User getUserByUserNameOrEmailAndPassword(String identity, String password) {
        Optional<UserEntity> userEntityOptional =
                userJpaRepository.findByUserNameOrEmailAndPassword(identity,password);
        if(userEntityOptional.isPresent()){
            userEntityOptional.get().setLastlogin(LocalDateTime.now());
            return modelMapper.map(userEntityOptional.get(), User.class);
        }
        else{
            throw new EntityNotFoundException("Some parameters are incorrect");
        }
    }


}
