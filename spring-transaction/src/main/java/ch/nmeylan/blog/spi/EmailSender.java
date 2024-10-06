package ch.nmeylan.blog.spi;

public interface EmailSender {

    void sendEmail(String email, String text);
}
