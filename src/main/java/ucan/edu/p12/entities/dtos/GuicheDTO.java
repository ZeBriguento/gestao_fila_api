/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ucan.edu.p12.entities.dtos;

import ucan.edu.p12.entities.Guiche;
import ucan.edu.p12.entities.Usuario;
import ucan.edu.p12.entities.enums.StatusGuicheEnum;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.Data;

/**
 *
 * @author controljp
 */
@Data
public class GuicheDTO {
    private UUID pkGuiche;
    private String nome;
    private StatusGuicheEnum status;
    private UUID fkUsuario;

    public static GuicheDTO toDTO(Guiche guiche) {
        if (guiche == null) {
            return null;
        }
        GuicheDTO dto = new GuicheDTO();
        dto.setPkGuiche(guiche.getPkGuiche());
        dto.setNome(guiche.getNome());
        dto.setStatus(guiche.getStatus());
        dto.setFkUsuario(guiche.getFkUsuario() != null ? guiche.getFkUsuario().getPkUsuario() : null);
        return dto;
    }

    public static Guiche toEntity(GuicheDTO dto) {
        if (dto == null) {
            return null;
        }
        Guiche guiche = new Guiche();
        guiche.setPkGuiche(dto.getPkGuiche());
        guiche.setNome(dto.getNome());
        guiche.setStatus(dto.getStatus());

        if (dto.getFkUsuario() != null) {
            Usuario usuario = new Usuario();
            usuario.setPkUsuario(dto.getFkUsuario());
            guiche.setFkUsuario(usuario);
        }

        return guiche;
    }

    public static List<GuicheDTO> converterListParaDTO(List<Guiche> entidades) {
        if (entidades == null) {
            return List.of();
        }
        return entidades.stream()
                .map(GuicheDTO::toDTO)
                .collect(Collectors.toList());
    }
}
