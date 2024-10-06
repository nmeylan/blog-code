package ch.nmeylan.blog;


import ch.nmeylan.blog.spi.UserPersistence;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterUserImpl {

    UserPersistence userPersistence;
    // This implementation does not send email, but register a durable job in database which will send email
    DurableEmailSenderImpl durableEmailSender;

    public RegisterUserImpl(UserPersistence userPersistence, DurableEmailSenderImpl durableEmailSender) {
        this.userPersistence = userPersistence;
        this.durableEmailSender = durableEmailSender;
    }

    @Transactional
    public void register(UserEntity userEntity) {
        userPersistence.save(userEntity);
        durableEmailSender.sendEmail(userEntity.getEmail(), "Welcome " + userEntity.getFirstName());
    }
}
