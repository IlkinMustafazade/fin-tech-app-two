package com.mustafazada.techapptwo.dto.response;

import com.mustafazada.techapptwo.dto.request.AccountRequestDTO;
import com.mustafazada.techapptwo.entity.Account;
import com.mustafazada.techapptwo.entity.TechUser;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;
    private String name;
    private String surname;
    private String password;
    private String pin;
    private String role;
    private List<AccountRequestDTO> accountRequestDTOList;

    public static UserResponseDTO entityResponse(TechUser user){
        List<AccountRequestDTO> accountList = new ArrayList<>();
        user.getAccountList().forEach(accountDTO -> accountList.add(AccountRequestDTO.builder()
                        .balance(accountDTO.getBalance())
                        .currency(accountDTO.getCurrency())
                        .isActive(accountDTO.getIsActive())
                        .accountNo(accountDTO.getAccountNo()).build()));

        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .password(user.getPassword())
                .pin(user.getPin())
                .role(user.getRole())
                .accountRequestDTOList(accountList).build();
    }

}
