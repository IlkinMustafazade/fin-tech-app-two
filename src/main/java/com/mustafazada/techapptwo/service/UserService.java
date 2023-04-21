package com.mustafazada.techapptwo.service;

import com.mustafazada.techapptwo.config.security.JwtUtil;
import com.mustafazada.techapptwo.dto.request.AuthenticationRequestDTO;
import com.mustafazada.techapptwo.dto.request.UserRequestDTO;
import com.mustafazada.techapptwo.dto.response.*;
import com.mustafazada.techapptwo.entity.TechUser;
import com.mustafazada.techapptwo.exception.NoSuchUserExistException;
import com.mustafazada.techapptwo.exception.UserAlreadyExist;
import com.mustafazada.techapptwo.repositroy.UserRepository;
import com.mustafazada.techapptwo.util.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private DTOUtil dtoUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public CommonResponseDTO<?> saveUser(UserRequestDTO userRequestDTO) {
        dtoUtil.isValid(userRequestDTO);

        if (userRepository.findByPin(userRequestDTO.getPin()).isPresent()) {
            throw UserAlreadyExist.builder().responseDTO(CommonResponseDTO.builder().status(Status.builder()
                    .statusCode(StatusCode.USER_EXIST)
                    .message("User already exist with pin: " + userRequestDTO.getPin())
                    .build()).build()).build();
        }

        TechUser user = TechUser.builder()
                .name(userRequestDTO.getName())
                .surname(userRequestDTO.getSurname())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .pin(userRequestDTO.getPin())
                .role("ROLE_USER")
                .build();

        user.addAccountToUser(userRequestDTO.getAccountRequestDTOList());

        return CommonResponseDTO.builder().status(Status.builder()
                        .statusCode(StatusCode.SUCCESS)
                        .message("User created successfully").build())
                .data(UserResponseDTO.entityResponse(userRepository.save(user))).build();
    }

    public CommonResponseDTO<?> loginUser(AuthenticationRequestDTO authenticationRequestDTO) {
        dtoUtil.isValid(authenticationRequestDTO);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (
                            authenticationRequestDTO.getPin(),
                            authenticationRequestDTO.getPassword()
                    ));
        } catch (Exception e) {
            throw NoSuchUserExistException.builder()
                    .responseDTO(CommonResponseDTO.builder().status(Status.builder()
                            .statusCode(StatusCode.USER_NOT_EXIST)
                            .message("There is no User with this pin: " + authenticationRequestDTO.getPin())
                            .build()).build()).build();
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequestDTO.getPin());
        return CommonResponseDTO.builder()
                .data(AuthenticationResponseDTO.builder().tokenForUser(jwtUtil.createToken(userDetails)).build())
                .status(Status.builder()
                        .statusCode(StatusCode.SUCCESS)
                        .message("Token was created successfully").build()).build();
    }
}
