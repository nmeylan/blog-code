package ch.nmeylan.blog.spi;

import ch.nmeylan.blog.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPersistence extends JpaRepository<UserEntity, String>  {

}
