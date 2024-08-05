package ch.nmeylan.articles.example.spi;

import ch.nmeylan.articles.example.UserEntity;

public interface UserService {
    void insert(UserEntity userEntity);
}
