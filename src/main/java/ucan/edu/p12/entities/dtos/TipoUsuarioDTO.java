/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ucan.edu.p12.entities.dtos;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.Data;
import ucan.edu.p12.entities.TipoUsuario;

/**
 *
 * @author controljp
 */
@Data
public class TipoUsuarioDTO {

    private UUID pkTipoUsuario;
    private String descricao;

    public static TipoUsuarioDTO toDTO(TipoUsuario tipoUsuario) {
        if (tipoUsuario == null) {
            return null; // Retorna null para entradas nulas
        }
        TipoUsuarioDTO dto = new TipoUsuarioDTO();
        dto.setPkTipoUsuario(tipoUsuario.getPkTipoUsuario());
        dto.setDescricao(tipoUsuario.getDescricao());
        return dto;
    }

    public static TipoUsuario toEntity(TipoUsuarioDTO dto) {
        if (dto == null) {
            return null; // Retorna null para entradas nulas
        }
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setPkTipoUsuario(dto.getPkTipoUsuario());
        tipoUsuario.setDescricao(dto.getDescricao());
        return tipoUsuario;
    }

    public static List<TipoUsuarioDTO> converterListParaDTO(List<TipoUsuario> tipoUsuarios) {
        if (tipoUsuarios == null) {
            return List.of(); // Retorna uma lista vazia para entradas nulas
        }
        return tipoUsuarios.stream()
                    .map(TipoUsuarioDTO::toDTO)
                    .collect(Collectors.toList());
    }
}
