package org.example.courseerpsystem;

import lombok.RequiredArgsConstructor;
import org.example.courseerpsystem.domain.entity.UserEntity;
import org.example.courseerpsystem.domain.enums.UserRole;
import org.example.courseerpsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class CourseEprSystemApplication implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(CourseEprSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("qwer").isEmpty()) {
            UserEntity superAdmin = UserEntity.builder()
                    .name("qwer")
                    .username("qwer")
                    .password(passwordEncoder.encode("qwer"))
                    .role(UserRole.SUPER_ADMIN)
                    .isBlock(false)
                    .build();
            userRepository.save(superAdmin);
        }
    }
}
