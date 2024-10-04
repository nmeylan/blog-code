package ch.nmeylan.blog.example;

import ch.nmeylan.blog.example.spi.UserRepository;
import ch.nmeylan.blog.example.spi.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void insert(UserEntity userEntity) {
        userRepository.save(userEntity);
    }
}
