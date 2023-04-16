package com.mustafazada.techapptwo.config.security;

import com.mustafazada.techapptwo.dto.response.CommonResponseDTO;
import com.mustafazada.techapptwo.dto.response.Status;
import com.mustafazada.techapptwo.dto.response.StatusCode;
import com.mustafazada.techapptwo.entity.TechUser;
import com.mustafazada.techapptwo.exception.NoSuchUserExistException;
import com.mustafazada.techapptwo.repositroy.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Logger logger;

    @Override
    public UserDetails loadUserByUsername(String pin) throws UsernameNotFoundException {
        Optional<TechUser> byPin = userRepository.findByPin(pin);
        if (byPin.isPresent()) {
            return new MyUserDetails(byPin.get());
        } else {
            logger.error("There is no user with this pin: " + pin);
            throw NoSuchUserExistException.builder().
                    responseDTO(CommonResponseDTO.builder().status(Status.builder()
                            .statusCode(StatusCode.USER_NOT_EXIST)
                            .message("There is no user with this pin: " + pin)
                            .build()).build()).build();
        }
    }
}
