package com.mustafazada.techapptwo.exception;

import com.mustafazada.techapptwo.dto.response.CommonResponseDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class UserAlreadyExist extends RuntimeException {
    private CommonResponseDTO<?> responseDTO;
}
