package org.example.practice.loader;

import lombok.RequiredArgsConstructor;
import org.example.practice.constants.RoleName;
import org.example.practice.entity.Role;
import org.example.practice.entity.User;
import org.example.practice.repository.RoleRepository;
import org.example.practice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Loader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() == 0) {

            Role role = new Role();
            role.setName("ROLE_USER");
            roleRepository.save(role);

            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setName("Asror");
            user.setSurname("Sattorov");
            user.setUserRole(role);
            userRepository.save(user);
        }
    }
}
