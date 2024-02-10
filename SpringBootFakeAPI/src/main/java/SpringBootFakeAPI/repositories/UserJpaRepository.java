package SpringBootFakeAPI.repositories;


import SpringBootFakeAPI.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUserNameOrEmail(String username, String email);
    Optional<UserEntity> findByUserNameAndPassword(String userName, String password);
    Optional<UserEntity> findByEmailAndPassword(String email, String password);

    @Query("SELECT p FROM UserEntity p " +
            "WHERE (p.userName LIKE :userName OR p.email LIKE :email) AND p.password LIKE :password")
    Optional<UserEntity> findByUserNameOrEmailAndPassword(
            @Param("userName") String userName,
            @Param("email") String email,
            @Param("password") String password);

}
