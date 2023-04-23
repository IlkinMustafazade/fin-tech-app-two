package com.mustafazada.techapptwo.service;

import com.mustafazada.techapptwo.dto.request.AccountToAccountRequestDTO;
import com.mustafazada.techapptwo.dto.response.*;
import com.mustafazada.techapptwo.entity.Account;
import com.mustafazada.techapptwo.entity.TechUser;
import com.mustafazada.techapptwo.exception.InvalidDTO;
import com.mustafazada.techapptwo.repositroy.AccountRepository;
import com.mustafazada.techapptwo.repositroy.UserRepository;
import com.mustafazada.techapptwo.util.CurrentUser;
import com.mustafazada.techapptwo.util.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrentUser currentUser;

    @Autowired
    private DTOUtil dtoUtil;

    @Autowired
    private AccountRepository accountRepository;

    public CommonResponseDTO<?> getAccount() {
        Optional<TechUser> user = userRepository.findByPin(currentUser.getCurrentUser().getUsername());

        return CommonResponseDTO.builder()
                .data(AccountResponseDTOList.entityDTO(user.get().getAccountList()))
                .status(Status.builder().statusCode(StatusCode.SUCCESS)
                        .message("Accounts Successfully fetched")
                        .build()).build();
    }

    @Transactional
    public CommonResponseDTO<?> account2account(AccountToAccountRequestDTO accountToAccountRequestDTO) {
        dtoUtil.isValid(accountToAccountRequestDTO);

        if (accountToAccountRequestDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw InvalidDTO.builder()
                    .responseDTO(CommonResponseDTO.builder()
                            .status(Status.builder()
                                    .statusCode(StatusCode.INVALID_DTO)
                                    .message("Amount is not correct")
                                    .build()).build()).build();
        } else if (accountToAccountRequestDTO.getDebitAccount().equals(accountToAccountRequestDTO.getCreditAccount())) {
            throw InvalidDTO.builder().responseDTO(CommonResponseDTO.builder().status(Status.builder()
                    .statusCode(StatusCode.INVALID_DTO)
                    .message("Debit and Credit accounts are same")
                    .build()).build()).build();
        }

        Optional<Account> byDebitAccountNo = accountRepository.findByAccountNo(accountToAccountRequestDTO.getDebitAccount());

        Account debitAccount;
        Account creditAccount;

        if (byDebitAccountNo.isPresent()) {
            debitAccount = byDebitAccountNo.get();
            if (!debitAccount.getIsActive()) {
                throw InvalidDTO.builder().responseDTO(CommonResponseDTO.builder().status(Status.builder()
                        .statusCode(StatusCode.INVALID_DTO)
                        .message("Debit account is not active")
                        .build()).build()).build();
            }
            if (debitAccount.getBalance().compareTo(accountToAccountRequestDTO.getAmount()) <= 0) {
                throw InvalidDTO.builder().responseDTO(CommonResponseDTO.builder().status(Status.builder()
                        .statusCode(StatusCode.INVALID_DTO)
                        .message("Balance is not enough")
                        .build()).build()).build();
            }

            Optional<Account> byCreditAccountNo = accountRepository.findByAccountNo(accountToAccountRequestDTO.getCreditAccount());
            if (byCreditAccountNo.isPresent()) {
                creditAccount = byCreditAccountNo.get();
                if (!creditAccount.getIsActive()) {
                    throw InvalidDTO.builder().responseDTO(CommonResponseDTO.builder().status(Status.builder()
                            .statusCode(StatusCode.INVALID_DTO)
                            .message("Credit Account is not active")
                            .build()).build()).build();
                }
            } else {
                throw InvalidDTO.builder().responseDTO(CommonResponseDTO.builder().status(Status.builder()
                        .statusCode(StatusCode.INVALID_DTO)
                        .message("Credit account is not present")
                        .build()).build()).build();
            }
        } else {
            throw InvalidDTO.builder().responseDTO(CommonResponseDTO.builder().status(Status.builder()
                    .statusCode(StatusCode.INVALID_DTO)
                    .message("Debit account is not present")
                    .build()).build()).build();
        }

        debitAccount.setBalance(debitAccount.getBalance().subtract(accountToAccountRequestDTO.getAmount()));
        creditAccount.setBalance(creditAccount.getBalance().add(accountToAccountRequestDTO.getAmount()));

        return CommonResponseDTO.builder().status(Status.builder().statusCode(StatusCode.SUCCESS)
                        .message("Transfer completed successfully").build())
                .data(AccountResponseDTO.builder()
                        .balance(debitAccount.getBalance())
                        .currency(debitAccount.getCurrency())
                        .isActive(debitAccount.getIsActive())
                        .accountNo(debitAccount.getAccountNo())
                        .build()).build();
    }
}
