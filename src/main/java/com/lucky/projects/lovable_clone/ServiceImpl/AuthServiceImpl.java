 package com.lucky.projects.lovable_clone.ServiceImpl;


  import com.lucky.projects.lovable_clone.Entity.User;
import com.lucky.projects.lovable_clone.dto.auth.AuthResponse;
import com.lucky.projects.lovable_clone.dto.auth.LoginRequest;
import com.lucky.projects.lovable_clone.dto.auth.SignupRequest;
import com.lucky.projects.lovable_clone.error.BadRequestException;
import com.lucky.projects.lovable_clone.mapper.UserMapper;
import com.lucky.projects.lovable_clone.repository.UserRepository;
import com.lucky.projects.lovable_clone.security.AuthUtil;
import com.lucky.projects.lovable_clone.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true , level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    AuthUtil authUtil;

    AuthenticationManager authenticationManager;

    @Override
    public AuthResponse signup(SignupRequest request) {

        // Validation is enforced by @Valid, but never trust blindly


        if (userRepository.existsByUsername(request.username())) {
            throw new BadRequestException(
                    "User already exists with username: " + request.username()
            );
        }
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);
        String token = authUtil.generateAccessToken(user);
        return new AuthResponse(token,
                userMapper.toUserProfileResponse(user));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);
        return new AuthResponse(token, userMapper.toUserProfileResponse(user));
    }
}
