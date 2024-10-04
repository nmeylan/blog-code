package ch.nmeylan.blog.example.spi;

import ch.nmeylan.blog.example.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String>  {

}
