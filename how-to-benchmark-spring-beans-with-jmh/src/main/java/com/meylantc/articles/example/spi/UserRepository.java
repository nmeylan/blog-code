package ch.nmeylan.articles.example.spi;

import ch.nmeylan.articles.example.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String>  {

}
