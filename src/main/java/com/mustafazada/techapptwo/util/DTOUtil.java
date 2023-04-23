package com.mustafazada.techapptwo.util;

import com.mustafazada.techapptwo.dto.request.AccountToAccountRequestDTO;
import com.mustafazada.techapptwo.dto.request.AuthenticationRequestDTO;
import com.mustafazada.techapptwo.dto.request.UserRequestDTO;
import com.mustafazada.techapptwo.dto.response.CommonResponseDTO;
import com.mustafazada.techapptwo.dto.response.Status;
import com.mustafazada.techapptwo.dto.response.StatusCode;
import com.mustafazada.techapptwo.exception.InvalidDTO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DTOUtil {
    @Autowired
    private Logger logger;

    public void isValid(UserRequestDTO userRequestDTO) {
        logger.info(userRequestDTO.toString());
        checkDTOInputInfo(userRequestDTO.getName());
        checkDTOInputInfo(userRequestDTO.getSurname());
        checkDTOInputInfo(userRequestDTO.getPassword());
        checkDTOInputInfo(userRequestDTO.getPin());
        checkDTOInputInfo(userRequestDTO.getAccountRequestDTOList());
    }

    public void isValid(AuthenticationRequestDTO authenticationRequestDTO) {
        checkDTOInputInfo(authenticationRequestDTO.getPin());
        checkDTOInputInfo(authenticationRequestDTO.getPassword());
    }

    public void isValid(AccountToAccountRequestDTO accountToAccountRequestDTO) {
        logger.info(accountToAccountRequestDTO.toString());
        checkDTOInputInfo(accountToAccountRequestDTO.getDebitAccount());
        checkDTOInputInfo(accountToAccountRequestDTO.getCreditAccount());
        checkDTOInputInfo(accountToAccountRequestDTO.getAmount());
    }

    private <T> void checkDTOInputInfo(T t) {
        if (Objects.isNull(t) || t.toString().isBlank()) {
            logger.error("Invalid Input");
            throw InvalidDTO.builder().responseDTO(CommonResponseDTO.builder().status(Status.builder()
                    .statusCode(StatusCode.INVALID_DTO)
                    .message("Invalid Data")
                    .build()).build()).build();
        }
    }
}
