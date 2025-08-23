package com.chirag.book.auth;

import com.chirag.book.Email.EmailService;
import com.chirag.book.role.RoleRepository;
import com.chirag.book.user.Token;
import com.chirag.book.user.TokenRepository;
import com.chirag.book.user.User;
import com.chirag.book.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final EmailService emailService;
    public void register(RegistrationRequest request) {
        var userRole = roleRepository.findByName("USER").orElseThrow(()-> new IllegalArgumentException("Role USER was mot initialized"));

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enable(false)
                .roles(List.of(userRole))
                .build();

        userRepository.save(user);
        sendValidarionEmail(user);
    }

    private void sendValidarionEmail(User user) {
        var newToken = generateAndSaveActivationToken(user);
        //send email

    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generatedActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();

        tokenRepository.save(token);
        return generatedToken;
    }

    private String generatedActivationCode(int length) {
        String character = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++){
            int randomIndex = secureRandom.nextInt(character.length());
            codeBuilder.append(character.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }
}
