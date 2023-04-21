package com.mustafazada.techapptwo.service;

import com.mustafazada.techapptwo.dto.response.AccountResponseDTOList;
import com.mustafazada.techapptwo.dto.response.CommonResponseDTO;
import com.mustafazada.techapptwo.dto.response.Status;
import com.mustafazada.techapptwo.dto.response.StatusCode;
import com.mustafazada.techapptwo.entity.TechUser;
import com.mustafazada.techapptwo.repositroy.UserRepository;
import com.mustafazada.techapptwo.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrentUser currentUser;

    public CommonResponseDTO<?> getAccount() {
        Optional<TechUser> user = userRepository.findByPin(currentUser.getCurrentUser().getUsername());

        return CommonResponseDTO.builder()
                .data(AccountResponseDTOList.entityDTO(user.get().getAccountList()))
                .status(Status.builder().statusCode(StatusCode.SUCCESS)
                        .message("Accounts Successfully fetched")
                        .build()).build();
    }
}
