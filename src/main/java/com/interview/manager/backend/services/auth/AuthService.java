package com.interview.manager.backend.services.auth;

import com.interview.manager.backend.dto.UserLoginRequestDto;
import com.interview.manager.backend.dto.UserLoginResponseDto;
import com.interview.manager.backend.dto.UserRegisterRequestDto;
import com.interview.manager.backend.dto.UserResponseDto;
import com.interview.manager.backend.models.User;
import com.interview.manager.backend.repositories.UserRepository;
import com.interview.manager.backend.services.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;

    private final JwtTokenUtil jwtTokenUtil;
    private static final UserMapper Mapper = UserMapper.INSTANCE;



    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail());

        // TODO: exceptions
        if(user == null) {
            throw new NoSuchElementException("User not found");
        }

        String hashedPassword = hashPasswordWithSalt(requestDto.getPassword(), user.getSalt());

        if(hashedPassword == null || !hashedPassword.equals(user.getPassword())) {
            return null;
        }

        String token = jwtTokenUtil.generateToken(user.getId(), false);
        String refreshToken = jwtTokenUtil.generateToken(user.getId(), true);

        return new UserLoginResponseDto(token, refreshToken);
    }

    public UserResponseDto register(UserRegisterRequestDto requestDto) {
        User existingUser = userRepository.findByEmail(requestDto.getEmail());

        // TODO: exceptions
        if(existingUser != null) {
            return null;
        }

        String randomSalt = UUID.randomUUID().toString();
        String password = hashPasswordWithSalt(requestDto.getPassword(), randomSalt);

        User newUser = User.builder()
            .name(requestDto.getName())
            .surname(requestDto.getSurname())
            .email(requestDto.getEmail())
            .password(password)
            .salt(randomSalt)
            .build();

        userRepository.save(newUser);
        return Mapper.userResponseDto(newUser);
    }

    public UserLoginResponseDto renewToken() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;

            Object principal = usernamePasswordAuthenticationToken.getPrincipal();

            if (principal instanceof UserResponseDto userResponseDto) {

                String token = jwtTokenUtil.generateToken(userResponseDto.getId(), false);
                String refreshToken = jwtTokenUtil.generateToken(userResponseDto.getId(), true);

                return new UserLoginResponseDto(token, refreshToken);

            }
        }

        if(authentication == null) {
            throw new NoSuchElementException("User not found");
        }

        return null;
    }

    private static String hashPasswordWithSalt(String password, String salt) {
        try {
            String passwordWithSalt = password + salt;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(passwordWithSalt.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (Exception e) {
            return null;
        }
    }
}
