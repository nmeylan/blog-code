package ch.nmeylan.blog.example.spi;

import ch.nmeylan.blog.example.UserEntity;

public interface UserService {
    void insert(UserEntity userEntity);
}
