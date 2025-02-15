package Philately.user.service;

import Philately.user.model.User;
import Philately.user.repository.UserRepository;
import Philately.web.dto.LoginRequest;
import Philately.web.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest registerRequest){

        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(registerRequest.getUsername(), registerRequest.getEmail());
        if (optionalUser.isPresent()){
            throw new RuntimeException("User with this email or username already exist!");
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        userRepository.save(user);

    }

    public User login(LoginRequest loginRequest) {

        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());
        if (optionalUser.isEmpty()){
            throw new RuntimeException("Incorrect username or password!");
        }

        User user = optionalUser.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new RuntimeException("Incorrect username or password!");
        }

        return user;
    }

    public User getById(UUID userId) {

        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User with id [%s] does not exist!".formatted(userId)));
    }
}
