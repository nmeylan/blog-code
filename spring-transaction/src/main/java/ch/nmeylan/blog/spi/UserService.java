package ch.nmeylan.blog.spi;

import ch.nmeylan.blog.UserEntity;

public interface UserService {
    void insert(UserEntity userEntity);
    void nameValidation(String name);
}
