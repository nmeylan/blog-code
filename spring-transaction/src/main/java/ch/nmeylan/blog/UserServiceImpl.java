package ch.nmeylan.blog;

import ch.nmeylan.blog.spi.UserRepository;
import ch.nmeylan.blog.spi.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.Thread.sleep;

@Service
@Transactional // <- Notice @Transactional at class level
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void insert(UserEntity userEntity)  {
        userRepository.save(userEntity);
    }

    @Override
    public void nameValidation(String name) {
        // Calling external service to check if name is valid
        try {
            sleep(1000 * 60);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
