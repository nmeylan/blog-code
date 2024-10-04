package ch.nmeylan.blog;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    static {
        initGeneratedClassHandler("target/cglib");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public static void initGeneratedClassHandler(String targetPath) {
        java.io.File dir = new java.io.File(targetPath);
        dir.mkdirs();
        org.springframework.cglib.core.ReflectUtils.setGeneratedClassHandler((String className, byte[] classContent) -> {
            try (java.io.FileOutputStream out = new java.io.FileOutputStream(new java.io.File(dir, className + ".class"))) {
                out.write(classContent);
            } catch (java.io.IOException e) {
                throw new java.io.UncheckedIOException("CGLIB debug failure - Error while storing " + className, e);
            }
        });
    }
}
