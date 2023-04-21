package com.mustafazada.techapptwo.dto.response;


import com.mustafazada.techapptwo.entity.Account;
import com.mustafazada.techapptwo.util.Currency;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class AccountResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private BigDecimal balance;
    private Currency currency;
    private Boolean isActive;
    private Integer accountNo;

    public static AccountResponseDTO entityDTO(Account account) {
        return AccountResponseDTO.builder()
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .isActive(account.getIsActive())
                .accountNo(account.getAccountNo())
                .build();
    }
}
