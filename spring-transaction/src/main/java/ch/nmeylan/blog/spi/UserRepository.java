package ch.nmeylan.blog.spi;

import ch.nmeylan.blog.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String>  {

}
