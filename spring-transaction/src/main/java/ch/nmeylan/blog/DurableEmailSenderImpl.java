package ch.nmeylan.blog;

import ch.nmeylan.blog.spi.EmailSender;
import org.springframework.stereotype.Service;

@Service
public class DurableEmailSenderImpl implements EmailSender {
    @Override
    public void sendEmail(String email, String text) {

    }
}
