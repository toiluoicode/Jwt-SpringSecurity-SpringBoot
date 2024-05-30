package org.example.jwt.Service;

import org.example.jwt.JWT.JWTService;
import org.example.jwt.Models.AuthenticationRespones;
import org.example.jwt.Models.User;
import org.example.jwt.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository  userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationRespones register(User request) {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole());
            userRepository.save(user);
            String token = jwtService.generateToken(user);
            return new AuthenticationRespones(token);
    }
    public AuthenticationRespones authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                       request.getUsername(),
                        request.getPassword()
                )
        );
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        String token = jwtService.generateToken(user.orElse(null));
        return new AuthenticationRespones(token);
    }
}
