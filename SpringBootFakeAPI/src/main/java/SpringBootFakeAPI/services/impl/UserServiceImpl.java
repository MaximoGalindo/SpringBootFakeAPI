package SpringBootFakeAPI.services.impl;

import SpringBootFakeAPI.dtos.request.PostUserDTO;
import SpringBootFakeAPI.dtos.request.UpdateUserDTO;
import SpringBootFakeAPI.dtos.response.DeleteDTO;
import SpringBootFakeAPI.dtos.response.UpdateDTO;
import SpringBootFakeAPI.entities.UserEntity;
import SpringBootFakeAPI.models.User;
import SpringBootFakeAPI.repositories.UserJpaRepository;
import SpringBootFakeAPI.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ModelMapper modelMapper;

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
            userEntity.setActive(true);
            userJpaRepository.save(userEntity);
            return modelMapper.map(userEntity,User.class);
        }
        else
            return null;
    }

    @Override
    public User updateUser(Long id, UpdateUserDTO updatedUser) {
        Optional<UserEntity> userEntityOptional = userJpaRepository.findById(id);
        if(userEntityOptional.isPresent()){
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setUserName(updatedUser.getUserName());
            userEntity.setPassword(updatedUser.getPassword());
            userEntity.setUpdateAt(LocalDateTime.now());
            userEntity = userJpaRepository.save(userEntity);
            User user =  modelMapper.map(userEntity,User.class);
            return user;
        }
        else{
            throw new EntityNotFoundException("This user ID doesn't exist");
        }
    }



    @Override
    public DeleteDTO deleteUser(Long id) {
        Optional<UserEntity> userEntityOptional = userJpaRepository.findById(id);
        if(userEntityOptional.isPresent()){
            UserEntity userEntity = userEntityOptional.get();
            userJpaRepository.delete(userEntity);
            return new DeleteDTO(true,"The user has been deleted successfully");
        }
        else
            return new DeleteDTO(false, "This user ID doesn't exist");

    }

    @Override
    public UpdateDTO changeStateUser(Long id,Boolean state) {
        Optional<UserEntity> userEntityOptional = userJpaRepository.findById(id);
        if(userEntityOptional.isPresent()){
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setActive(state);
            userEntity = userJpaRepository.save(userEntity);
            return (userEntity != null) ?
                    new UpdateDTO(true, "The user's status has changed to " + state.toString().toUpperCase()) :
                    new UpdateDTO(false, "Failed to update user status.");
        }
        else
            return new UpdateDTO(false, "This user ID doesn't exist");

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
    public User getUserById(Long id) {
        Optional<UserEntity> userEntityOptional = userJpaRepository.findById(id);
        if(userEntityOptional.isPresent()){
            userEntityOptional.get().setLastlogin(LocalDateTime.now());
            return modelMapper.map(userEntityOptional.get(), User.class);
        }
        else{
            throw new EntityNotFoundException("This user ID doesn't exist");
        }
    }

    @Override
    public User getUserByUserNameOrEmailAndPassword(String email,String userName, String password) {
        Optional<UserEntity> userEntityOptional =
                userJpaRepository.findByUserNameOrEmailAndPassword(email,userName,password);
        if(userEntityOptional.isPresent()){
            userEntityOptional.get().setLastlogin(LocalDateTime.now());
            return modelMapper.map(userEntityOptional.get(), User.class);
        }
        else{
            throw new EntityNotFoundException("Some parameters are incorrect");
        }
    }


}
