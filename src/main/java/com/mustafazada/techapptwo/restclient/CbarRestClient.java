package com.mustafazada.techapptwo.restclient;

import com.mustafazada.techapptwo.config.ApplicationConfig;
import com.mustafazada.techapptwo.dto.mbdto.ValCursResponseDTO;
import com.mustafazada.techapptwo.dto.response.CommonResponseDTO;
import com.mustafazada.techapptwo.dto.response.Status;
import com.mustafazada.techapptwo.dto.response.StatusCode;
import com.mustafazada.techapptwo.exception.InvalidDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class CbarRestClient {
    @Autowired
    private RestTemplate restTemplate;


    public ValCursResponseDTO getCurrency() {
        ValCursResponseDTO valCursResponseDTO;
        try {
            valCursResponseDTO = restTemplate.getForObject(ApplicationConfig.urlMB, ValCursResponseDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw InvalidDTO.builder().responseDTO(CommonResponseDTO.builder().status(Status.builder()
                    .statusCode(StatusCode.CBAR_ERROR)
                    .message("Error Happened while getting response from CBAR")
                    .build()).build()).build();
        }

        if (Objects.isNull(valCursResponseDTO)){
            throw InvalidDTO.builder().responseDTO(CommonResponseDTO.builder().status(Status.builder()
                    .statusCode(StatusCode.CBAR_ERROR)
                    .message("Error Happened while getting response from CBAR")
                    .build()).build()).build();
        }
        return valCursResponseDTO;
    }
}
