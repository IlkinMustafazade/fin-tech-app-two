package com.mustafazada.techapptwo.dto.response;

import com.mustafazada.techapptwo.entity.Account;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class AccountResponseDTOList implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<AccountResponseDTO> accountResponseDTOList;

    public static AccountResponseDTOList entityDTO(List<Account> accountList) {
        List<AccountResponseDTO> accountResponseDTOList = new ArrayList<>();
        accountList.forEach(account -> accountResponseDTOList.add(AccountResponseDTO.entityDTO(account)));
        return AccountResponseDTOList.builder().accountResponseDTOList(accountResponseDTOList).build();
    }
}
