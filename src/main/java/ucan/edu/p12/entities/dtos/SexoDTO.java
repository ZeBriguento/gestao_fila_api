/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ucan.edu.p12.entities.dtos;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.Data;
import ucan.edu.p12.entities.Sexo;

/**
 *
 * @author controljp
 */
@Data
public class SexoDTO {

    private UUID pkSexo;
    private String descricao;

    public static SexoDTO toDTO(Sexo sexo) {
        if (sexo == null) {
            return null; // Retorna null para entradas nulas
        }
        SexoDTO dto = new SexoDTO();
        dto.setPkSexo(sexo.getPkSexo());
        dto.setDescricao(sexo.getDescricao());
        return dto;
    }

    public static Sexo toEntity(SexoDTO dto) {
        if (dto == null) {
            return null; // Retorna null para entradas nulas
        }
        Sexo sexo = new Sexo();
        sexo.setPkSexo(dto.getPkSexo());
        sexo.setDescricao(dto.getDescricao());
        return sexo;
    }

    public static List<SexoDTO> converterListParaDTO(List<Sexo> sexos) {
        if (sexos == null) {
            return List.of(); // Retorna uma lista vazia para entradas nulas
        }
        return sexos.stream()
                    .map(SexoDTO::toDTO)
                    .collect(Collectors.toList());
    }
}
