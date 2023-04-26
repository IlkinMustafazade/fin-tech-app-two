package com.mustafazada.techapptwo.dto.mbdto;


import lombok.Builder;
import lombok.Data;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@XmlRootElement(name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
public class ValCursResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "ValType")
    private List<ValTypeResponseDTO> valTypeList;

    public ValCursResponseDTO() {
    }

    public ValCursResponseDTO(List<ValTypeResponseDTO> valTypeList) {
        this.valTypeList = valTypeList;
    }
}
