package ch.nmeylan.articles.example;

import ch.nmeylan.articles.example.spi.UserRepository;
import ch.nmeylan.articles.example.spi.UserService;
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
