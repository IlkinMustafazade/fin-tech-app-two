package com.mustafazada.techapptwo.service;

import com.mustafazada.techapptwo.dto.mbdto.ValCursResponseDTO;
import com.mustafazada.techapptwo.dto.response.CommonResponseDTO;
import com.mustafazada.techapptwo.dto.response.Status;
import com.mustafazada.techapptwo.dto.response.StatusCode;
import com.mustafazada.techapptwo.restclient.CbarRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

    @Autowired
    private CbarRestClient cbarRestClient;

    public CommonResponseDTO<?> getCurrencyRate() {
        ValCursResponseDTO valCursResponseDTO = cbarRestClient.getCurrency();
        return CommonResponseDTO.builder().status(Status.builder()
                .statusCode(StatusCode.SUCCESS)
                .message("All Currencies")
                .build()).data(valCursResponseDTO).build();
    }
}
